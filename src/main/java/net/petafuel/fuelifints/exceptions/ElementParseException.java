package net.petafuel.fuelifints.exceptions;

/**
 * Exception für Probleme, die beim Parsen einer Nachricht auftreten können
 */

public class ElementParseException extends Exception {

    public ElementParseException(String reason) {
        super(reason);
    }

    public ElementParseException(Exception e) {
        super(e);
    }
}
