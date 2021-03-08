package net.petafuel.fuelifints.protocol.fints3.segments.deg;


import net.petafuel.fuelifints.exceptions.ElementParseException;
import net.petafuel.fuelifints.exceptions.HBCIValidationException;
import net.petafuel.fuelifints.protocol.fints3.segments.AbstractElement;
import net.petafuel.fuelifints.protocol.fints3.segments.MessageElementParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * Repräsentiert eine abstrakte DEG.
 * DEGs beinhalten Dateneletemente.
 */
public abstract class DatenElementGruppe extends AbstractElement {

    private static final Logger LOG = LogManager.getLogger(DatenElementGruppe.class);

    protected byte[] bytes;

    protected MessageElementParser messageElementParser;

    public DatenElementGruppe(byte[] bytes) {
        this.bytes = bytes;
        this.messageElementParser = new MessageElementParser(this);
        try {
            this.parseElement();
        } catch (ElementParseException e) {
            LOG.error("ElementParseException", e);
        }
    }

    public MessageElementParser getMessageElementParser() {
        return messageElementParser;
    }

    public void setMessageElementParser(MessageElementParser messageElementParser) {
        this.messageElementParser = messageElementParser;
    }

    public byte[] getBytes() {
        return bytes;
    }


    @Override
    public void parseElement() throws ElementParseException {
        //build Elements
        if (bytes != null) {
            this.messageElementParser.fillElements();
        }
    }

    @Override
    public boolean validate() throws HBCIValidationException {
        Validator v = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<DatenElementGruppe>> constraintViolations = v.validate(this);

        for (ConstraintViolation<DatenElementGruppe> violation : constraintViolations) {
            LOG.error(violation.getPropertyPath() + " " + violation.getMessage() + " class: " + this.getClass().getSimpleName());
            throw new HBCIValidationException(violation.getMessage());
        }

        Field[] fields = getClass().getDeclaredFields();
        for (Field f : fields) {
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

        LOG.info(">> validation ok");
        return true;
    }

    @Override
    protected char getSeparatorCharacter() {
        return ':';
    }
}
