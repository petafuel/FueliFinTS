package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.CreditCardRevenueDataObject;
import net.petafuel.fuelifints.dataaccess.dataobjects.SaldoDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Betrag;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.BetragSollHabenKennung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungNational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KreditkartenUmsatz;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Saldo;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Transaktionsbeschreibung;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.txt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Name: Kreditkartenumsätze anfordern
 * Typ: Segment
 * Kennung: DKKKU
 * Bezugssegment: -
 * Version 1
 * Sender: Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class DKKKU extends Segment implements IDependentElement, IExecutableElement {

    @Element(description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.C)})
    @ApplicantAccount
    private KontoverbindungNational kontoverbindungAuftraggeber;

    @Element(description = {@ElementDescription(number = 3, length = -19)})
    @an
    private String kreditkartennummer;

    @Element(description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C)})
    @id
    private String kreditkartenkontonummer;

    @Element(description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C)})
    @id
    private String kundennummer;

    @Element(description = {@ElementDescription(number = 6, status = ElementDescription.StatusCode.O)})
    @dat
    private String vonDatum;

    @Element(description = {@ElementDescription(number = 7, status = ElementDescription.StatusCode.O)})
    @dat
    private String bisDatum;

    @Element(description = {@ElementDescription(number = 8, status = ElementDescription.StatusCode.C, length = -4)})
    @num
    private Integer maximaleAnzahlEintraege;

    @Element(description = {@ElementDescription(number = 9, status = ElementDescription.StatusCode.C, length = -35)})
    @an
    private String aufsetzpunkt;

    private HIRMS statusElement;
    private List<IMessageElement> replyElements;

    public DKKKU(byte[] message) {
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

    public String getVonDatum() {
        return vonDatum;
    }

    public void setVonDatum(String vonDatum) {
        this.vonDatum = vonDatum;
    }

    public String getBisDatum() {
        return bisDatum;
    }

    public void setBisDatum(String bisDatum) {
        this.bisDatum = bisDatum;
    }

    public Integer getMaximaleAnzahlEintraege() {
        return maximaleAnzahlEintraege;
    }

    public void setMaximaleAnzahlEintraege(Integer maximaleAnzahlEintraege) {
        this.maximaleAnzahlEintraege = maximaleAnzahlEintraege;
    }

    public String getAufsetzpunkt() {
        return aufsetzpunkt;
    }

    public void setAufsetzpunkt(String aufsetzpunkt) {
        this.aufsetzpunkt = aufsetzpunkt;
    }

    @Override
    public StatusCode execute(Dialog dialog) {
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());

        boolean operationAllowed = dataAccessFacade.operationAllowedForAccount(dialog.getLegitimationsInfo(), kreditkartennummer, getClass());

        if (!operationAllowed) {
            Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("9380");
            statusElement.addRueckmeldung(rueckmeldung);
            return StatusCode.ERROR;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date von = null;
        Date bis = null;
        try {
            if (vonDatum != null && !vonDatum.isEmpty()) {
                von = simpleDateFormat.parse(vonDatum);
                if (bisDatum != null && !bisDatum.isEmpty()) {
                    bis = simpleDateFormat.parse(bisDatum);
                }
            }
        } catch (ParseException e) {
            //ignored sollten bereits geprüft sein
        }

        List<CreditCardRevenueDataObject> revenues = dataAccessFacade.getCreditCardRevenueData(kreditkartennummer, von, bis, dialog.getLegitimationsInfo());
        SaldoDataObject saldoDataObject = dataAccessFacade.getSaldo(kreditkartennummer,dialog.getLegitimationsInfo());
        if(revenues == null || revenues.isEmpty()) {
            Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("3010");
            statusElement.addRueckmeldung(rueckmeldung);
            return StatusCode.WARNING;
        }
        DIKKU dikku = new DIKKU(segmentkopf.getSegmentVersion(), segmentkopf.getSegmentNummer());

        Betrag betrag = new Betrag(new byte[0]);
        betrag.setWert(String.format(Locale.GERMAN, "%.2f", Math.abs(saldoDataObject.getGebuchterSaldo())));
        betrag.setWaehrung(saldoDataObject.getWaehrung());

        Saldo saldo = new Saldo(new byte[0]);
        saldo.setBetrag(betrag);
        String sollHabenKennzeichen = "C";
        if(saldoDataObject.getGebuchterSaldo() < 0) {
            sollHabenKennzeichen = "D";
        }
        saldo.setSollHabenKennzeichen(sollHabenKennzeichen);
        saldo.setDatum(simpleDateFormat.format(new Date()));

        dikku.setAktuellerSaldo(saldo);
        if(kontoverbindungAuftraggeber != null) {
            dikku.setKreditkartenkontonummer(kontoverbindungAuftraggeber.getKontonummer());
        }
        dikku.setKundennummer(dialog.getLegitimationsInfo().getCustomerId());
        dikku.setKreditkartennummer(kreditkartennummer);
        List<KreditkartenUmsatz> umsaetze = new LinkedList<>();
        for(CreditCardRevenueDataObject creditCardRevenueDataObject : revenues) {
            KreditkartenUmsatz kreditkartenUmsatz = new KreditkartenUmsatz(new byte[0]);
            kreditkartenUmsatz.setGetaetigtVon(creditCardRevenueDataObject.getCreditCardNumber());
            kreditkartenUmsatz.setBelegDatum(simpleDateFormat.format(creditCardRevenueDataObject.getVoucherDate()));
            kreditkartenUmsatz.setBuchungsDatum(simpleDateFormat.format(creditCardRevenueDataObject.getBookingDate()));
            BetragSollHabenKennung buchungsbetrag = new BetragSollHabenKennung(new byte[0]);
            buchungsbetrag.setWert(String.format(Locale.GERMAN, "%.2f", Math.abs(creditCardRevenueDataObject.getAmount())));
            buchungsbetrag.setWaehrung(saldoDataObject.getWaehrung());
            buchungsbetrag.setSollHabenKennung("C");
            if(creditCardRevenueDataObject.getAmount() < 0) {
                buchungsbetrag.setSollHabenKennung("D");
            }
            kreditkartenUmsatz.setBuchungsBetrag(buchungsbetrag);
            Transaktionsbeschreibung transaktionsbeschreibung = new Transaktionsbeschreibung(new byte[0]);
            transaktionsbeschreibung.setTransaktionsbeschreibungsgrundtext(creditCardRevenueDataObject.getPurpose());
            kreditkartenUmsatz.setTransaktionsbeschreibung(transaktionsbeschreibung);
            kreditkartenUmsatz.setUmsatzAbgerechnet(creditCardRevenueDataObject.isBilled() ? "J" : "N");
            umsaetze.add(kreditkartenUmsatz);
        }

        dikku.setUmsaetze(umsaetze);
        replyElements = new LinkedList<>();
        replyElements.add(dikku);
        statusElement.addRueckmeldung(Rueckmeldung.getRueckmeldung("0020"));
        return StatusCode.OK;
    }

    @Override
    public List<IMessageElement> getReplyMessageElements() {
        return replyElements;
    }

    @Override
    public IMessageElement getStatusElement() {
        return statusElement;
    }
}
