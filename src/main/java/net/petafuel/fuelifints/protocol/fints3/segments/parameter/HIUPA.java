package net.petafuel.fuelifints.protocol.fints3.segments.parameter;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.segments.Segment;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Name: Userparameter allgemein
 * Typ: Segment
 * Segmentart:  Administration
 * Kennung:   HIUPA
 * Bezugssegment:   HKVVB
 * Version:  4
 * Sender: Kreditinstitut
 * <p/>
 * FinTS 3.0: Formals, E.2
 * Beispiel:     HIUPA:14:3:7+12345+4+0+Herr Meier'
 */
public class HIUPA extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @id
    private String benutzerkennung;

    @Element(
            description = {@ElementDescription(number = 3, length = -3)})
    @num
    private Integer upd_version;

    @Element(
            description = {@ElementDescription(number = 3, length = 1)})
    @code(restrictions = {"0", "1"})
    private Integer upd_verwendung;

    @Element(
            description = {@ElementDescription(number = 4, length = -35, status = ElementDescription.StatusCode.O)})
    @an
    private String benutzername;

    @Element(
            description = {@ElementDescription(number = 5, length = -2048, status = ElementDescription.StatusCode.O)})
    @an
    private String erweiterung_allgemein;


    public HIUPA(String userId, int upd_version, String userName, int bezugsSegment) {
        super(new byte[0]);

        /*
        this.segmentkopf = new Segmentkopf(
                ("HIUPA:0:4:" +
                        bezugsSegment).getBytes()
        );
        */
        this.segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(HIUPA.class).setSegmentVersion(4).setBezugssegment(bezugsSegment).build();

        this.upd_verwendung = 0;    //0: Die nicht aufgeführten Geschäftsvorfälle sind gesperrt (die aufgeführten Geschäftsvorfälle sind zugelassen).
        //1: Bei den nicht aufgeführten Geschäftsvorfällen ist anhand der UPD keine Aussage darüber möglich, ob diese erlaubt oder gesperrt sind. Diese Prü- fung kann nur online vom Kreditinstitutssystem vorgenommen werden.
        this.benutzerkennung = userId;
        this.upd_version = upd_version;
        this.benutzername = userName;
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public String getBenutzerkennung() {
        return benutzerkennung;
    }

    public void setBenutzerkennung(String benutzerkennung) {
        this.benutzerkennung = benutzerkennung;
    }

    public Integer getUpd_version() {
        return upd_version;
    }

    public void setUpd_version(Integer upd_version) {
        this.upd_version = upd_version;
    }

    public Integer getUpd_verwendung() {
        return upd_verwendung;
    }

    public void setUpd_verwendung(Integer upd_verwendung) {
        this.upd_verwendung = upd_verwendung;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getErweiterung_allgemein() {
        return erweiterung_allgemein;
    }

    public void setErweiterung_allgemein(String erweiterung_allgemein) {
        this.erweiterung_allgemein = erweiterung_allgemein;
    }
}
