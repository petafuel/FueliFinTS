package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;

/**
 * Name: Verschlüsselte Daten
 * Typ: Segment
 * Segmentart: Administration
 * Kennung: HNVSD
 * Sender: Kunde/Kreditinstitut
 */
public class HNVSD extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = 0)})
    @bin
    private byte[] verschluesselte_daten;

    public HNVSD(byte[] message) {
        super(message);
    }

    public byte[] getVerschluesselteDaten() {
        return verschluesselte_daten;
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }


    public void setVerschluesselteDaten(byte[] verschluesselte_daten) {
        this.verschluesselte_daten = verschluesselte_daten;
    }
}
