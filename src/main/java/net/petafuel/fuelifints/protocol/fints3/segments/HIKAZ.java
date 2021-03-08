package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;

/**
 * Name:  Kontoumsätze rückmelden/Zeitraum
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HIKAZ
 * Bezugssegment:  HKKAZ
 * Version:  6
 * Anzahl:  n
 * Sender:  Kreditinstitut
 */
public class HIKAZ extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @bin
    private byte[] gebuchteUmsaetze;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.O)})
    @bin
    private byte[] nichtGebuchteUmsaetze;

    public HIKAZ(byte[] gebuchteUmsaetze, byte[] nichtGebuchteUmsaetze, int bezugsSegment, int segmentversion) {
        super(new byte[0]);
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(this.getClass()).setSegmentVersion(segmentversion).setBezugssegment(bezugsSegment).build();
        this.gebuchteUmsaetze = SegmentUtil.wrapBinary(gebuchteUmsaetze);
        this.nichtGebuchteUmsaetze = SegmentUtil.wrapBinary(nichtGebuchteUmsaetze);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public byte[] getNichtGebuchteUmsaetze() {
        return nichtGebuchteUmsaetze;
    }

    public void setNichtGebuchteUmsaetze(byte[] nichtGebuchteUmsaetze) {
        this.nichtGebuchteUmsaetze = nichtGebuchteUmsaetze;
    }

    public byte[] getGebuchteUmsaetze() {
        return gebuchteUmsaetze;
    }

    public void setGebuchteUmsaetze(byte[] gebuchteUmsaetze) {
        this.gebuchteUmsaetze = gebuchteUmsaetze;
    }
}
