package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Description: Identifikation
 * <p/>
 * dient der eindeutigen Kennzeichnung von Ob- jekten (z.B. Benutzerkennung, Kontonummer)
 */
@Constraint(validatedBy = IdValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface id {
    String message() default "ist keine gültige Identifikation";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
