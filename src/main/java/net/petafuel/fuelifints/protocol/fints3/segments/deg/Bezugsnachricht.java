package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Bezugsnachricht
 * Eindeutige Referenz für Kundennachrichten. Die eindeutige Referenzierung
 * erfolgt anhand der Dialog-ID und der Nachrichtennummer der Kundennach-
 * richt.  Falls  auf  eine  Dialoginitialisierungsnachricht  des  Kunden  referenziert
 * werden soll, ist nicht die vom Kunden übermittelte Dialog-ID (0), sondern die
 * vom Kreditinstitut neu vergebene Dialog-ID einzustellen.
 * Es  darf  nur  auf  Nachrichten  des  dialogführenden  Benutzers  referenziert
 * werden.  Eine  explizite  Angabe  der  Benutzerkennung  als  Referenzierungs-
 * kriterium  ist  nicht  erforderlich,  da  diese  bereits  im  Signaturkopf  spezifiziert
 * wurde.
 */
public class Bezugsnachricht extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = 0)})
    @id
    private String dialog_id;

    @Element(
            description = {@ElementDescription(number = 2, length = -4)})
    @num
    private Integer nachrichtenummern;

    public Bezugsnachricht(byte[] degString) {
        super(degString);
    }

    @Override
    public String toString() {
        return "Bezugsnachricht{" +
                "dialog_id='" + dialog_id + '\'' +
                ", nachrichtenummern=" + nachrichtenummern +
                '}';
    }
}
