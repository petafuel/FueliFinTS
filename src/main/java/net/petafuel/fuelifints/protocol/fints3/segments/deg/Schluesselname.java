package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Schlüsselname
 * Verwendeter Schlüsselnamen beim RAH- und RDH-Verfahren respektive die
 * Referenz  auf  den  Chiffrierschlüssel  beim  DDV-Verfahren  in  strukturierter
 * Form. Mit dieser Information kann die Referenz auf einen Schlüssel herge-
 * stellt werden.
 * Dabei  enthält  das  DE  „Benutzerkennung“  bei  Schlüsseln  des  Kunden  die
 * Benutzerkennung, mit der der Kunde eindeutig identifiziert wird. Bei Schlüs-
 * seln des Kreditinstituts ist dagegen eine beliebige Kennung einzustellen, die
 * dazu  dient,  den  Kreditinstitutsschlüssel  eindeutig  zu  identifizieren.  Diese
 * Kennung darf weder einer anderen gültigen Benutzerkennung des Kreditin-
 * stituts noch der Benutzerkennung für den anonymen Zugang entsprechen.
 */
public class Schluesselname extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = 0)})
    private Kik kreditsinstitutkennung;

    @Element(
            description = {@ElementDescription(number = 2, length = 0)})
    @id
    private String benutzerkennung;

    @Element(
            description = {@ElementDescription(number = 3, length = 1)})
    @code(restrictions = {"D", "S", "V"})
    private String schluesselart;

    @Element(
            description = {@ElementDescription(number = 4, length = -3)})
    @num
    private Integer schluesselnummer;

    @Element(
            description = {@ElementDescription(number = 5, length = -3)})
    @num
    private Integer schluesselversion;

    public Schluesselname(byte[] degString) {
        super(degString);
    }

    public Kik getKreditsinstitutkennung() {
        return kreditsinstitutkennung;
    }

    public String getBenutzerkennung() {
        return benutzerkennung;
    }

    public Integer getSchluesselversion() {
        return schluesselversion;
    }

    public String getSchluesselart() {
        return schluesselart;
    }

    public Integer getSchluesselnummer() {
        return schluesselnummer;
    }

    public void setKreditsinstitutkennung(Kik kreditsinstitutkennung) {
        this.kreditsinstitutkennung = kreditsinstitutkennung;
    }

    public void setBenutzerkennung(String benutzerkennung) {
        this.benutzerkennung = benutzerkennung;
    }

    public void setSchluesselart(String schluesselart) {
        this.schluesselart = schluesselart;
    }

    public void setSchluesselnummer(Integer schluesselnummer) {
        this.schluesselnummer = schluesselnummer;
    }

    public void setSchluesselversion(Integer schluesselversion) {
        this.schluesselversion = schluesselversion;
    }

    @Override
    public String toString() {
        return "Schluesselname{" +
                "kreditsinstitutkennung=" + kreditsinstitutkennung +
                ", benutzerkennung='" + benutzerkennung + '\'' +
                ", schluesselart='" + schluesselart + '\'' +
                ", schluesselnummer=" + schluesselnummer +
                ", schluesselversion=" + schluesselversion +
                '}';
    }
}
