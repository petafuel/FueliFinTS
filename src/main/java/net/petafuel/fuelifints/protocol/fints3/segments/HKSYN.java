package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.model.client.ClientProductInfo;
import net.petafuel.fuelifints.model.client.LegitimationInfo;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

import java.util.LinkedList;
import java.util.List;

/**
 * Name: Synchronisierungsnachricht
 * Typ: Nachricht
 * Sender: Kunde
 */
public class HKSYN extends Segment implements IExecutableElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;
    @Element(
            description = {@ElementDescription(number = 2, length = 1)})
    @code(restrictions = {"0", "1", "2"})
    private String synchronisierungsmodus;
    private List<IMessageElement> replyElements;

    public HKSYN(byte[] message) {
        super(message);
    }

    @Override
    public StatusCode execute(Dialog dialog) {
        replyElements = new LinkedList<IMessageElement>();
        dialog.getClientProductInfo().hksynRequested();
        ClientProductInfo clientProductInfo = dialog.getClientProductInfo();
        LegitimationInfo legitimationInfo = dialog.getLegitimationsInfo();
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(legitimationInfo.getBankId());
        String userSystemId = dataAccessFacade.getOrGenerateUserSystemId(dialog.getDialogId(), legitimationInfo, clientProductInfo);
        clientProductInfo.setUserSystemId(userSystemId);
        HISYN hisyn = new HISYN(new byte[0]);
        Segmentkopf segmentkopf;// = new Segmentkopf(("HISYN:0:4:"+this.segmentkopf.getSegmentNummer()).getBytes());
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(HISYN.class).setSegmentVersion(4).setBezugssegment(this.segmentkopf.getSegmentNummer()).build();

        hisyn.setSegmentkopf(segmentkopf);
        hisyn.setKundensystemId(userSystemId);
        replyElements.add(hisyn);
        return StatusCode.OK;
    }

    @Override
    public List<IMessageElement> getReplyMessageElements() {
        return replyElements;
    }

    @Override
    public IMessageElement getStatusElement() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
