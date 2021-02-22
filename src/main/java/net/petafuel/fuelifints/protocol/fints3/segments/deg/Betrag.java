package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;

/**
 * Beispiel: 4567,89:EUR
 */
public class Betrag extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1)})
    @an
    private String wert;

    @Element(
            description = {@ElementDescription(number = 2)})
    @an
    private String waehrung;


    public Betrag(byte[] bytes) {
        super(bytes);
    }

    public String getWert() {
        return wert;
    }

    public void setWert(String wert) {
        this.wert = wert;
    }

    public String getWaehrung() {
        return waehrung;
    }

    public void setWaehrung(String waehrung) {
        this.waehrung = waehrung;
    }
}
