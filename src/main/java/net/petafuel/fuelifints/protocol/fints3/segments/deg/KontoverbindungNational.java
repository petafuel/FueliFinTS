package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;

/**
 * Deutsche Kontoverbindung, die im Rahmen der Abwicklung eines Auftrags benötigt wird.
 * Typ: DEG
 * Formatkennung: ktv
 * Länge: #
 * Version: 2
 * <p/>
 * Beispiel: 1234567:EUR:280:10020030
 */
public class KontoverbindungNational extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1)})
    @an
    private String kontonummer;

    @Element(
            description = {@ElementDescription(number = 2)})
    @an
    private String unterkontomerkmal;

    /*
    @Element(number = 3)
    @an
    private String sprache;

    @Element(number = 4)
    @an
    private String bankleitzahl;
    */
    @Element(
            description = {@ElementDescription(number = 3)})
    private Kik kreditinstitutskennung;


    public KontoverbindungNational(byte[] bytes) {
        super(bytes);
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public String getUnterkontomerkmal() {
        return unterkontomerkmal;
    }

    public void setUnterkontomerkmal(String unterkontomerkmal) {
        this.unterkontomerkmal = unterkontomerkmal;
    }

    public Kik getKreditinstitutskennung() {
        return kreditinstitutskennung;
    }

    public void setKreditinstitutskennung(Kik kreditinstitutskennung) {
        this.kreditinstitutskennung = kreditinstitutskennung;
    }
}
