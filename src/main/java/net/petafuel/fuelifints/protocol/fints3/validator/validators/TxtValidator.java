package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: FinTS-Basiszeichensatz mit CR (0D) & LF (0A)
 * <p/>
 * Die FinTS-Basiszeichensätze sind Subsets des ISO 8859. Erlaubt sind nur druckba- re Zeichen des ISO 8859-Zeichensatzes, d.h. die Bereiche X’20’ bis X’7E’ und X’A1’ bis X’FF’
 */
public class TxtValidator implements ConstraintValidator<txt, String> {
    @Override
    public void initialize(txt txt) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("^[\u0020-\u007E\u00A1-\u00FF\r\n]+$");
    }
}
