package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.exceptions.ElementParseException;
import net.petafuel.fuelifints.exceptions.HBCIValidationException;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.DatenElementGruppe;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Set;

public abstract class Segment extends AbstractElement {

    private static final Logger LOG = LogManager.getLogger(Segment.class);

    protected byte[] bytes;

    protected MessageElementParser myParser = null;

    @Override
    public void parseElement() throws ElementParseException {
        //build Elements
        if (bytes != null) {
            this.myParser.fillElements();
        }
    }

    public int getVersion() throws ElementParseException {
        return this.myParser == null ? 0 : this.myParser.getSegmentVersion();
    }

    @Override
    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean validate() throws HBCIValidationException {
        Validator v = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Segment>> constraintViolations = v.validate(this);

        for (ConstraintViolation<Segment> violation : constraintViolations) {
            LOG.error(violation.getPropertyPath() + " " + violation.getMessage() + " class: " + this.getClass().getSimpleName());
            throw new HBCIValidationException(violation.getMessage());
        }

        Field[] declaredFields = getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            if (DatenElementGruppe.class.isAssignableFrom(f.getType())) {
                try {
                    DatenElementGruppe deg = (DatenElementGruppe) f.get(this);
                    if (deg != null) {
                        deg.validate();
                    }
                } catch (IllegalAccessException e) {
                    LOG.error("Cannot access field {}", f.getName(), e);
                }
            }
        }
        LOG.debug(">> validation ok");
        return true;
    }

    public Segment(byte[] message) {
        bytes = message;
        this.myParser = new MessageElementParser(this);
    }

    public MessageElementParser getMyParser() {
        return myParser;
    }

    public void setMyParser(MessageElementParser myParser) {
        this.myParser = myParser;
    }

    @Override
    protected char getSeparatorCharacter() {
        return '+';
    }

    @Override
    public String toString() {
        try {
            return "Segment{" +
                    "message='" + new String(bytes, "ISO-8859-1") + '\'' +
                    '}';
        } catch (UnsupportedEncodingException e) {
            return "could not convert bytes to ISO-8859-1 (latin 1)";
        }
    }

    public boolean checkDependencies() {
        return true;
    }

    public Segmentkopf getSegmentkopf() {
        Field segmentkopfField = null;
        for (Field f : getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (Segmentkopf.class.equals(f.getType())) {
                segmentkopfField = f;
                break;
            }
        }
        if (segmentkopfField != null) {
            try {
                return (Segmentkopf) segmentkopfField.get(this);
            } catch (IllegalAccessException e) {
                LOG.error("IllegalAccessException", e);
            }
        }
        return null;
    }
}
