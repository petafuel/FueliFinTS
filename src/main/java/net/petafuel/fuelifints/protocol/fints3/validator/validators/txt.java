package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import net.petafuel.fuelifints.protocol.fints3.validator.validators.AnValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Description: FinTS-Basiszeichensatz mit CR (0D) & LF (0A)
 * <p/>
 * Die FinTS-Basiszeichensätze sind Subsets des ISO 8859. Erlaubt sind nur druckba- re Zeichen des ISO 8859-Zeichensatzes, d.h. die Bereiche X’20’ bis X’7E’ und X’A1’ bis X’FF’
 */
@Constraint(validatedBy = TxtValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface txt {
    String message() default "entspricht nicht dem vollständigen FinTS-Basiszeichensatz";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
