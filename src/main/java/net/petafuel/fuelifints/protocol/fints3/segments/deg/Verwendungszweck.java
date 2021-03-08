package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;

import java.util.List;

/**
 * Verwendungszweck
 * Angabe zum Verwendungszweck bei einem Überweisungsauftrag.
 * Die  maximale  Anzahl  der  Verwendungszweckzeilen  ergibt  sich  aus  den
 * BPD. Es ist der DTAUS0-Zeichensatz mit der entsprechenden Codierung zu
 * verwenden.
 */
public class Verwendungszweck extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -27)})
    private List<String> verwendungszweckzeilen;

    public Verwendungszweck(byte[] bytes) {
        super(bytes);
    }

    public List<String> getVerwendungszweckzeilen() {
        return verwendungszweckzeilen;
    }

    public void setVerwendungszweckzeilen(List<String> verwendungszweckzeilen) {
        this.verwendungszweckzeilen = verwendungszweckzeilen;
    }
}
