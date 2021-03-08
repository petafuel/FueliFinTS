package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.txt;

/**
 * Name:    Kreditinstitutsmeldung
 * Typ:  Segment
 * Segmentart:    Administration
 * Kennung:     HIKIM
 * Bezugssegment:  -
 * Sender:  Kreditinstitut
 */
public class HIKIM extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = -35)})
    @an
    private String betreff;

    @Element(
            description = {@ElementDescription(number = 3, length = -2048)})
    @txt
    private String freitextmeldung;

    public HIKIM(String betreff, String freitextmeldung) {
        super(new byte[0]);
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(getClass()).setSegmentVersion(2).build();
        this.betreff = betreff;
        this.freitextmeldung = freitextmeldung;
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public String getBetreff() {
        return betreff;
    }

    public void setBetreff(String betreff) {
        this.betreff = betreff;
    }

    public String getFreitextmeldung() {
        return freitextmeldung;
    }

    public void setFreitextmeldung(String freitextmeldung) {
        this.freitextmeldung = freitextmeldung;
    }
}
