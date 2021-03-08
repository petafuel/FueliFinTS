package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.*;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;

/**
 * Name:  Saldenrückmeldung
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HISAL
 * Bezugssegment:  HKSAL
 * Version:  6
 * Anzahl:  n
 * Sender:  Kreditinstitut
 */
public class HISAL extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, segmentVersion = 6),
                    @ElementDescription(number = 2, segmentVersion = 5)})
    private KontoverbindungNational kontoverbindungAuftraggeber;

    @Element(
            description = {@ElementDescription(number = 2, segmentVersion = 7)})
    private KontoverbindungInternational kontoverbindungInternational;

    @Element(
            description = {@ElementDescription(number = 3)})
    private String kontoproduktBezeichnung;

    @Element(
            description = {@ElementDescription(number = 4)})
    private String kontowaehrung;

    @Element(
            description = {@ElementDescription(number = 5)})
    private Saldo gebuchterSaldo;

    @Element(
            description = {@ElementDescription(number = 6, status = ElementDescription.StatusCode.O)})
    private Saldo saldoDerVorgemerktenUmsaetze;

    @Element(
            description = {@ElementDescription(number = 7, status = ElementDescription.StatusCode.O)})
    private Betrag kreditlinie;

    @Element(
            description = {@ElementDescription(number = 8, status = ElementDescription.StatusCode.O)})
    private Betrag verfuegbarerBetrag;

    @Element(
            description = {@ElementDescription(number = 9, status = ElementDescription.StatusCode.O)})
    private Betrag bereitsVerfuegterBetrag;

    @Element(
            description = {@ElementDescription(number = 10, status = ElementDescription.StatusCode.O)})
    private Betrag ueberziehung;

    @Element(
            description = {@ElementDescription(number = 11, status = ElementDescription.StatusCode.O)})
    private Zeitstempel buchungszeitpunkt;

    @Element(
            description = {@ElementDescription(number = 12, status = ElementDescription.StatusCode.C, length = 8)})
    @dat
    private String faelligkeit;

    public HISAL(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public KontoverbindungNational getKontoverbindungAuftraggeber() {
        return kontoverbindungAuftraggeber;
    }

    public void setKontoverbindungAuftraggeber(KontoverbindungNational kontoverbindungAuftraggeber) {
        this.kontoverbindungAuftraggeber = kontoverbindungAuftraggeber;
    }

    public String getKontoproduktBezeichnung() {
        return kontoproduktBezeichnung;
    }

    public void setKontoproduktBezeichnung(String kontoproduktBezeichnung) {
        this.kontoproduktBezeichnung = kontoproduktBezeichnung;
    }

    public String getKontowaehrung() {
        return kontowaehrung;
    }

    public void setKontowaehrung(String kontowaehrung) {
        this.kontowaehrung = kontowaehrung;
    }

    public Saldo getGebuchterSaldo() {
        return gebuchterSaldo;
    }

    public void setGebuchterSaldo(Saldo gebuchterSaldo) {
        this.gebuchterSaldo = gebuchterSaldo;
    }

    public Saldo getSaldoDerVorgemerktenUmsaetze() {
        return saldoDerVorgemerktenUmsaetze;
    }

    public void setSaldoDerVorgemerktenUmsaetze(Saldo saldoDerVorgemerktenUmsaetze) {
        this.saldoDerVorgemerktenUmsaetze = saldoDerVorgemerktenUmsaetze;
    }

    public KontoverbindungInternational getKontoverbindungInternational() {
        return kontoverbindungInternational;
    }

    public void setKontoverbindungInternational(KontoverbindungInternational kontoverbindungInternational) {
        this.kontoverbindungInternational = kontoverbindungInternational;
    }

    public Betrag getKreditlinie() {
        return kreditlinie;
    }

    public void setKreditlinie(Betrag kreditlinie) {
        this.kreditlinie = kreditlinie;
    }

    public Betrag getVerfuegbarerBetrag() {
        return verfuegbarerBetrag;
    }

    public void setVerfuegbarerBetrag(Betrag verfuegbarerBetrag) {
        this.verfuegbarerBetrag = verfuegbarerBetrag;
    }

    public Betrag getBereitsVerfuegterBetrag() {
        return bereitsVerfuegterBetrag;
    }

    public void setBereitsVerfuegterBetrag(Betrag bereitsVerfuegterBetrag) {
        this.bereitsVerfuegterBetrag = bereitsVerfuegterBetrag;
    }

    public Betrag getUeberziehung() {
        return ueberziehung;
    }

    public void setUeberziehung(Betrag ueberziehung) {
        this.ueberziehung = ueberziehung;
    }

    public Zeitstempel getBuchungszeitpunkt() {
        return buchungszeitpunkt;
    }

    public void setBuchungszeitpunkt(Zeitstempel buchungszeitpunkt) {
        this.buchungszeitpunkt = buchungszeitpunkt;
    }

    public String getFaelligkeit() {
        return faelligkeit;
    }

    public void setFaelligkeit(String faelligkeit) {
        this.faelligkeit = faelligkeit;
    }
}
