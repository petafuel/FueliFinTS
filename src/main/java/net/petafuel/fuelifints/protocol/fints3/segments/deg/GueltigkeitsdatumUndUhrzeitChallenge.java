package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.tim;

/**
 * Gültigkeitsdatum und –uhrzeit für Challenge
 * Datum  und  Uhrzeit,  bis zu  welchem  Zeitpunkt  eine  TAN  auf  Basis  der ge-
 * sendeten Challenge gültig ist. Nach Ablauf der Gültigkeitsdauer wird die ent-
 * sprechende TAN entwertet.
 */
public class GueltigkeitsdatumUndUhrzeitChallenge extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1)})
    @dat
    private String datum;

    @Element(
            description = {@ElementDescription(number = 2)})
    @tim
    private String uhrzeit;

    public GueltigkeitsdatumUndUhrzeitChallenge(byte[] bytes) {
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
