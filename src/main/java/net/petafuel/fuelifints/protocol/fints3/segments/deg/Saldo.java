package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.tim;

/**
 * Saldo
 * Kontostand zum aktuellen oder zu einem vergangenen Zeitpunkt, welcher sich als
 * Unterschiedsbetrag  zwischen  der  Soll-  und  Haben-Seiten  des  Kontos  bis  dato
 * ergibt.
 */
public class Saldo extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = 1)})
    @code(restrictions = {"C", "D"})
    private String sollHabenKennzeichen;

    @Element(
            description = {@ElementDescription(number = 2)})
    private Betrag betrag;

    @Element(
            description = {@ElementDescription(number = 3, length = 8)})
    @dat
    private String datum;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O, length = 6)})
    @tim
    private String uhrzeit;

    public Saldo(byte[] bytes) {
        super(bytes);
    }

    public String getSollHabenKennzeichen() {
        return sollHabenKennzeichen;
    }

    public void setSollHabenKennzeichen(String sollHabenKennzeichen) {
        this.sollHabenKennzeichen = sollHabenKennzeichen;
    }

    public Betrag getBetrag() {
        return betrag;
    }

    public void setBetrag(Betrag betrag) {
        this.betrag = betrag;
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
