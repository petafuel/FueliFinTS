package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Description: Datum
 * <p/>
 * Format: JJJJMMTT
 * Erlaubt sind alle existenten Datumsangaben.<p/>
 */
@Constraint(validatedBy = DatValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface dat {
    String message() default "entspricht nicht dem FinTS-Basiszeichensatz";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
