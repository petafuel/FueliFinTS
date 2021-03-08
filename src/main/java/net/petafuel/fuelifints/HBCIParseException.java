package net.petafuel.fuelifints;

import net.petafuel.fuelifints.exceptions.HBCISyntaxException;

public class HBCIParseException extends Exception {
    public HBCIParseException(String reason) {
        super(reason);
    }

    public HBCIParseException(Exception e) {
        super(e);
    }
}
