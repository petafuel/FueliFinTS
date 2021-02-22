package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimValidator implements ConstraintValidator<tim, String> {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");

    @Override
    public void initialize(tim tim) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            simpleDateFormat.parse(s);
        } catch (NumberFormatException e) {
            //Falls das Parsen fehl schlägt dann ist die Zeitangabe falsch
            return false;
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
