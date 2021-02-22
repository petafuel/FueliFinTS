package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Name: Nachrichtenabschluss
 * Typ: Segment
 * Segmentart: Administration
 * Kennung: HNHBS
 * Sender: Kunde/Kreditinstitut
 */

public class HNHBS extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @num
    private Integer nachrichtennummer;

    public HNHBS(byte[] message) {
        super(message);
    }

    @Override
    public String toString() {
        return "HNHBS{" +
                "segmentkopf=" + segmentkopf +
                ", nachrichtennummer=" + nachrichtennummer +
                '}';
    }

    public Integer getNachrichtennummer() {
        return nachrichtennummer;
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public void setNachrichtennummer(Integer nachrichtennummer) {
        this.nachrichtennummer = nachrichtennummer;
    }
}
