package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Description: ￼Binär
 * <p/>
 * Binäre Daten werden unverändert in den HBCI-Datensatz eingestellt.
 * Eine Umwandlung in eine Zeichendarstellung er- folgt nicht.
 * Es ist zu beachten, dass der HBCI-Basiszeichen- satz für binäre Daten keine Gültigkeit besitzt.
 * Ferner gelten die speziellen Syntaxregeln für binäre Daten
 *<p/>
 */
public class BinValidator implements ConstraintValidator<bin, byte[]> {
    @Override
    public void initialize(bin bin) {

    }

    @Override
    public boolean isValid(byte[] bytes, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
