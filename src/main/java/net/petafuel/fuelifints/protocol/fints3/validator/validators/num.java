package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Description:
 * <p/>
 * Zulässig sind lediglich die Ziffern ‘0’ bis ‘9’. Führende Nullen sind nicht zugelassen.
 */
@Constraint(validatedBy = NumValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface num {
    String message() default "ist keine gültige Zahl";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
