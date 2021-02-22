package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TimValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface tim {

    String message() default "entspricht nicht dem FinTS-Basiszeichensatz";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
