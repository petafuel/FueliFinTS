package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: Datum
 * <p/>
 * Format: JJJJMMTT
 * Erlaubt sind alle existenten Datumsangaben.<p/>
 */
public class DatValidator implements ConstraintValidator<dat, String> {
    @Override
    public void initialize(dat dat) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        /*
         * Falls ein O/C Feld auftritt ist der String leer, dies ist jedoch korrekt.
         */
        if (s == null || s.isEmpty()) {
            return true;
        }
        if (s.length() != 8)
            return false;
        return s.matches("^[1-9]+[0-9]*$");
    }
}
