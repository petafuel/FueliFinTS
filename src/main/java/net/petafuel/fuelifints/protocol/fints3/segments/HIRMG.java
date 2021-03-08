package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

/**
 * Name: Rückmeldung zur Gesamtnachricht
 * Typ: Segment
 * Sender: Kreditinstitut
 */
public class HIRMG extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    private Rueckmeldung rueckmeldung;

    public HIRMG(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public Rueckmeldung getRueckmeldung() {
        return rueckmeldung;
    }

    public void setRueckmeldung(Rueckmeldung rueckmeldung) {
        this.rueckmeldung = rueckmeldung;
    }
}
