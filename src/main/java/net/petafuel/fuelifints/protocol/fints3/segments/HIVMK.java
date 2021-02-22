package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;

/**
 * Name:  Vormerkposten anfordern
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HIVMK
 * Bezugssegment:  HKVMK
 * Version:  1
 * Anzahl:  n
 * Sender:  Kreditinstitut
 */
public class HIVMK extends Segment{

    @Element(
            description = {@ElementDescription(number = 1)}
    )
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)}
    )
    @bin
    private byte[] nichtGebuchteUmsaetze;

    public HIVMK(byte[] nichtGebuchteUmsaetze, Integer segmentNummer, Integer segmentVersion) {
        super(new byte[0]);
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(getClass()).setSegmentVersion(segmentVersion).setBezugssegment(segmentNummer).build();
        this.nichtGebuchteUmsaetze = SegmentUtil.wrapBinary(nichtGebuchteUmsaetze);
    }
}
