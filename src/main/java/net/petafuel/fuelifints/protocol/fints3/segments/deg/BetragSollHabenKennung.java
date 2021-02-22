package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

import java.lang.reflect.Field;
import java.util.List;

public class BetragSollHabenKennung extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1)})
    @an
    private String wert = "";

    @Element(
            description = {@ElementDescription(number = 2)})
    @an
    private String waehrung = "";

    @Element(
            description = {@ElementDescription(number = 3,length = 1)})
    @code(restrictions = {"C","D",""})
    private String sollHabenKennung = "";

    public BetragSollHabenKennung(byte[] bytes) {
        super(bytes);
    }

    public String getWert() {
        return wert;
    }

    public void setWert(String wert) {
        this.wert = wert;
    }

    public String getWaehrung() {
        return waehrung;
    }

    public void setWaehrung(String waehrung) {
        this.waehrung = waehrung;
    }

    public String getSollHabenKennung() {
        return sollHabenKennung;
    }

    public void setSollHabenKennung(String sollHabenKennung) {
        this.sollHabenKennung = sollHabenKennung;
    }
}