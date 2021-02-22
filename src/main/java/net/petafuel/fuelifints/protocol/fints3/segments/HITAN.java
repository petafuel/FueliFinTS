package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.GueltigkeitsdatumUndUhrzeitChallenge;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

/**
 * Name:  Zwei-Schritt-TAN-Einreichung Rückmeldung
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HITAN
 * Bezugssegment:  HKTAN
 * Segmentversion:  1
 * Anzahl:  1
 * Sender:  Kreditinstitut
 */
public class HITAN extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = 1)})
    @code(restrictions = {"1", "2", "3", "4"})
    private String tanProzess;

    @Element(
            description = {@ElementDescription(number = 3, length = -256)})
    @bin
    private byte[] auftragsHashwert;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C, length = -35)})
    @an
    private String auftragsreferenz;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C, length = -256)})
    @an
    private String challenge;


    @Element(
        description = {
            @ElementDescription(number = 6, status = ElementDescription.StatusCode.C, segmentVersion = 4),
            @ElementDescription(number = 6, status = ElementDescription.StatusCode.C, segmentVersion = 5),
            @ElementDescription(number = 6, status = ElementDescription.StatusCode.C, segmentVersion = 6)
        }
    )
    @bin
    private byte[] challengeHHD_UC;

    @Element(
        description = {
                @ElementDescription(number = 6, status = ElementDescription.StatusCode.O, segmentVersion = 1),
                @ElementDescription(number = 6, status = ElementDescription.StatusCode.O, segmentVersion = 2),
                @ElementDescription(number = 6, status = ElementDescription.StatusCode.O, segmentVersion = 3),
                @ElementDescription(number = 7, status = ElementDescription.StatusCode.O, segmentVersion = 4),
                @ElementDescription(number = 7, status = ElementDescription.StatusCode.O, segmentVersion = 5),
                @ElementDescription(number = 7, status = ElementDescription.StatusCode.O, segmentVersion = 6)
        }
    )
    private GueltigkeitsdatumUndUhrzeitChallenge gueltigkeitsdatumUndUhrzeitChallenge;

    @Element(
        description = {
            @ElementDescription(number = 7, status = ElementDescription.StatusCode.C, length = -20, segmentVersion = 1),
            @ElementDescription(number = 7, status = ElementDescription.StatusCode.C, length = -20, segmentVersion = 2),
            @ElementDescription(number = 7, status = ElementDescription.StatusCode.C, length = -20, segmentVersion = 3),
            @ElementDescription(number = 8, status = ElementDescription.StatusCode.C, length = -20, segmentVersion = 4),
            @ElementDescription(number = 8, status = ElementDescription.StatusCode.C, length = -20, segmentVersion = 5)
        }
    )
    @an
    private String tanListennummer;      //Muss nur gesetzt werden, wenn das Institut mehr als eine aktive Tan Liste unterstützt

    @Element(
            description = {@ElementDescription(number = 8, status = ElementDescription.StatusCode.C, length = -99, segmentVersion = 1)})
    @an
    private String tanZusatzinformationen;

    @Element(
            description = {@ElementDescription(number = 8, status = ElementDescription.StatusCode.C, length = -99, segmentVersion = 2),
                    @ElementDescription(number = 8, status = ElementDescription.StatusCode.C, length = -99, segmentVersion = 3),
                    @ElementDescription(number = 9, status = ElementDescription.StatusCode.C, length = -99, segmentVersion = 4),
                    @ElementDescription(number = 9, status = ElementDescription.StatusCode.C, length = -99, segmentVersion = 5)})
    @an
    private String ben;  //Wird nur bei Tan Prozess = 2 gesetzt

    @Element(
        description = {
            @ElementDescription(number = 9, status = ElementDescription.StatusCode.C, length = -32, segmentVersion = 3),
            @ElementDescription(number = 10, status = ElementDescription.StatusCode.C, length = -32, segmentVersion = 4),
            @ElementDescription(number = 10, status = ElementDescription.StatusCode.C, length = -32, segmentVersion = 5),
            @ElementDescription(number = 8, status = ElementDescription.StatusCode.C, length = -32, segmentVersion = 6)
        }
    )
    @an
    private String bezeichnungDesTanMediums;

    public HITAN(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public String getTanProzess() {
        return tanProzess;
    }

    public void setTanProzess(String tanProzess) {
        this.tanProzess = tanProzess;
    }

    public byte[] getAuftragsHashwert() {
        return auftragsHashwert;
    }

    public void setAuftragsHashwert(byte[] auftragsHashwert) {
        this.auftragsHashwert = auftragsHashwert;
    }

    public String getAuftragsreferenz() {
        return auftragsreferenz;
    }

    public void setAuftragsreferenz(String auftragsreferenz) {
        this.auftragsreferenz = auftragsreferenz;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public GueltigkeitsdatumUndUhrzeitChallenge getGueltigkeitsdatumUndUhrzeitChallenge() {
        return gueltigkeitsdatumUndUhrzeitChallenge;
    }

    public void setGueltigkeitsdatumUndUhrzeitChallenge(GueltigkeitsdatumUndUhrzeitChallenge gueltigkeitsdatumUndUhrzeitChallenge) {
        this.gueltigkeitsdatumUndUhrzeitChallenge = gueltigkeitsdatumUndUhrzeitChallenge;
    }

    public String getTanListennummer() {
        return tanListennummer;
    }

    public void setTanListennummer(String tanListennummer) {
        this.tanListennummer = tanListennummer;
    }

    public String getTanZusatzinformationen() {
        return tanZusatzinformationen;
    }

    public void setTanZusatzinformationen(String tanZusatzinformationen) {
        this.tanZusatzinformationen = tanZusatzinformationen;
    }
}
