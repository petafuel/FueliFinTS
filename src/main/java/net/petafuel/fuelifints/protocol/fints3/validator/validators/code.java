package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface code {

    String message() default "enthält keinen gültigen code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] restrictions();
}
