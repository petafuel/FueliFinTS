package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;

/**
 * Benutzerdefinierte Signatur
 * Bei  nicht-schlüsselbasierten  Sicherheitsverfahren  kann  der  Benutzer  hier
 * Angaben zur Authentisierung machen. Ob das Feld verpflichtend ist, ist vom
 * jeweiligen Sicherheitsverfahren abhängig.
 * Format: s. Spezifikation „Sicherheitsverfahren PIN/TAN“
 * Typ:  DEG
 * Format:
 * Länge:
 * Version:  1
 */
public class BenutzerdefinierteSignatur extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -99)})
    @an
    private String pin;

    @Element(
            description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.O, length = -99)})
    @an
    private String tan;

    public BenutzerdefinierteSignatur(byte[] degString) {
        super(degString);
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getTan() {
        return tan;
    }

    public void setTan(String tan) {
        this.tan = tan;
    }
}
