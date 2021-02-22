package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.KreditkartenUmsatz;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Saldo;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.util.List;

/**
 * Name: Kreditkartenumsätze rückmelden
 * Typ: Segment
 * Segmentart: Geschäftsvorfall
 * Kennung: DIKKU
 * Bezugssegment: DKKKU
 * Version: 1
 * Anzahl: 1
 */
public class DIKKU extends Segment {

    @Element(description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(description = {@ElementDescription(number = 2,length = -19)})
    @an
    private String kreditkartennummer;

    @Element(description = {@ElementDescription(number = 3,status = ElementDescription.StatusCode.O)})
    @id
    private String kreditkartenkontonummer;

    @Element(description = {@ElementDescription(number = 4,status = ElementDescription.StatusCode.O)})
    @id
    private String kundennummer;

    @Element(description = {@ElementDescription(number = 5)})
    private Saldo aktuellerSaldo;

    @Element(description = {@ElementDescription(number = 6,status = ElementDescription.StatusCode.O)})
    @dat
    private String datumLetzteAbrechnung;

    @Element(description = {@ElementDescription(number = 7,status = ElementDescription.StatusCode.O)})
    @dat
    private String vorraussichtlichesAbrechnungsdatum;

    @Element(description = {@ElementDescription(number = 8,status = ElementDescription.StatusCode.O)})
    private List<KreditkartenUmsatz> umsaetze;

    public DIKKU(byte[] message) {
        super(message);
    }

    public DIKKU(int segmentVersion, int bezugssegment) {
        super(new byte[0]);
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(DIKKU.class).setSegmentVersion(segmentVersion).setBezugssegment(bezugssegment).build();
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public String getKreditkartennummer() {
        return kreditkartennummer;
    }

    public void setKreditkartennummer(String kreditkartennummer) {
        this.kreditkartennummer = kreditkartennummer;
    }

    public String getKreditkartenkontonummer() {
        return kreditkartenkontonummer;
    }

    public void setKreditkartenkontonummer(String kreditkartenkontonummer) {
        this.kreditkartenkontonummer = kreditkartenkontonummer;
    }

    public String getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(String kundennummer) {
        this.kundennummer = kundennummer;
    }

    public Saldo getAktuellerSaldo() {
        return aktuellerSaldo;
    }

    public void setAktuellerSaldo(Saldo aktuellerSaldo) {
        this.aktuellerSaldo = aktuellerSaldo;
    }

    public String getDatumLetzteAbrechnung() {
        return datumLetzteAbrechnung;
    }

    public void setDatumLetzteAbrechnung(String datumLetzteAbrechnung) {
        this.datumLetzteAbrechnung = datumLetzteAbrechnung;
    }

    public String getVorraussichtlichesAbrechnungsdatum() {
        return vorraussichtlichesAbrechnungsdatum;
    }

    public void setVorraussichtlichesAbrechnungsdatum(String vorraussichtlichesAbrechnungsdatum) {
        this.vorraussichtlichesAbrechnungsdatum = vorraussichtlichesAbrechnungsdatum;
    }

    public List<KreditkartenUmsatz> getUmsaetze() {
        return umsaetze;
    }

    public void setUmsaetze(List<KreditkartenUmsatz> umsaetze) {
        this.umsaetze = umsaetze;
    }
}
