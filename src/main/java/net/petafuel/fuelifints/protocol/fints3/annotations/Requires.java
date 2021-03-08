package net.petafuel.fuelifints.protocol.fints3.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Requires {
    enum Requirement {
        EXECUTION_ALLOWED,
        KUNDENSYSTEM_ID,
        USER_IDENTIFIED,
        TAN
    }

    Requirement[] value();
}
