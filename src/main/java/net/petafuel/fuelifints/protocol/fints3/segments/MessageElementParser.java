package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.exceptions.ElementParseException;
import net.petafuel.fuelifints.exceptions.HBCISyntaxException;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.DatenElementGruppe;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.UnterstuetzteCamtMessages;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;
import net.petafuel.fuelifints.support.ByteSplit;
import net.petafuel.fuelifints.support.FieldComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageElementParser {

    private static final Logger LOG = LogManager.getLogger(MessageElementParser.class);

    protected IMessageElement myParseElement;

    public MessageElementParser(IMessageElement parseElement) {
        this.myParseElement = parseElement;
    }

    private int getElementCount(Class<?> c, int segmentVersion) {
        List<Field> fields = getAllDeclaredFieldsFromAllSuperClasses(c);
        int result = 0;
        for (Field f : fields) {
            if (AbstractElement.getMatchingElementDescription(f, segmentVersion) != null) {
                result++;
            }
        }
        return result;
    }

    /**
     * Belegt alle entsprechenden Variablen einer Klasse mit dem zuständigen Wert eines Segments.
     * DatenElemente und DatenElementGruppen werden rekursiv auch gefüllt.
     *
     * @throws ElementParseException
     */
    public void fillElements() throws ElementParseException {
        int splitMode = (myParseElement instanceof Segment) ? ByteSplit.MODE_DEG : ByteSplit.MODE_DE;
        int segmentVersion = -1;
        if (splitMode == ByteSplit.MODE_DEG) {
            segmentVersion = getSegmentVersion();
        }
        try {

            List<byte[]> splittedMessage = null;
            try {
                splittedMessage = ByteSplit.split(myParseElement.getBytes(), splitMode);
            } catch (HBCISyntaxException e) {
                throw new ElementParseException(e);
            }
            if (splittedMessage == null) {
                return;
            }
            List<Field> fields = getAllDeclaredFieldsFromAllSuperClasses(myParseElement.getClass());

            List<Field> usedFields = new LinkedList<Field>();
            for (Field f : fields) {
                f.setAccessible(true);
                if (AbstractElement.getMatchingElementDescription(f, segmentVersion) != null)
                    usedFields.add(f);
            }
            /*
             * Sortiere die Fields anhand der Element-Number
             */
            Collections.sort(usedFields, new FieldComparator(segmentVersion));
            if (myParseElement instanceof DatenElementGruppe) {
                splittedMessage = groupSplittedMessage(splittedMessage, usedFields, segmentVersion);
            }
            for (Field f : usedFields) {
                if (AbstractElement.getMatchingElementDescription(f, segmentVersion) != null) {
                    if (splittedMessage.size() >= AbstractElement.getMatchingElementDescription(f, segmentVersion).number()) {
                        f.setAccessible(true);
                        if (f.getAnnotation(bin.class) != null) {
                            //binary
                            byte[] bytes = splittedMessage.get(AbstractElement.getMatchingElementDescription(f, segmentVersion).number() - 1);
                            f.set(myParseElement, bytes);
                        } else if (f.getAnnotation(num.class) != null) {
                            //int type:
                            String currentElement = null;
                            try {
                                currentElement = new String(splittedMessage.get(AbstractElement.getMatchingElementDescription(f, segmentVersion).number() - 1), "ISO-8859-1");
                            } catch (UnsupportedEncodingException e) {
                                LOG.error("UnsupportedEncodingException", e);
                            }
                            try {
                                f.set(myParseElement, Integer.parseInt(currentElement));
                            } catch (NumberFormatException e) {
                                //Feld bleibt in dem Fall leer
                            }
                        } else if (f.getType().equals(List.class)) {
                            //liste gefunden, diese elemente müssen ganz zum schluss stehen!
                            if (AbstractElement.getMatchingElementDescription(f, segmentVersion).number() != usedFields.size()) {
                                for (int i = AbstractElement.getMatchingElementDescription(f, segmentVersion).number(); i < splittedMessage.size(); i++) {
                                    LOG.error("Could not fill List..");
                                }

                            } else {
                                LOG.debug(new String(splittedMessage.get(AbstractElement.getMatchingElementDescription(f, segmentVersion).number() - 1)));
                                if (splitMode == ByteSplit.MODE_DE) {
                                    Type type = f.getGenericType();
                                    ParameterizedType parameterizedType = (ParameterizedType) type;
                                    type = parameterizedType.getActualTypeArguments()[0];
                                    if (type.equals(String.class)) {
                                        LinkedList<String> linkedList = new LinkedList<>();
                                        for (int i = AbstractElement.getMatchingElementDescription(f, segmentVersion).number() - 1; i < splittedMessage.size(); i++) {
                                            try {
                                                linkedList.add(new String(splittedMessage.get(i), "ISO-8859-1"));
                                            } catch (UnsupportedEncodingException e) {
                                                //ignored
                                            }
                                        }
                                        f.set(myParseElement, linkedList);
                                    } else if (type.equals(byte[].class)) {
                                        f.set(myParseElement, splittedMessage.subList(AbstractElement.getMatchingElementDescription(f, segmentVersion).number(), splittedMessage.size()));
                                    }
                                }
                            }
                        } else if(f.getType().equals(UnterstuetzteCamtMessages.class)) {
                            byte[] content = splittedMessage.get(AbstractElement.getMatchingElementDescription(f, segmentVersion).number() - 1);

                            UnterstuetzteCamtMessages unterstuetzteCamtMessages = new UnterstuetzteCamtMessages(content);
                            f.set(myParseElement, unterstuetzteCamtMessages);

                        } else if (!f.getType().equals(String.class)) {
                            //new DEGs
                            Class[] constructorParams = new Class[]{byte[].class};
                            java.lang.reflect.Constructor constructor = f.getType().getConstructor(constructorParams);
                            Object[] messageObject = new Object[]{splittedMessage.get(AbstractElement.getMatchingElementDescription(f, segmentVersion).number() - 1)};

                            f.set(myParseElement, constructor.newInstance(messageObject));

                        } else {
                            String currentElement = null;
                            try {
                                currentElement = new String(splittedMessage.get(AbstractElement.getMatchingElementDescription(f, segmentVersion).number() - 1), "ISO-8859-1");
                            } catch (UnsupportedEncodingException e) {
                                LOG.error("UnsupportedEncodingException", e);
                            }
                            currentElement = removeEscapeSymbols(currentElement);
                            f.set(myParseElement, currentElement);
                        }
                    } /*else {
                        throw new ElementParseException();
                    }   */

                }
            }
        } catch (IllegalAccessException e) {
            LOG.error("Illegal access while filling data elements: ", e);
        } catch (NoSuchMethodException e) {
            LOG.error("String constructor not found while filling data elements: ", e);
        } catch (InstantiationException e) {
            LOG.error("Problem with instantiating data element group: ", e);
        } catch (InvocationTargetException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private String removeEscapeSymbols(String currentElement) {
        currentElement = currentElement.replaceAll("\\?\\:", ":");
        currentElement = currentElement.replaceAll("\\?\\+", "+");
        currentElement = currentElement.replaceAll("\\?\\'", "'");
        currentElement = currentElement.replaceAll("\\?\\@", "@");
        currentElement = currentElement.replaceAll("\\?\\?", "?");
        return currentElement;
    }

    public int getSegmentVersion() throws ElementParseException {
        if (myParseElement instanceof Segment) {
            byte[] data = myParseElement.getBytes();
            int segmentVersion = 0;
            int dataElementCounter = 0;
            for (int i = 0; i < data.length; i++) {
                if (dataElementCounter > 1) {
                    if (data[i] == ':' || data[i] == '+') {
                        break;
                    }
                    segmentVersion = segmentVersion * 10 + (data[i] - 48);
                }
                if (data[i] == ':') {
                    dataElementCounter++;
                }
            }
            if (segmentVersion > 999 || segmentVersion < 0) {
                //parse error or other issue
                throw new ElementParseException("could not get segmentversion.");
            }
            //LOG.debug("Found Segmentversion: "+segmentVersion);
            return segmentVersion;
        }
        return 0;
    }

    private List<byte[]> groupSplittedMessage(List<byte[]> splittedMessage, List<Field> usedFields, int segmentVersion) {
        List<byte[]> elements = new LinkedList<byte[]>();
        int offset = 0;
        for (Field f : usedFields) {
            byte[] degBytes;
            if (AbstractElement.getMatchingElementDescription(f, segmentVersion).status() == ElementDescription.StatusCode.C
                    || AbstractElement.getMatchingElementDescription(f, segmentVersion).status() == ElementDescription.StatusCode.O) {
                if (offset >= splittedMessage.size()) {
                    continue;
                }
            }
            if (List.class.isAssignableFrom(f.getType()) && usedFields.size() == 1) {
                return splittedMessage;
            } else
                if (DatenElementGruppe.class.isAssignableFrom(f.getType())) {
                List<byte[]> degElements = new ArrayList<byte[]>();
                int elementCount = getElementCount(f.getType(), segmentVersion);
                for (int i = offset; i < offset + elementCount; i++) {
                    degElements.add(splittedMessage.get(i));
                }
                degBytes = combineArraysInList(degElements);
                offset += elementCount - 1;
            } else {
                degBytes = splittedMessage.get(offset);
            }
            elements.add(degBytes);
            offset++;
        }
        return elements;
    }


    private byte[] combineArraysInList(List<byte[]> deg) {
        byte[] result;
        int resultSize = 0;
        for (byte[] b : deg) {
            resultSize += b.length + 1;
        }
        resultSize -= 1;
        result = new byte[resultSize];
        int offset = 0;
        for (int i = 0; i < deg.size(); i++) {
            if (i < deg.size() - 1) {
                System.arraycopy(deg.get(i), 0, result, offset, deg.get(i).length);
                offset += deg.get(i).length;
                result[offset++] = 0x3A;
            } else {
                System.arraycopy(deg.get(i), 0, result, offset, deg.get(i).length);
            }
        }
        return result;
    }

    /**
     * Über diese Methode erhält man alle Fields der übergebenen Klasse und deren Superklasse zurück in einer Map zurück.
     *
     * @param clazz Klassenobjekt
     * @return HashMap, in welcher alle enthaltenen Fields aufgelistet sind
     */
    public Map<String, Field> getFieldsFromClassAndSuperClass(Class<?> clazz) {
        /**
         * Hack: to get all fields from the class up to net.petafuel.mobile.objectbroker.commondataacesslayer.SerializableObject
         * we have to go over all super classes and read the declared fields.
         */
        Map<String, Field> fieldsFromClassAndSuper = new HashMap<String, Field>();
        Class<?> superClass = clazz;
        while (!AbstractElement.class.equals(superClass) && superClass != null) {
            for (Field f : superClass.getDeclaredFields()) {
                fieldsFromClassAndSuper.put(f.getName(), f);
            }
            superClass = superClass.getSuperclass();
        }
        return fieldsFromClassAndSuper;
    }

    /**
     * Über diese Methoden werden alle deklarierten Fields geholt.
     * Nicht nur die der ersten Superklasse, sondern die aller Superklassen bis hin zum
     * net.petafuel.mobile.objectbroker.commondataacesslayer.SerializableObject
     *
     * @param clazz Klasse
     * @return LinklList mit allen Fields
     */
    public LinkedList<Field> getAllDeclaredFieldsFromAllSuperClasses(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        LinkedList<Field> fieldsFromClassAndSuper = new LinkedList<Field>();
        fieldsFromClassAndSuper.addAll(Arrays.asList(fields));
        /**
         * Hack: to get all fields from the class up to net.petafuel.mobile.objectbroker.commondataacesslayer.SerializableObject
         * we have to go over all super classes and read the declared fields.
         */
        Class<?> superClass = clazz.getSuperclass();
        while (!AbstractElement.class.equals(superClass) && superClass != null) {
            fields = superClass.getDeclaredFields();
            fieldsFromClassAndSuper.addAll(Arrays.asList(fields));
            superClass = superClass.getSuperclass();
        }
        return fieldsFromClassAndSuper;
    }

}
