package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Kik;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

import java.util.LinkedList;
import java.util.List;

/**
 * Name:  Kommunikationszugang anfordern
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKKOM
 * Bezugssegment:  -
 * Version:  4
 */
public class HKKOM extends Segment implements IExecutableElement {

    @Element(description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.O)})
    private Kik vonKreditinstitutskennung;

    @Element(description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.O)})
    private Kik bisKreditinstitutskennung;

    @Element(description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O)})
    private Integer maximaleAnzahlEintraege;

    @Element(description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C)})
    private String aufsetzpunkt;

    public HKKOM(byte[] message) {
        super(message);
    }

    private List<IMessageElement> replyElements;
    private HIRMS statusElement;

    @Override
    public StatusCode execute(Dialog dialog) {
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        HIKOM hikom = new HIKOM(segmentkopf.getSegmentVersion(), segmentkopf.getSegmentNummer(), dataAccessFacade.getKommunikationsParameterData(dialog.getBankId()));
        replyElements = new LinkedList<>();
        replyElements.add(hikom);
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
