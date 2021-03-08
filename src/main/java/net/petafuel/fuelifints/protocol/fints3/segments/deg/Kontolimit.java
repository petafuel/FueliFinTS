package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Kontobezogenes Limit für Verfügungen am Konto.
 * ￼Die Angabe eines Kontolimits ist kreditinstitutsseitig optional,
 * so dass für den Kunden ein Limit bestehen kann, auch wenn dieses nicht in die
 * UPD eingestellt wurde. Ein kontobezogenes Limit darf nicht gleichzeitig mit
 * geschäftsvorfallbezogenen Limiten angegeben werden.
 */
public class Kontolimit extends DatenElementGruppe {

    /**
     * Codierung:
     * E: Einzelauftragslimit
     * T: Tageslimit
     * W: Wochenlimit
     * M: Monatslimit
     * Z: Zeitlimit
     */
    @Element(
            description = {@ElementDescription(number = 1, length = 1)})
    @code(restrictions = {"E", "T", "W", "M", "Z"})
    private String limitart;

    @Element(
            description = {@ElementDescription(number = 2)})
    private Betrag limitbetrag;

    /**
     * C => Optional nur bei limitart == Z, sonst nicht
     */
    @Element(
            description = {@ElementDescription(number = 3, length = -3)})
    @num
    private Betrag limit_tage;

    public Kontolimit(byte[] bytes) {
        super(bytes);
    }
}
