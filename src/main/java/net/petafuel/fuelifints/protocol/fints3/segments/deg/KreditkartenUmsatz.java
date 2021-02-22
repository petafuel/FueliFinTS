package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dig;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.jn;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Umsatz Kreditkartenkonto
 * Buchungsposition einer Kreditkartenabrechnung bzw. -umsatzanzeige.
 */
public class KreditkartenUmsatz extends DatenElementGruppe{

    @Element(description = {@ElementDescription(number = 1,length = -16)})
    @an
    private String getaetigtVon;

    @Element(description = {@ElementDescription(number = 2)})
    @dat
    private String belegDatum;

    @Element(description = {@ElementDescription(number = 3)})
    @dat
    private String buchungsDatum;

    @Element(description = {@ElementDescription(number = 4,status = ElementDescription.StatusCode.O)})
    @dat
    private String wertstellungsDatum;

    @Element(description = {@ElementDescription(number = 5,status = ElementDescription.StatusCode.O)})
    private BetragSollHabenKennung originalBetrag;

    @Element(description = {@ElementDescription(number = 6,status = ElementDescription.StatusCode.O)})
    @an
    private String umrechnungskurs;

    @Element(description = {@ElementDescription(number = 7)})
    private BetragSollHabenKennung buchungsBetrag;

    @Element(description = {@ElementDescription(number = 8)})
    private Transaktionsbeschreibung transaktionsbeschreibung;

    @Element(description = {@ElementDescription(number = 9,status = ElementDescription.StatusCode.O)})
    @dig
    private String laenderkennzeichen;

    @Element(description = {@ElementDescription(number = 10)})
    @jn
    private String umsatzAbgerechnet;

    @Element(description = {@ElementDescription(number = 11,status = ElementDescription.StatusCode.O)})
    @id
    private String buchungsreferenz;

    @Element(description = {@ElementDescription(number = 12,status = ElementDescription.StatusCode.O)})
    @an
    private String gebuehrenschluessel;

    @Element(description = {@ElementDescription(number = 13,status = ElementDescription.StatusCode.O)})
    @num
    private String haendlerart;

    @Element(description = {@ElementDescription(number = 14,status = ElementDescription.StatusCode.O)})
    @an
    private String abrechnungskennzeichen;

    @Element(description = {@ElementDescription(number = 15,status = ElementDescription.StatusCode.O)})
    @an
    private String gaaBarEntgeltBuchungsreferenz;

    @Element(description = {@ElementDescription(number = 16,status = ElementDescription.StatusCode.O)})
    @an
    private String aeeBuchungsreferenz;

    public KreditkartenUmsatz(byte[] bytes) {
        super(bytes);
    }

    public String getGetaetigtVon() {
        return getaetigtVon;
    }

    public void setGetaetigtVon(String getaetigtVon) {
        this.getaetigtVon = getaetigtVon;
    }

    public String getBelegDatum() {
        return belegDatum;
    }

    public void setBelegDatum(String belegDatum) {
        this.belegDatum = belegDatum;
    }

    public String getBuchungsDatum() {
        return buchungsDatum;
    }

    public void setBuchungsDatum(String buchungsDatum) {
        this.buchungsDatum = buchungsDatum;
    }

    public String getWertstellungsDatum() {
        return wertstellungsDatum;
    }

    public void setWertstellungsDatum(String wertstellungsDatum) {
        this.wertstellungsDatum = wertstellungsDatum;
    }

    public BetragSollHabenKennung getOriginalBetrag() {
        return originalBetrag;
    }

    public void setOriginalBetrag(BetragSollHabenKennung originalBetrag) {
        this.originalBetrag = originalBetrag;
    }

    public String getUmrechnungskurs() {
        return umrechnungskurs;
    }

    public void setUmrechnungskurs(String umrechnungskurs) {
        this.umrechnungskurs = umrechnungskurs;
    }

    public BetragSollHabenKennung getBuchungsBetrag() {
        return buchungsBetrag;
    }

    public void setBuchungsBetrag(BetragSollHabenKennung buchungsBetrag) {
        this.buchungsBetrag = buchungsBetrag;
    }

    public Transaktionsbeschreibung getTransaktionsbeschreibung() {
        return transaktionsbeschreibung;
    }

    public void setTransaktionsbeschreibung(Transaktionsbeschreibung transaktionsbeschreibung) {
        this.transaktionsbeschreibung = transaktionsbeschreibung;
    }

    public String getLaenderkennzeichen() {
        return laenderkennzeichen;
    }

    public void setLaenderkennzeichen(String laenderkennzeichen) {
        this.laenderkennzeichen = laenderkennzeichen;
    }

    public String getUmsatzAbgerechnet() {
        return umsatzAbgerechnet;
    }

    public void setUmsatzAbgerechnet(String umsatzAbgerechnet) {
        this.umsatzAbgerechnet = umsatzAbgerechnet;
    }

    public String getBuchungsreferenz() {
        return buchungsreferenz;
    }

    public void setBuchungsreferenz(String buchungsreferenz) {
        this.buchungsreferenz = buchungsreferenz;
    }

    public String getGebuehrenschluessel() {
        return gebuehrenschluessel;
    }

    public void setGebuehrenschluessel(String gebuehrenschluessel) {
        this.gebuehrenschluessel = gebuehrenschluessel;
    }

    public String getHaendlerart() {
        return haendlerart;
    }

    public void setHaendlerart(String haendlerart) {
        this.haendlerart = haendlerart;
    }

    public String getAbrechnungskennzeichen() {
        return abrechnungskennzeichen;
    }

    public void setAbrechnungskennzeichen(String abrechnungskennzeichen) {
        this.abrechnungskennzeichen = abrechnungskennzeichen;
    }

    public String getGaaBarEntgeltBuchungsreferenz() {
        return gaaBarEntgeltBuchungsreferenz;
    }

    public void setGaaBarEntgeltBuchungsreferenz(String gaaBarEntgeltBuchungsreferenz) {
        this.gaaBarEntgeltBuchungsreferenz = gaaBarEntgeltBuchungsreferenz;
    }

    public String getAeeBuchungsreferenz() {
        return aeeBuchungsreferenz;
    }

    public void setAeeBuchungsreferenz(String aeeBuchungsreferenz) {
        this.aeeBuchungsreferenz = aeeBuchungsreferenz;
    }

    @Override
    public byte[] getHbciEncoded() {
        return super.getHbciEncoded();
    }
}
