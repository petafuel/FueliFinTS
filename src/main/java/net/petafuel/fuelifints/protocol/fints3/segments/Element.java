package net.petafuel.fuelifints.protocol.fints3.segments;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Element {

    ElementDescription[] description();
}
