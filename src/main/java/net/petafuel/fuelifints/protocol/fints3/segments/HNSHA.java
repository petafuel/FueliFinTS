package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.BenutzerdefinierteSignatur;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;

import java.util.List;

/**
 * Name: Signaturabschluss
 * Typ: Segment
 * Segmentart: Administration
 * Kennung: HNSHA
 * Bezugssegment:
 * Segmentversion:
 * Sender: Kunde/Kreditinstitut
 */
public class HNSHA extends Segment implements IExecutableElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = -14)})
    @an
    private String sicherheitskontrollreferenz;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.C, length = -512)})
    @bin
    private byte[] validierungsresultat;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C)})
    private BenutzerdefinierteSignatur benutzerdefinierteSignatur;

    public HNSHA(byte[] message) {
        super(message);
    }

    public byte[] getValidierungsresultat() {
        return validierungsresultat;
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public String getSicherheitskontrollreferenz() {
        return sicherheitskontrollreferenz;
    }

    public void setSicherheitskontrollreferenz(String sicherheitskontrollreferenz) {
        this.sicherheitskontrollreferenz = sicherheitskontrollreferenz;
    }

    public void setValidierungsresultat(byte[] validierungsresultat) {
        this.validierungsresultat = validierungsresultat;
    }

    public BenutzerdefinierteSignatur getBenutzerdefinierteSignatur() {
        return benutzerdefinierteSignatur;
    }

    public void setBenutzerdefinierteSignatur(BenutzerdefinierteSignatur benutzerdefinierteSignatur) {
        this.benutzerdefinierteSignatur = benutzerdefinierteSignatur;
    }

    @Override
    public StatusCode execute(Dialog dialog) {
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        if (dialog.getTransactionInfo() != null && dialog.getTransactionInfo().isTanUsed()) {
            dataAccessFacade.devalueTan(dialog.getDialogId(), dialog.getLegitimationsInfo(), dialog.getClientProductInfo(), getBenutzerdefinierteSignatur().getTan());
            dialog.setTransactionInfo(null);
        }
        return StatusCode.OK;
    }

    @Override
    public List<IMessageElement> getReplyMessageElements() {
        return null;
    }

    @Override
    public IMessageElement getStatusElement() {
        return null;
    }
}
