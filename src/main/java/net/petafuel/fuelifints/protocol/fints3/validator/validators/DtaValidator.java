package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: DTAUS-Zeichensatz
 * <p/>
 * Es gilt der DTAUS-Zeichensatz mit der entsprechenden Co- dierung.
 */
public class DtaValidator implements ConstraintValidator<dta, String> {
    @Override
    public void initialize(dta dta) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //optionale Felder sind nicht zwangsweise belegt
        if (s == null || s.isEmpty()) {
            return true;
        }
        return s.matches("^[\u0020-\u007E\u00A1-\u00FF\r\n]+$");
    }
}
