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
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.jn;

import java.util.List;

/**
 * Name:  Einreichung terminierter SEPA-Sammellastschrift
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKDME
 * Bezugssegment:  -
 * Version:  1
 * Sender:  Kunde
 */

@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKDME extends Segment implements IExecutableElement, IDependentElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @ApplicantAccount
    private KontoverbindungInternational kontoverbindungInternational;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.C)}
    )
    private Betrag summenfeld;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C)}
    )
    @jn
    private String einzelbuchungGewuenscht;


    @Element(
            description = {@ElementDescription(number = 5)})
    @an
    private String sepaDescriptor;

    @Element(
            description = {@ElementDescription(number = 6)})
    @bin
    private byte[] sepaPainMessage;

    public HKDME(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public KontoverbindungInternational getKontoverbindungInternational() {
        return kontoverbindungInternational;
    }

    public void setKontoverbindungInternational(KontoverbindungInternational kontoverbindungInternational) {
        this.kontoverbindungInternational = kontoverbindungInternational;
    }

    public String getSepaDescriptor() {
        return sepaDescriptor;
    }

    public void setSepaDescriptor(String sepaDescriptor) {
        this.sepaDescriptor = sepaDescriptor;
    }

    public byte[] getSepaPainMessage() {
        return sepaPainMessage;
    }

    public void setSepaPainMessage(byte[] sepaPainMessage) {
        this.sepaPainMessage = sepaPainMessage;
    }

    private HIRMS statusElement;
    private List<IMessageElement> replyElements;

    @Override
    public StatusCode execute(Dialog dialog) {
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());
        /*
         * SFirm Fix:
         * SFirm schickt die Sepa Pain Message falsch!
         */
        String sepaFile = new String(sepaPainMessage).replaceAll("pain.008.001.01", "CstmrDrctDbtInitn").replaceAll("OthrId", "Othr");
        sepaPainMessage = sepaFile.getBytes();

        /*
         * Reiche die Sepa Pain Message an die DataAccessFacade zur weiteren Auswertung weiter:
         */
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        ReturnDataObject transactionSubmitted = dataAccessFacade.submitNewTransaction(dialog.getLegitimationsInfo(), sepaDescriptor, SegmentUtil.getBytes(getSepaPainMessage()), true);
        StatusCode statusCode;
        if (transactionSubmitted.isSuccess()) {
            //die Lastschrift wurde angenommen
            statusCode = StatusCode.OK;
        } else {
            //die Lastschrift wurde abgelehnt
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

    public Betrag getSummenfeld() {
        return summenfeld;
    }

    public void setSummenfeld(Betrag summenfeld) {
        this.summenfeld = summenfeld;
    }

    public String getEinzelbuchungGewuenscht() {
        return einzelbuchungGewuenscht;
    }

    public void setEinzelbuchungGewuenscht(String einzelbuchungGewuenscht) {
        this.einzelbuchungGewuenscht = einzelbuchungGewuenscht;
    }
}
