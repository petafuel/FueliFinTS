package net.petafuel.fuelifints.protocol.fints3.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ApplicantAccount sollte in IExecutableElements bei der Kontoverbindung des Auftraggebers gesetzt werden.
 * Anhand dieser Annotation wird geprüft, ob die Ausführung des entsprechenden Segments für die entsprechende
 * Kontoverbindung erlaubt ist.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicantAccount {
}
