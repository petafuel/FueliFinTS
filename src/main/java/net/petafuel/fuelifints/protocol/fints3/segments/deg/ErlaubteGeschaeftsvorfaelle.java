package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Erlaubte Geschäftsvorfälle
 * Information  darüber,  ob  der  Kunde  zur  Ausführung  des  jeweiligen  Ge-
 * schäftsvorfalls zugelassen ist und wie viele Signaturen hierzu mindestens er-
 * forderlich sind. Ferner können für jeden Geschäftsvorfall Einzelauftragslimite
 * angegeben  werden,  sofern  dies  bankfachlich  möglich  ist.  Die  Reihenfolge
 * der Geschäftsvorfälle ist unerheblich.
 */
public class ErlaubteGeschaeftsvorfaelle extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -6)})
    @an
    private String geschaeftsvorfall;

    @Element(
            description = {@ElementDescription(number = 2, length = -2)})
    @num
    private Integer anzahlBenoetigterSignaturen;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.O, length = 1)})
    @code(restrictions = {"E", "T", "W", "M", "Z"})
    private String limitart;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C)})
    private Betrag betrag;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C, length = -3)})
    @num
    private Integer limitTage;

    public ErlaubteGeschaeftsvorfaelle(byte[] bytes) {
        super(bytes);
    }

    public String getGeschaeftsvorfall() {
        return geschaeftsvorfall;
    }

    public void setGeschaeftsvorfall(String geschaeftsvorfall) {
        this.geschaeftsvorfall = geschaeftsvorfall;
    }

    public Integer getAnzahlBenoetigterSignaturen() {
        return anzahlBenoetigterSignaturen;
    }

    public void setAnzahlBenoetigterSignaturen(Integer anzahlBenoetigterSignaturen) {
        this.anzahlBenoetigterSignaturen = anzahlBenoetigterSignaturen;
    }

    public String getLimitart() {
        return limitart;
    }

    public void setLimitart(String limitart) {
        this.limitart = limitart;
    }

    public Betrag getBetrag() {
        return betrag;
    }

    public void setBetrag(Betrag betrag) {
        this.betrag = betrag;
    }

    public Integer getLimitTage() {
        return limitTage;
    }

    public void setLimitTage(Integer limitTage) {
        this.limitTage = limitTage;
    }
}
