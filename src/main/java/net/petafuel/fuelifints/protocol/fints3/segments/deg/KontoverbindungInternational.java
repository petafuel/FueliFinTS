package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;

/**
 * Kontoverbindung international
 * Die Kontoverbindung international dient zur Verwendung von IBAN und BIC sowie
 * auch der nationalen Elemente Kreditinstitutskennung und Konto-/Depotnummer mit
 * optionalem Unterkontomerkmal, strukturell angelehnt an das Aggregate „Account“ in
 * ISO20022.
 * <p/>
 * Name:  Kontoverbindung international
 * Typ:  Mehrfach verwendetes Element
 * Kennung:  kti
 * Version:  1
 */
public class KontoverbindungInternational extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, status = ElementDescription.StatusCode.O, length = -34)})
    @an
    private String iban;

    @Element(
            description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.O, length = -11)})
    @an
    private String bic;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.O, length = -30)})
    @id
    private String konto_depot_nummer;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O, length = -30)})
    @id
    private String unterkontenmerkmal;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.O)})
    private Kik kreditsinstitutskennung;

    public KontoverbindungInternational(byte[] bytes) {
        super(bytes);
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getKonto_depot_nummer() {
        return konto_depot_nummer;
    }

    public void setKonto_depot_nummer(String konto_depot_nummer) {
        this.konto_depot_nummer = konto_depot_nummer;
    }

    public String getUnterkontenmerkmal() {
        return unterkontenmerkmal;
    }

    public void setUnterkontenmerkmal(String unterkontenmerkmal) {
        this.unterkontenmerkmal = unterkontenmerkmal;
    }

    public Kik getKreditsinstitutskennung() {
        return kreditsinstitutskennung;
    }

    public void setKreditsinstitutskennung(Kik kreditsinstitutskennung) {
        this.kreditsinstitutskennung = kreditsinstitutskennung;
    }
}
