package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungZvInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

import java.util.LinkedList;
import java.util.List;

/**
 * Name:  SEPA-Kontoverbindung rückmelden
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HISPA
 * Bezugssegment:  HKSPA
 * Version:  1
 * Anzahl:  1
 * Sender:  Kreditinstitut
 */
public class HISPA extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.O)})
    private List<KontoverbindungZvInternational> sepaKontoverbindungen;

    public HISPA(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public List<KontoverbindungZvInternational> getSepaKontoverbindungen() {
        return sepaKontoverbindungen;
    }

    public void setSepaKontoverbindungen(List<KontoverbindungZvInternational> sepaKontoverbindungen) {
        this.sepaKontoverbindungen = sepaKontoverbindungen;
    }

    public void addSepaKontoverbindung(KontoverbindungZvInternational sepaKontoverbindung) {
        if (this.sepaKontoverbindungen == null) {
            this.sepaKontoverbindungen = new LinkedList<KontoverbindungZvInternational>();
        }
        this.sepaKontoverbindungen.add(sepaKontoverbindung);
    }
}
