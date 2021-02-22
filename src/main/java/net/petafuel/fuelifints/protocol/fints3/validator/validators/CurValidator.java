package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Kennzeichen gemäß ISO 4217 (alphabetischer Code) in Großbuchstaben
 */
public class CurValidator implements ConstraintValidator<cur, String> {
    @Override
    public void initialize(cur cur) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
