package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Sicherheitsprofil
 * Verfahren  zur  Absicherung  der  Transaktionen,  das  zwischen  Kunde  und
 * Kreditinstitut vereinbar wurde. Das Sicherheitsprofil wird anhand der Kombi-
 * nation  der  beiden  Elemente  „Sicherheitsverfahren“  und  „Version“  bestimmt
 * (z.B. RDH-3, DDV-1). Für das Sicherheitsverfahren PINTAN ist als Code der
 * Wert PIN und als Version der Wert 1 einzustellen.
 */
public class Sicherheitsprofil extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = 3)})
    @code(restrictions = {"DDV", "RAH", "RDH", "PIN", "EMV"})
    private String sicherheitsverfahren;

    @Element(
            description = {@ElementDescription(number = 2, length = -3)})
    @num
    private Integer sicherheitsverfahrensversion;

    public Sicherheitsprofil(byte[] degString) {
        super(degString);
    }

    public String getSicherheitsverfahren() {
        return sicherheitsverfahren;
    }

    public Integer getSicherheitsverfahrensversion() {
        return sicherheitsverfahrensversion;
    }
}
