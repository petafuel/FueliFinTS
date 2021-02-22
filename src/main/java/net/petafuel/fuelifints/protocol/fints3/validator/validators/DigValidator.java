package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: 0-9
 * <p/>
 * Zulässig sind lediglich die Ziffern ‘0’ bis ‘9’. Führende Nullen sind zugelassen.
 */
public class DigValidator implements ConstraintValidator<dig, String> {
    @Override
    public void initialize(dig dig) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s==null || s.equals("") || s.matches("^[0-9]+$");
    }
}
