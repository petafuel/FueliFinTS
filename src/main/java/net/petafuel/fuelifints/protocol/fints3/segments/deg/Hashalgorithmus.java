package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

/**
 * Hashalgorithmus
 * Angaben  zu  einem  kryptographischen  Algorithmus,  seinen  Operations-
 * modus, sowie dessen Einsatz.
 */
public class Hashalgorithmus extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -3)})
    @code(restrictions = {"1"})
    private String hashalgorithmusverwendung;

    @Element(
            description = {@ElementDescription(number = 2, length = -3)})
    @code(restrictions = {"1", "2", "3", "4", "5", "6", "999"})
    private String hashalgorithmus;

    @Element(
            description = {@ElementDescription(number = 3, length = -3)})
    @code(restrictions = {"1"})
    private String hashalgorithmusparameterbezeichner;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O, length = -512)})
    @bin
    private byte[] hashalgorithmusparameterwert;

    public Hashalgorithmus(byte[] degString) {
        super(degString);
    }

    public String getHashalgorithmusverwendung() {
        return hashalgorithmusverwendung;
    }

    public void setHashalgorithmusverwendung(String hashalgorithmusverwendung) {
        this.hashalgorithmusverwendung = hashalgorithmusverwendung;
    }

    public String getHashalgorithmus() {
        return hashalgorithmus;
    }

    public void setHashalgorithmus(String hashalgorithmus) {
        this.hashalgorithmus = hashalgorithmus;
    }

    public String getHashalgorithmusparameterbezeichner() {
        return hashalgorithmusparameterbezeichner;
    }

    public void setHashalgorithmusparameterbezeichner(String hashalgorithmusparameterbezeichner) {
        this.hashalgorithmusparameterbezeichner = hashalgorithmusparameterbezeichner;
    }

    public byte[] getHashalgorithmusparameterwert() {
        return hashalgorithmusparameterwert;
    }

    public void setHashalgorithmusparameterwert(byte[] hashalgorithmusparameterwert) {
        this.hashalgorithmusparameterwert = hashalgorithmusparameterwert;
    }
}
