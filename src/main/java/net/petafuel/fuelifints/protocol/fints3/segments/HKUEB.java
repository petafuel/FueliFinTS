package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.ReturnDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Betrag;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungNational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Verwendungszweck;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dig;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dta;

import java.util.List;

/**
 * Name:  Einzelüberweisung
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKUEB
 * Bezugssegment:  -
 * Version:  5
 * Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKUEB extends Segment implements IExecutableElement, IDependentElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @ApplicantAccount
    private KontoverbindungNational kontoverbindungAuftraggeber;

    @Element(
            description = {@ElementDescription(number = 3)})
    private KontoverbindungNational kontoverbindungEmpfaenger;

    @Element(
            description = {@ElementDescription(number = 4, length = -27)})
    @dta
    private String nameEmpfaenger1;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.O, length = -27)})
    @dta
    private String nameEmpfaenger2;

    @Element(
            description = {@ElementDescription(number = 6)})
    private Betrag betrag;

    @Element(
            description = {@ElementDescription(number = 7, length = 2)})
    @dig
    private String textschluessel;

    @Element(
            description = {@ElementDescription(number = 8, status = ElementDescription.StatusCode.O, length = 3)})
    @dig
    private String textschluesselErgaenzung;

    @Element(
            description = {@ElementDescription(number = 9, status = ElementDescription.StatusCode.O)})
    private Verwendungszweck verwendungszweck;

    public HKUEB(byte[] message) {
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

    public KontoverbindungNational getKontoverbindungEmpfaenger() {
        return kontoverbindungEmpfaenger;
    }

    public void setKontoverbindungEmpfaenger(KontoverbindungNational kontoverbindungEmpfaenger) {
        this.kontoverbindungEmpfaenger = kontoverbindungEmpfaenger;
    }

    public String getNameEmpfaenger1() {
        return nameEmpfaenger1;
    }

    public void setNameEmpfaenger1(String nameEmpfaenger1) {
        this.nameEmpfaenger1 = nameEmpfaenger1;
    }

    public String getNameEmpfaenger2() {
        return nameEmpfaenger2;
    }

    public void setNameEmpfaenger2(String nameEmpfaenger2) {
        this.nameEmpfaenger2 = nameEmpfaenger2;
    }

    public Betrag getBetrag() {
        return betrag;
    }

    public void setBetrag(Betrag betrag) {
        this.betrag = betrag;
    }

    public String getTextschluessel() {
        return textschluessel;
    }

    public void setTextschluessel(String textschluessel) {
        this.textschluessel = textschluessel;
    }

    public String getTextschluesselErgaenzung() {
        return textschluesselErgaenzung;
    }

    public void setTextschluesselErgaenzung(String textschluesselErgaenzung) {
        this.textschluesselErgaenzung = textschluesselErgaenzung;
    }

    public Verwendungszweck getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(Verwendungszweck verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    private HIRMS statusElement;
    private List<IMessageElement> replyElements;

    @Override
    public StatusCode execute(Dialog dialog) {
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());

        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());

        ReturnDataObject transactionSubmitted = dataAccessFacade.submitNewTransaction(dialog.getLegitimationsInfo(), getKontoverbindungAuftraggeber(), getKontoverbindungEmpfaenger(), getNameEmpfaenger1(), getNameEmpfaenger2(), getTextschluessel(), getTextschluesselErgaenzung(), getVerwendungszweck().getVerwendungszweckzeilen(),getBetrag(), "", "");
        StatusCode statusCode;
        if (transactionSubmitted.isSuccess()) {
            //die Überweisung wurde angenommen
            statusCode = StatusCode.OK;
        } else {
            //die Überweisung wurde abgelehnt
            statusCode = StatusCode.ERROR;
        }
        Rueckmeldung rueckmeldung = new Rueckmeldung(new byte[0]);
        rueckmeldung.setRueckmeldungscode(transactionSubmitted.getReturnCode());
        rueckmeldung.setRueckmeldungstext(transactionSubmitted.getMessage());
        statusElement.addRueckmeldung(rueckmeldung);
        //entferne TansactionInfo object vom Dialog
        dialog.setTransactionInfo(null);
        return statusCode;
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
