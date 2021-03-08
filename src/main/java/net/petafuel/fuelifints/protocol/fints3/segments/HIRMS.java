package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

import java.util.LinkedList;
import java.util.List;

/**
 * Name: Rückmeldungen zu Segmenten
 * Typ: Segment
 * Sender: Kreditinstitut
 */
public class HIRMS extends Segment {
    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    private List<Rueckmeldung> rueckmeldung;

    public HIRMS(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public List<Rueckmeldung> getRueckmeldung() {
        return rueckmeldung;
    }

    public void setRueckmeldung(List<Rueckmeldung> rueckmeldung) {
        this.rueckmeldung = rueckmeldung;
    }

    public void addRueckmeldung(Rueckmeldung rueckmeldung) {
        if (this.rueckmeldung == null) {
            this.rueckmeldung = new LinkedList<Rueckmeldung>();
        }
        if (rueckmeldung != null) {
            this.rueckmeldung.add(rueckmeldung);
        }
    }
}
