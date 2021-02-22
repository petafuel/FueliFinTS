package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.SaldoDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.*;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.jn;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Name:  Saldenabfrage
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKSAL
 * Bezugssegment:  -
 * Version:  6
 * Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKSAL extends Segment implements IDependentElement, IExecutableElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;
    @Element(
            description = {@ElementDescription(number = 2, segmentVersion = 6),
                    @ElementDescription(number = 2, segmentVersion = 3),
                    @ElementDescription(number = 2, segmentVersion = 4),
                    @ElementDescription(number = 2, segmentVersion = 5)})
    @ApplicantAccount
    private KontoverbindungNational kontoverbindungAuftraggeber;
    @Element(
            description = {@ElementDescription(number = 2, segmentVersion = 7)})
    @ApplicantAccount
    private KontoverbindungInternational kontoverbindungInternational;
    @Element(
            description = {@ElementDescription(number = 3, length = 1)})
    @jn
    private String alleKonten;
    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O, length = -4)})
    @num
    private Integer maximaleAnzahlAuftraege;
    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C, length = -35)})
    @an
    private String aufsetzpunkt;

    private HIRMS statusElement;
    private List<IMessageElement> replyElements;

    public HKSAL(byte[] message) {
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

    public KontoverbindungInternational getKontoverbindungInternational() {
        return kontoverbindungInternational;
    }

    public void setKontoverbindungInternational(KontoverbindungInternational kontoverbindungInternational) {
        this.kontoverbindungInternational = kontoverbindungInternational;
    }

    public String getAlleKonten() {
        return alleKonten;
    }

    public void setAlleKonten(String alleKonten) {
        this.alleKonten = alleKonten;
    }

    public Integer getMaximaleAnzahlAuftraege() {
        return maximaleAnzahlAuftraege;
    }

    public void setMaximaleAnzahlAuftraege(Integer maximaleAnzahlAuftraege) {
        this.maximaleAnzahlAuftraege = maximaleAnzahlAuftraege;
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
        String accountId = "";
        if (segmentkopf.getSegmentVersion() < 7) {
            accountId = kontoverbindungAuftraggeber.getKontonummer();
        } else {
            accountId = SegmentUtil.ibanToAccountNr(kontoverbindungInternational.getIban());
        }

        SaldoDataObject saldo = dataAccessFacade.getSaldo(accountId, dialog.getLegitimationsInfo());
        double saldoWert = saldo.getGebuchterSaldo();

        HISAL hisal = new HISAL(new byte[0]);
        Segmentkopf sk = Segmentkopf.Builder.newInstance().setSegmentKennung(HISAL.class).setSegmentVersion(segmentkopf.getSegmentVersion()).setBezugssegment(segmentkopf.getSegmentNummer()).build();
        hisal.setSegmentkopf(sk);
        hisal.setKontoverbindungAuftraggeber(kontoverbindungAuftraggeber);
        hisal.setKontoverbindungInternational(kontoverbindungInternational);
        hisal.setKontoproduktBezeichnung(dataAccessFacade.getKontoproduktbezeichnung(accountId, dialog.getLegitimationsInfo()));    //TODO: Fehlermeldung, wenn hier was schiefläuft!
        hisal.setKontowaehrung(dataAccessFacade.getKontowaehrung(accountId));
        Saldo gebuchterSaldo = new Saldo(new byte[0]);
        if (saldoWert < 0) {
            gebuchterSaldo.setSollHabenKennzeichen("D");
        } else {
            gebuchterSaldo.setSollHabenKennzeichen("C");
        }
        Betrag betrag = new Betrag(new byte[0]);
        Betrag betragPrebooked = new Betrag(new byte[0]);
        Betrag betragVerfuegbarerBetrag = new Betrag(new byte[0]);
        Betrag betragKreditlinie = new Betrag(new byte[0]);
        betrag.setWaehrung(saldo.getWaehrung());
        betragPrebooked.setWaehrung(saldo.getWaehrung());
        betragVerfuegbarerBetrag.setWaehrung(saldo.getWaehrung());
        betragKreditlinie.setWaehrung(saldo.getWaehrung());
        betrag.setWert(String.format(Locale.GERMAN, "%.2f", Math.abs(saldoWert)));
        gebuchterSaldo.setBetrag(betrag);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        gebuchterSaldo.setDatum(simpleDateFormat.format(new Date()));
        hisal.setGebuchterSaldo(gebuchterSaldo);
        if (saldo.getVerfuegbarerBetrag() != null) {
            betragVerfuegbarerBetrag.setWert(String.format(Locale.GERMAN, "%.2f", Math.abs(saldo.getVerfuegbarerBetrag())));
            hisal.setVerfuegbarerBetrag(betragVerfuegbarerBetrag);
        }
        if (saldo.getKreditlinie() != null) {
            betragKreditlinie.setWert(String.format(Locale.GERMAN, "%.2f", Math.abs(saldo.getKreditlinie())));
            hisal.setKreditlinie(betragKreditlinie);
        }
        if (saldo.getVorgemerkterSaldo() != null) {
            Saldo vorgemerkterSaldo = new Saldo(new byte[0]);
            if (saldo.getVorgemerkterSaldo() < 0) {
                vorgemerkterSaldo.setSollHabenKennzeichen("D");
            } else {
                vorgemerkterSaldo.setSollHabenKennzeichen("C");
            }
            betragPrebooked.setWert(String.format(Locale.GERMAN, "%.2f", Math.abs(saldo.getVorgemerkterSaldo())));
            vorgemerkterSaldo.setBetrag(betragPrebooked);
			vorgemerkterSaldo.setDatum(simpleDateFormat.format(new Date()));
            hisal.setSaldoDerVorgemerktenUmsaetze(vorgemerkterSaldo);
        }
        Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("0020");
        statusElement.addRueckmeldung(rueckmeldung);

        replyElements = new LinkedList<IMessageElement>();
        replyElements.add(hisal);
        hisal.getSegmentkopf().setSegmentNummer(0);
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
