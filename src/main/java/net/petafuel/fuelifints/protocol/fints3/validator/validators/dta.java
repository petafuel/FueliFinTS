package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Description:
 * <p/>
 * Description: DTAUS-Zeichensatz
 * <p/>
 * Es gilt der DTAUS-Zeichensatz mit der entsprechenden Co- dierung.
 */
@Constraint(validatedBy = DtaValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface dta {
    String message() default "entspricht dem DTAUS Zeichensatz";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
