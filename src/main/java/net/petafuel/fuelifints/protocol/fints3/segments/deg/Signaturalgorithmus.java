package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

/**
 * Signaturalgorithmus
 * Angaben zum kryptographischen Algorithmus, zu seinem Operationsmodus,
 * so wie zu dessen Einsatz, in diesem Fall für die Signaturbildung über DDV
 * bzw. RAH / RDH.
 */
public class Signaturalgorithmus extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -3)})
    @code(restrictions = {"6"})
    private String signaturalgorithmusverwendung;

    @Element(
            description = {@ElementDescription(number = 2, length = -3)})
    @code(restrictions = {"1", "10"})
    private String signaturalgorithmus;

    @Element(
            description = {@ElementDescription(number = 3, length = -3)})
    @code(restrictions = {"16", "17", "18", "19", "999"})
    private String operationsmodus;

    public Signaturalgorithmus(byte[] degString) {
        super(degString);
    }
}
