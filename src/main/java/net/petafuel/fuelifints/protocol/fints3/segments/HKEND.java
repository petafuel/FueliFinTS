package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;

import java.util.List;

/**
 * Name:  Dialogende
 * Typ:  Segment
 * Segmentart:  Administration
 * Sender:  Kunde
 */
public class HKEND extends Segment implements IExecutableElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = -30)})
    @id
    private String dialogId;

    public HKEND(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    @Override
    public StatusCode execute(Dialog dialog) {
        dialog.setLastMessage(true);  //HKEND ist immer die letzte Servernachricht in einem Dialog
        return StatusCode.OK;
    }

    @Override
    public List<IMessageElement> getReplyMessageElements() {
        return null;
    }

    @Override
    public IMessageElement getStatusElement() {
        HIRMS statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());
        statusElement.addRueckmeldung(Rueckmeldung.getRueckmeldung("0100"));
        return statusElement;
    }
}
