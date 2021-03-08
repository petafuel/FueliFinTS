package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: FinTS-Basiszeichensatz ohne CR (0D) & LF (0A)
 * <p/>
 * Die FinTS-Basiszeichensätze sind Subsets des ISO 8859. Erlaubt sind nur druckba- re Zeichen des ISO 8859-Zeichensatzes, d.h. die Bereiche X’20’ bis X’7E’ und X’A1’ bis X’FF’
 * <p/>
 */
public class AnValidator implements ConstraintValidator<an, String> {
    @Override
    public void initialize(an an) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        /*
         * Falls ein O/C Feld auftritt ist der String leer, dies ist jedoch korrekt.
         */
        if (s == null || s.isEmpty()) {
            return true;
        }
        return s.matches("^[\u0020-\u007E\u00A1-\u00FF]+$");
    }
}
