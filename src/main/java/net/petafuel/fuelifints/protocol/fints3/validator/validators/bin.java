package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Description: ￼Binär
 * <p/>
 * Binäre Daten werden unverändert in den HBCI-Datensatz eingestellt.
 * Eine Umwandlung in eine Zeichendarstellung er- folgt nicht.
 * Es ist zu beachten, dass der HBCI-Basiszeichen- satz für binäre Daten keine Gültigkeit besitzt.
 * Ferner gelten die speziellen Syntaxregeln für binäre Daten
 */
@Constraint(validatedBy = BinValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface bin {
    String message() default "enthält keine gültigen Binärdaten";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
