package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Name: Synchronisierungsantwortnachricht
 * Typ: Segment
 * Sender: Kreditinstitut
 */
public class HISYN extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = -30)})
    @id
    private String kundensystemId;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.C, length = -4)})
    @num
    private Integer nachrichtennummer;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C, length = -16)})
    @num
    private Integer sicherheitsreferenznummerSignierschluessel;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C, length = -16)})
    @num
    private Integer sicherheitsreferenznummerDigitaleSignatur;

    public HISYN(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public String getKundensystemId() {
        return kundensystemId;
    }

    public void setKundensystemId(String kundensystemId) {
        this.kundensystemId = kundensystemId;
    }

    public Integer getNachrichtennummer() {
        return nachrichtennummer;
    }

    public void setNachrichtennummer(Integer nachrichtennummer) {
        this.nachrichtennummer = nachrichtennummer;
    }

    public Integer getSicherheitsreferenznummerSignierschluessel() {
        return sicherheitsreferenznummerSignierschluessel;
    }

    public void setSicherheitsreferenznummerSignierschluessel(Integer sicherheitsreferenznummerSignierschluessel) {
        this.sicherheitsreferenznummerSignierschluessel = sicherheitsreferenznummerSignierschluessel;
    }

    public Integer getSicherheitsreferenznummerDigitaleSignatur() {
        return sicherheitsreferenznummerDigitaleSignatur;
    }

    public void setSicherheitsreferenznummerDigitaleSignatur(Integer sicherheitsreferenznummerDigitaleSignatur) {
        this.sicherheitsreferenznummerDigitaleSignatur = sicherheitsreferenznummerDigitaleSignatur;
    }
}
