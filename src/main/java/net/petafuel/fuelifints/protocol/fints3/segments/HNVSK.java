package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.*;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

/**
 * Name: Verschlüsselungskopf
 * Typ: Segment
 * Segmentart: Administration
 * Kennung: HNVSK
 * Sender: Kunde/Kreditinstitut
 */
public class HNVSK extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    private Sicherheitsprofil sicherheitsprofil;

    @Element(
            description = {@ElementDescription(number = 3, length = -3)})
    @code(restrictions = {"4", "998"})   //4 = RDH, 998 = PIN/TAN
    private String sicherheitsfunktion;

    @Element(
            description = {@ElementDescription(number = 4, length = -3)})
    @code(restrictions = {"1", "4"})
    private String sicherheitslieferant;

    @Element(
            description = {@ElementDescription(number = 5)})
    private Sicherheitsidentifikationsdetails sicherheitsidentifikationsdetails;

    @Element(
            description = {@ElementDescription(number = 6)})
    private SicherheitsdatumUndUhrzeit sicherheitsdatumUndUhrzeit;

    @Element(
            description = {@ElementDescription(number = 7)})
    private Verschluesselungsalgorithmus verschluesselungsalgorithmus;

    @Element(
            description = {@ElementDescription(number = 8)})
    private Schluesselname schluesselname;

    @Element(
            description = {@ElementDescription(number = 9)})
    @code(restrictions = {"0", "1", "2", "3", "4", "5", "6", "7", "999"})
    private String komprimierungsfunktion;

    @Element(
            description = {@ElementDescription(number = 10, status = ElementDescription.StatusCode.O)})
    private Zertifikat zertifikat;

    public HNVSK(byte[] message) {
        super(message);
    }

    public Sicherheitsprofil getSicherheitsprofil() {
        return sicherheitsprofil;
    }

    public Verschluesselungsalgorithmus getVerschluesselungsalgorithmus() {
        return verschluesselungsalgorithmus;
    }

    public Schluesselname getSchluesselname() {
        return schluesselname;
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public void setSicherheitsprofil(Sicherheitsprofil sicherheitsprofil) {
        this.sicherheitsprofil = sicherheitsprofil;
    }

    public String getSicherheitsfunktion() {
        return sicherheitsfunktion;
    }

    public void setSicherheitsfunktion(String sicherheitsfunktion) {
        this.sicherheitsfunktion = sicherheitsfunktion;
    }

    public String getSicherheitslieferant() {
        return sicherheitslieferant;
    }

    public void setSicherheitslieferant(String sicherheitslieferant) {
        this.sicherheitslieferant = sicherheitslieferant;
    }

    public Sicherheitsidentifikationsdetails getSicherheitsidentifikationsdetails() {
        return sicherheitsidentifikationsdetails;
    }

    public void setSicherheitsidentifikationsdetails(Sicherheitsidentifikationsdetails sicherheitsidentifikationsdetails) {
        this.sicherheitsidentifikationsdetails = sicherheitsidentifikationsdetails;
    }

    public SicherheitsdatumUndUhrzeit getSicherheitsdatumUndUhrzeit() {
        return sicherheitsdatumUndUhrzeit;
    }

    public void setSicherheitsdatumUndUhrzeit(SicherheitsdatumUndUhrzeit sicherheitsdatumUndUhrzeit) {
        this.sicherheitsdatumUndUhrzeit = sicherheitsdatumUndUhrzeit;
    }

    public void setVerschluesselungsalgorithmus(Verschluesselungsalgorithmus verschluesselungsalgorithmus) {
        this.verschluesselungsalgorithmus = verschluesselungsalgorithmus;
    }

    public void setSchluesselname(Schluesselname schluesselname) {
        this.schluesselname = schluesselname;
    }

    public String getKomprimierungsfunktion() {
        return komprimierungsfunktion;
    }

    public void setKomprimierungsfunktion(String komprimierungsfunktion) {
        this.komprimierungsfunktion = komprimierungsfunktion;
    }

    public Zertifikat getZertifikat() {
        return zertifikat;
    }

    public void setZertifikat(Zertifikat zertifikat) {
        this.zertifikat = zertifikat;
    }
}
