package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: 0-9
 * <p/>
 * Zulässig sind lediglich die Ziffern ‘0’ bis ‘9’. Führende Nullen sind nicht zugelassen.
 */
public class NumValidator implements ConstraintValidator<num, Integer> {
    @Override
    public void initialize(num num) {

    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
