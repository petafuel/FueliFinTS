package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.util.List;

/**
 * Name:  Parameter PIN/TAN-spezifische Informationen
 * Typ:  Datenelementgruppe
 * Status:  M
 */
public class PinTanSpezifischeInformationen extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, status = ElementDescription.StatusCode.O, length = -2)})
    @num
    public Integer minPinLaenge;

    @Element(
            description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.O, length = -2)})
    @num
    public Integer maxPinLaenge;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.O, length = -2)})
    @num
    public Integer maxTanLaenge;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O, length = -30)})
    @an
    public String textBelegungBenutzerkennung;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.O, length = -30)})
    @an
    public String textBelegungKundenId;

    @Element(
            description = {@ElementDescription(number = 6, status = ElementDescription.StatusCode.O)})
    public List<GeschaeftsvorfallspezifischePinTanInformation> geschaeftsvorfallspezifischePinTanInformationen;

    public PinTanSpezifischeInformationen() {
        super(new byte[0]);
    }
}