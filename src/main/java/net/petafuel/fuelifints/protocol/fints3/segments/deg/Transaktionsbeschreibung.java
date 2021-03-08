package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;

import java.lang.reflect.Field;
import java.util.List;

/**
 * CTransaktionsbeschreibung.
 */
public class Transaktionsbeschreibung extends DatenElementGruppe {

    @Element(description = {@ElementDescription(number = 1,length = -25)})
    @an
    private String transaktionsbeschreibungsgrundtext;


    @Element(description = {@ElementDescription(number = 2,status = ElementDescription.StatusCode.O, length = -25)})
    @an
    private String transaktionsbeschreibungszusatz;


    public Transaktionsbeschreibung(byte[] bytes) {
        super(bytes);
    }

    public String getTransaktionsbeschreibungsgrundtext() {
        return transaktionsbeschreibungsgrundtext;
    }

    public void setTransaktionsbeschreibungsgrundtext(String transaktionsbeschreibungsgrundtext) {
        this.transaktionsbeschreibungsgrundtext = transaktionsbeschreibungsgrundtext;
    }

    public String getTransaktionsbeschreibungszusatz() {
        return transaktionsbeschreibungszusatz;
    }

    public void setTransaktionsbeschreibungszusatz(String transaktionsbeschreibungszusatz) {
        this.transaktionsbeschreibungszusatz = transaktionsbeschreibungszusatz;
    }
}
