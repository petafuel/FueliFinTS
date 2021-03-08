package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.DatenElementGruppe;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;
import net.petafuel.fuelifints.support.FieldComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractElement implements IMessageElement {


    private static final Logger LOG = LogManager.getLogger(AbstractElement.class);

    private boolean stopSkippingUnusedElements = false;

    public void setStopSkippingUnusedElements() {
        stopSkippingUnusedElements = true;
    }

    public static String generateEscapedVersion(String s) {
        String escapedString = "";
        if (s != null) {
            for (char c : s.toCharArray()) {
                switch (c) {
                    case ':':
                    case '+':
                    case '@':
                    case '?':
                    case '\'':
                        escapedString += '?';
                }
                escapedString += c;
            }
        }
        return escapedString;
    }

    public static ElementDescription getMatchingElementDescription(Field f, int segmentVersion) {
        Element element;
        if ((element = f.getAnnotation(Element.class)) != null) {
            for (ElementDescription description : element.description()) {
                if (description.segmentVersion() == segmentVersion ||
                        description.segmentVersion() == 0)
                    return description;
            }
        }
        return null;
    }

    public byte[] getHbciEncoded() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        List<Field> degs = new LinkedList<Field>();

        int segmentVersion = -1;
        if (this instanceof Segment) {
            Segmentkopf segmentkopf = ((Segment) this).getSegmentkopf();
            if (segmentkopf != null) {
                segmentVersion = segmentkopf.getSegmentVersion();
            }
        }

        Field[] declaredFields = getClass().getDeclaredFields();

        for (Field f : declaredFields) {
            f.setAccessible(true);
            if (getMatchingElementDescription(f, segmentVersion) != null)
                degs.add(f);
        }
        Collections.sort(degs, new FieldComparator(segmentVersion));
        try {
            removeUnusedDE(degs, segmentVersion);
            for (int i = 0; i < degs.size(); i++) {
                Field f = degs.get(i);
                if (f.getAnnotation(bin.class) != null) {
                    Object value = f.get(this);
                    if (value == null && getMatchingElementDescription(f, segmentVersion).status() == ElementDescription.StatusCode.M) {
                        throw new RuntimeException("Muss Feld nicht gesetzt: " + f.getName() + " typ: " + f.getType().getSimpleName());
                    }
                    if (value != null)
                        byteArrayOutputStream.write((byte[]) f.get(this));
                } else if (f.getAnnotation(num.class) != null) {
                    Object value = f.get(this);
                    if (value == null && getMatchingElementDescription(f, segmentVersion).status() == ElementDescription.StatusCode.M) {
                        throw new RuntimeException("Muss Feld von Klasse " + this.getClass().getSimpleName() + " nicht gesetzt: " + f.getName() + " typ: " + f.getType().getSimpleName());
                    }
                    if (value != null) {
                        byteArrayOutputStream.write(Integer.toString((Integer) value).getBytes("ISO-8859-1"));
                    }
                } else if (AbstractElement.class.isAssignableFrom(f.getType())) {
                    AbstractElement abstractElement = (AbstractElement) f.get(this);
                    if (abstractElement == null) {
                        if (getMatchingElementDescription(f, segmentVersion).status() == ElementDescription.StatusCode.M) {
                            LOG.debug(f.getName());
                            throw new RuntimeException("Muss Feld nicht gesetzt: " + f.getName() + " typ: " + f.getType().getSimpleName());
                        } else if (this instanceof DatenElementGruppe) {
                            //falls noch weitere Felder folgen müssen für optionale DEGs alle felder "leer gesetzt werden" sprich es fehlen noch separator character
                            if (i < degs.size() - 1) {
                                //anzahl elemente - 1 für letzte automatisch gesetzte trennzeichen
                                int count = f.getType().getDeclaredFields().length - 1;
                                for (int j = 0; j < count; j++) {
                                    byteArrayOutputStream.write(':');
                                }
                            }
                        }
                    } else {
                        byteArrayOutputStream.write(abstractElement.getHbciEncoded());
                    }
                } else if (List.class.isAssignableFrom(f.getType())) {
                    List list = (List) f.get(this);

                    /* Alle List-Typen die kein AbstractElement sind (DEGs/DEs die öfter vorkommen
                     * müssen einen Typ besitzen der sich als String ohne Fehler darstellen lässt.
                     */
                    if (list != null) {
                        for (int j = 0; j < list.size(); j++) {
                            Object o = list.get(j);
                            if (AbstractElement.class.isAssignableFrom(o.getClass())) {
                                AbstractElement abstractElement = (AbstractElement) o;
                                byteArrayOutputStream.write(abstractElement.getHbciEncoded());
                            } else {
                                Type type = f.getGenericType();
                                ParameterizedType parameterizedType = (ParameterizedType) type;
                                type = parameterizedType.getActualTypeArguments()[0];
                                if (type.equals(String.class)) {
                                    byteArrayOutputStream.write(o.toString().getBytes("ISO-8859-1"));
                                } else if (type.equals(byte[].class)) {
                                    byteArrayOutputStream.write((byte[]) o);
                                }
                            }
                            if (j < list.size() - 1) {
                                byteArrayOutputStream.write(getSeparatorCharacter());
                            }
                        }
                    }
                } else {
                    /*
                     * es sollten lediglich noch Strings übrig bleiben
                     */
                    Object value = f.get(this);
                    if (value == null && getMatchingElementDescription(f, segmentVersion).status() == ElementDescription.StatusCode.M) {
                        throw new RuntimeException("Muss Feld nicht gesetzt.: " + f.getName());
                    }
                    if (value != null)
                        byteArrayOutputStream.write(generateEscapedVersion(f.get(this).toString()).getBytes("ISO-8859-1"));
                }
                if (i < degs.size() - 1) {
                    byteArrayOutputStream.write(getSeparatorCharacter());
                }
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            LOG.error("IOException", e);
        } catch (IllegalAccessException e) {
            LOG.error("IllegalAccessException", e);
        }

        return byteArrayOutputStream.toByteArray();
    }

    protected abstract char getSeparatorCharacter();

    private void removeUnusedDE(List<Field> degs, int segmentVersion) throws IllegalAccessException {
        if (stopSkippingUnusedElements) {
            return;
        }
        List<Field> toRemove = new LinkedList<Field>();
        for (int i = degs.size() - 1; i >= 0; i--) {
            Field f = degs.get(i);
            if (getMatchingElementDescription(f, segmentVersion).status() == ElementDescription.StatusCode.C ||
                    getMatchingElementDescription(f, segmentVersion).status() == ElementDescription.StatusCode.O) {
                if (f.get(this) == null || ((f.get(this) instanceof String) && f.get(this).equals(""))) {
                    toRemove.add(f);
                } else {
                    /*
                     * falls es mehrere optionale Felder gibt und eines davon gesetzt ist müssen die anderen leer bleiben
                     * also abbrechen
                     */
                    break;
                }
            } else {
                /*
                 * falls ein pflichtfeld gefunden wird abbrechen
                 */
                break;
            }
        }
        degs.removeAll(toRemove);
    }

}
