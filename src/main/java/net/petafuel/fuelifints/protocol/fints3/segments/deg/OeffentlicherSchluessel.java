package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

/**
 * Öffentlicher Schlüssel
 * Information,  die  beim  RAH-/RDH-Key-Management  zum  Transport  des  öf-
 * fentlichen  Schlüssels  zwischen  Kunde  und  Kreditinstitut  bzw.  umgekehrt
 * dient.
 */
public class OeffentlicherSchluessel extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -3)})
    @code(restrictions = {"5", "6"})
    private String verwendungszweckOeffentlicherSchluessel;

    @Element(
            description = {@ElementDescription(number = 2, length = -3)})
    @code(restrictions = {"2", "16", "17", "18", "19", "999"})
    private String opertationsmodusKodiert;

    @Element(
            description = {@ElementDescription(number = 3, length = -3)})
    @code(restrictions = {"10"})
    private String verfahrenBenutzer;

    @Element(
            description = {@ElementDescription(number = 4, length = -512)})
    @bin
    private byte[] modulus;

    @Element(
            description = {@ElementDescription(number = 5, length = -3)})
    @code(restrictions = {"12"})
    private String bezeichnerModulus;

    @Element(
            description = {@ElementDescription(number = 6, length = -512)})
    @bin
    private byte[] exponent;

    @Element(
            description = {@ElementDescription(number = 7, length = -3)})
    @code(restrictions = {"13"})
    private String bezeichnerExponent;

    public OeffentlicherSchluessel(byte[] degString) {
        super(degString);
    }

    public String getVerwendungszweckOeffentlicherSchluessel() {
        return verwendungszweckOeffentlicherSchluessel;
    }

    public void setVerwendungszweckOeffentlicherSchluessel(String verwendungszweckOeffentlicherSchluessel) {
        this.verwendungszweckOeffentlicherSchluessel = verwendungszweckOeffentlicherSchluessel;
    }

    public String getOpertationsmodusKodiert() {
        return opertationsmodusKodiert;
    }

    public void setOpertationsmodusKodiert(String opertationsmodusKodiert) {
        this.opertationsmodusKodiert = opertationsmodusKodiert;
    }

    public String getVerfahrenBenutzer() {
        return verfahrenBenutzer;
    }

    public void setVerfahrenBenutzer(String verfahrenBenutzer) {
        this.verfahrenBenutzer = verfahrenBenutzer;
    }

    public byte[] getModulus() {
        return modulus;
    }

    public void setModulus(byte[] modulus) {
        this.modulus = modulus;
    }

    public String getBezeichnerModulus() {
        return bezeichnerModulus;
    }

    public void setBezeichnerModulus(String bezeichnerModulus) {
        this.bezeichnerModulus = bezeichnerModulus;
    }

    public byte[] getExponent() {
        return exponent;
    }

    public void setExponent(byte[] exponent) {
        this.exponent = exponent;
    }

    public String getBezeichnerExponent() {
        return bezeichnerExponent;
    }

    public void setBezeichnerExponent(String bezeichnerExponent) {
        this.bezeichnerExponent = bezeichnerExponent;
    }

    @Override
    public String toString() {
        return "OeffentlicherSchluessel{" +
                "verwendungszweckOeffentlicherSchluessel='" + verwendungszweckOeffentlicherSchluessel + '\'' +
                ", opertationsmodusKodiert='" + opertationsmodusKodiert + '\'' +
                ", verfahrenBenutzer='" + verfahrenBenutzer + '\'' +
                ", modulus=" + modulus.length +
                ", bezeichnerModulus='" + bezeichnerModulus + '\'' +
                ", exponent=" + exponent.length +
                ", bezeichnerExponent='" + bezeichnerExponent + '\'' +
                '}';
    }
}
