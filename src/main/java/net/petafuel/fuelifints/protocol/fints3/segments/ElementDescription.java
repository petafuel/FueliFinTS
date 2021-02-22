package net.petafuel.fuelifints.protocol.fints3.segments;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ElementDescription {

    enum StatusCode {M, O, C}

    int number();

    StatusCode status() default StatusCode.M;

    int length() default 0;

    int segmentVersion() default 0;
}
