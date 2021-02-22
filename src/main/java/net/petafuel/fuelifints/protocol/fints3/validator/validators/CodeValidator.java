package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodeValidator implements ConstraintValidator<code, String> {

    private code code;

    @Override
    public void initialize(code code) {
        this.code = code;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (String restriction : code.restrictions()) {
            if (s.equals(restriction)) {
                return true;
            }
        }
        return false;
    }
}
