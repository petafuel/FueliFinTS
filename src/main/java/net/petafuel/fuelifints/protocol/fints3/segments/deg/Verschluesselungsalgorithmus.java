package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

/**
 * Verschlüsselungsalgorithmus
 * Angaben zum kryptographischen Algorithmus, zu seinem Operationsmodus,
 * so wie zu dessen Einsatz, in diesem Fall für die Nachrichtenverschlüsselung.
 */
public class Verschluesselungsalgorithmus extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -3)})
    @code(restrictions = {"2"})
    private String verwedungVerschluesselungsalgorithmusKodiert;

    @Element(
            description = {@ElementDescription(number = 2, length = -3)})
    @code(restrictions = {"2", "16", "17", "18", "19"})
    private String operationsmodusKodiert;

    @Element(
            description = {@ElementDescription(number = 3, length = -3)})
    @code(restrictions = {"13", "14"})
    private String verschluesselungsalgorithmusKodiert;

    @Element(
            description = {@ElementDescription(number = 4, length = -512)})
    @bin
    private byte[] wertAlgorithmusparameterSchluessel;

    @Element(
            description = {@ElementDescription(number = 5, length = -3)})
    @code(restrictions = {"5", "6"})
    private String bezeichnerAlgorithmusparameterSchluessel;

    @Element(
            description = {@ElementDescription(number = 6, length = -3)})
    @code(restrictions = {"1"})
    private String bezeichnerAlgorithmusparameterIV;

    @Element(
            description = {@ElementDescription(number = 7, status = ElementDescription.StatusCode.O, length = -512)})
    @bin
    private byte[] wertAlgorithmusparameterIV;

    public Verschluesselungsalgorithmus(byte[] degString) {
        super(degString);
    }

    public byte[] getWertAlgorithmusparameterSchluessel() {
        return wertAlgorithmusparameterSchluessel;
    }
}
