package net.petafuel.fuelifints.model;

import net.petafuel.fuelifints.exceptions.ElementParseException;
import net.petafuel.fuelifints.exceptions.HBCIValidationException;

/**
 * Dieses Interface liefert die Basismethoden, die für jedes Nachrichtenelement nötig sind.
 */
public interface IMessageElement {

    public byte[] getBytes();
    public void parseElement() throws ElementParseException;
    public boolean validate() throws HBCIValidationException;
}
