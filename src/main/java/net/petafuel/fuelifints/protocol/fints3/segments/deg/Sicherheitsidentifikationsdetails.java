package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;

/**
 * Sicherheitsidentifikation, Details
 * Identifikation  der  im  Sicherheitsprozess  involvierten  Parteien.  Dient  zur
 * Übermittlung  der  CID  bei  kartenbasierten  Sicherheitsverfahren  bzw.  der
 * Kundensystem-ID  bei  softwarebasierten  Verfahren  (z.B.  Speicherung  der
 * Schlüssel in einer Schlüsseldatei).
 */
public class Sicherheitsidentifikationsdetails extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -3)})
    @code(restrictions = {"1", "2"})
    private String sicherheitsparteibezeichner;

    @Element(
            description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.C, length = -256)})
    @bin
    private byte[] cid;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.C, length = 0)})
    @id
    private String parteiidentifizierung;

    public Sicherheitsidentifikationsdetails(byte[] degString) {
        super(degString);
    }
}
