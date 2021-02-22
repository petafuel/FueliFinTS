package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Kennzeichen gemäß ISO 4217 (alphabetischer Code) in Großbuchstaben
 */
@Constraint(validatedBy = CurValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface cur {
    String message() default "ist keine gültige Währung";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
