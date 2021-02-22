package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Description: Ja/Nein
 * <p/>
 * Format: J bzw. N (in Großbuchstaben) Hat das DE den Status „Kann“, so gilt bei Auslassung der Standardwert „N“.
 */
@Constraint(validatedBy = JnValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface jn {
    String message() default "ist weder J noch N";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
