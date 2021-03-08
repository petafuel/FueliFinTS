package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Angaben zu Empfängerkonten
 * Typ: DEG
 * Version: 1
 */
public class AngabenEmpfaengerkonten extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1)})
    @an
    private KontoverbindungInternational kontoverbindungInternationalEmpfaenger;

    @Element(
            description = {@ElementDescription(number = 2,status = ElementDescription.StatusCode.O)})
    @an
    private String name1Empfaenger = "";

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.O)})
    @an
    private String name2Empfaenger = "";

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O)})
    @num
    private Integer kontoart;


    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.O)})
    @an
    private String kontoproduktbezeichnung = "";

    public AngabenEmpfaengerkonten(byte[] bytes) {
        super(bytes);
    }

    public KontoverbindungInternational getKontoverbindungInternationalEmpfaenger() {
        return kontoverbindungInternationalEmpfaenger;
    }

    public void setKontoverbindungInternationalEmpfaenger(KontoverbindungInternational kontoverbindungInternationalEmpfaenger) {
        this.kontoverbindungInternationalEmpfaenger = kontoverbindungInternationalEmpfaenger;
    }

    public String getName1Empfaenger() {
        return name1Empfaenger;
    }

    public void setName1Empfaenger(String name1Empfaenger) {
        this.name1Empfaenger = name1Empfaenger;
    }

    public String getName2Empfaenger() {
        return name2Empfaenger;
    }

    public void setName2Empfaenger(String name2Empfaenger) {
        this.name2Empfaenger = name2Empfaenger;
    }

    public Integer getKontoart() {
        return kontoart;
    }

    public void setKontoart(Integer kontoart) {
        this.kontoart = kontoart;
    }

    public String getKontoproduktbezeichnung() {
        return kontoproduktbezeichnung;
    }

    public void setKontoproduktbezeichnung(String kontoproduktbezeichnung) {
        this.kontoproduktbezeichnung = kontoproduktbezeichnung;
    }
}