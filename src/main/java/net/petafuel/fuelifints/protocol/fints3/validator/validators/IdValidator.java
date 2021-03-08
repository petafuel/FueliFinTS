package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: Identifikation
 * <p/>
 * dient der eindeutigen Kennzeichnung von Ob- jekten (z.B. Benutzerkennung, Kontonummer)
 */
public class IdValidator implements ConstraintValidator<id, String> {

    @Override
    public void initialize(id id) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        /*
         * Falls ein O/C Feld auftritt ist der String leer, dies ist jedoch korrekt.
         */
        return s == null || (s.matches("^[\u0020-\u007E\u00A1-\u00FF]+$") && s.length() < 31) || s.length() == 0;
    }
}
