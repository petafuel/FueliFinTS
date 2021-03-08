package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: Ja/Nein
 * <p/>
 * Format: J bzw. N (in Großbuchstaben) Hat das DE den Status „Kann“, so gilt bei Auslassung der Standardwert „N“.
 */
public class JnValidator implements ConstraintValidator<jn, String> {
    @Override
    public void initialize(jn jn) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //optionale oder coditional felder müssen möglicherweise dieses feld nicht setzen
        if (s == null || s.equals("")) {
            return true;
        }
        return (s.equals("J") || s.equals("N"));
    }
}
