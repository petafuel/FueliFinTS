package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.tim;

/**
 * Sicherheitsdatum und -uhrzeit
 * Zeitstempel,  beispielsweise  Datum  und  Uhrzeit  des  lokalen  Rechners,  an
 * dem die elektronische Unterschrift geleistet wurde, sowie die Bedeutung des
 * Zeitstempels.
 */
public class SicherheitsdatumUndUhrzeit extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -3)})
    @code(restrictions = {"1", "6"})
    private String datumUndZeitbezeichner;

    @Element(
            description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.O, length = 0)})
    @dat
    private String datum;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.C, length = 0)})
    @tim
    private String uhrzeit;

    public SicherheitsdatumUndUhrzeit(byte[] degString) {
        super(degString);
    }
}
