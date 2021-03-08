package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Kommunikationsparameter
 * Die Kommunikationsparameter enthalten Informationen für den Aufbau der
 * Transportverbindung.
 */
public class KommunikationsParameter extends DatenElementGruppe {

    /**
     * Kommunikationsdienst
     * Unterstütztes Kommunikationsverfahren (Protokollstack).
     * Zur Zeit unterstützte Kommunikationsverfahren:
     * 1: T-Online (mit FinTS V3.0 nicht mehr unterstützt)
     * 2: TCP/IP (Protokollstack SLIP/PPP)
     * 3: https (verwendet im Sicherheitsverfahren PIN/TAN)
     */
    @Element(description = {@ElementDescription(number = 1, length = -2)})
    @num
    private Integer kommunikationsdienst;

    /**
     * Kommunikationsadresse
     * Beim  Zugang  über  T-Online  ist  die  Gateway-Seite  als  numerischer  Wert
     * (ohne die Steuerzeichen * und #) einzustellen.
     * Beim  Zugang  über  TCP/IP  ist  die  IP-Adresse  als  alphanumerischer  Wert
     * (z.B. ‘123.123.123.123’) einzustellen.
     * Beim Zugang über https ist die Adresse des Servlets als alphanumerischer
     * Wert (z.B. „https://www.xyz.de:7000/Servlet“) einzustellen.
     */
    @Element(description = {@ElementDescription(number = 2, length = -512)})
    @an
    private String kommunikationsadresse;

    /**
     * Kommunikationsadressenzusatz
     * Beim Zugang über T-Online ist der Regionalbereich einzustellen (‚00’ für ein
     * bundesweites Angebot). Beim Zugang über TCP/IP und https wird das Feld
     * nicht belegt.
     */
    @Element(description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.C, length = -512)})
    @an
    private String kommunikationsadressenzusatz;

    /**
     * Filterfunktion
     * Falls  das  Übertragungsverfahren  eine  Umwandlung  der  Nachricht  in  eine
     * 7 Bit-Zeichendarstellung  erfordert  (z.B.  Internet),  so  ist  hier  das  anzuwen-
     * dende Filterverfahren anzugeben. Die Nachricht ist stets komplett zu filtern,
     * auch wenn eine Filterung nicht notwendig wäre, da bspw. keine binären Da-
     * ten enthalten sind. Ein Kreditinstitut darf jeweils nur eine Filterfunktion unter-
     * stützen.
     * Codierung:
     * MIM: MIME Base 64
     * UUE: Uuencode/Uudecode
     */
    @Element(description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C, length = 3)})
    @an
    private String filterfunktion;

    /**
     * Version der Filterfunktion
     */
    @Element(description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C, length = -3)})
    private Integer versionDerFilterfunktion;

    public KommunikationsParameter(String zugangsAdresse) {
        super(new byte[0]);
        if (zugangsAdresse.toLowerCase().contains("https")) {
            kommunikationsdienst = 3;
        } else {
            kommunikationsdienst = 2;
            filterfunktion = "UUE";
        }
        kommunikationsadresse = zugangsAdresse;
    }

    public Integer getKommunikationsdienst() {
        return kommunikationsdienst;
    }

    public void setKommunikationsdienst(Integer kommunikationsdienst) {
        this.kommunikationsdienst = kommunikationsdienst;
    }

    public String getKommunikationsadresse() {
        return kommunikationsadresse;
    }

    public void setKommunikationsadresse(String kommunikationsadresse) {
        this.kommunikationsadresse = kommunikationsadresse;
    }

    public String getKommunikationsadressenzusatz() {
        return kommunikationsadressenzusatz;
    }

    public void setKommunikationsadressenzusatz(String kommunikationsadressenzusatz) {
        this.kommunikationsadressenzusatz = kommunikationsadressenzusatz;
    }

    public String getFilterfunktion() {
        return filterfunktion;
    }

    public void setFilterfunktion(String filterfunktion) {
        this.filterfunktion = filterfunktion;
    }

    public Integer getVersionDerFilterfunktion() {
        return versionDerFilterfunktion;
    }

    public void setVersionDerFilterfunktion(Integer versionDerFilterfunktion) {
        this.versionDerFilterfunktion = versionDerFilterfunktion;
    }
}
