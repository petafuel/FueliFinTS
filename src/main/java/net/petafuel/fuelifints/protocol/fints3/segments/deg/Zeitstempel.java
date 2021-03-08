package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.tim;

/**
 * Zeitstempel
 * Diese Struktur enthält eine Zeitangabe, bestehend aus Datum und optional Uhrzeit.
 */
public class Zeitstempel extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = 8)})
    @dat
    private String datum;

    @Element(
            description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.O, length = 6)})
    @tim
    private String uhrzeit;

    public Zeitstempel(byte[] bytes) {
        super(bytes);
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(String uhrzeit) {
        this.uhrzeit = uhrzeit;
    }
}
