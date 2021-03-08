package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.model.client.LegitimationInfo;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Kik;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.util.List;

/**
 * Name:  Identifikation
 * Typ:  Segment
 * Segmentart:  Administration
 * Bezugssegment:        -
 * Sender:  Kunde
 */
public class HKIDN extends Segment implements IExecutableElement, IDependentElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    private Kik kreditinstitutskennung;

    @Element(
            description = {@ElementDescription(number = 3)})
    @id
    private String kunden_id;

    @Element(
            description = {@ElementDescription(number = 4)})
    @id
    private String kundensystem_id;

    @Element(
            description = {@ElementDescription(number = 5,
                    length = 1)})
    @num
    private Integer kundensystem_status;

    public HKIDN(byte[] message) {
        super(message);
    }


    private HIRMS statusElement;

    //Example
    // HKIDN:5:2+280:10020030+12345+2+1'

    @Override
    public String toString() {
        return "HKIDN{" +
                "segmentkopf=" + segmentkopf +
                ", kreditinstitutskennung=" + kreditinstitutskennung +
                ", kunden_id='" + kunden_id + '\'' +
                ", kundensystem_id='" + kundensystem_id + '\'' +
                ", kundensystem_status='" + kundensystem_status + '\'' +
                '}';
    }

    @Override
    public StatusCode execute(Dialog dialog) {
        dialog.setBankId(kreditinstitutskennung.getKreditinstitutscode());
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        if(dataAccessFacade == null) {
            statusElement = new HIRMS(new byte[0]);
            statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());
            Rueckmeldung rueckmeldung = new Rueckmeldung(null);
            rueckmeldung.setRueckmeldungscode("9999");
            rueckmeldung.setRueckmeldungstext("BLZ nicht bekannt.");
            statusElement.addRueckmeldung(rueckmeldung);

            return StatusCode.ERROR;
        }
        LegitimationInfo legitimationInfo = dialog.getLegitimationsInfo();
        legitimationInfo.setCustomerId(kunden_id);
        if ("9999999999".equals(kunden_id)) {
            legitimationInfo.setUserId(kunden_id);
        }
        legitimationInfo.setUserIdentified(dataAccessFacade.isCustomerIdValid(dialog.getLegitimationsInfo().getUserId(), kunden_id));

        if(!legitimationInfo.isUserIdentified()) {
            statusElement = new HIRMS(new byte[0]);
            statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());
            Rueckmeldung rueckmeldung = new Rueckmeldung(null);
            rueckmeldung.setRueckmeldungscode("9010");
            rueckmeldung.setRueckmeldungstext("Benutzerkennung oder Kunden-ID unbekannt.");
            statusElement.addRueckmeldung(rueckmeldung);
            return StatusCode.ERROR;
        }

        legitimationInfo.setBankId(kreditinstitutskennung.getKreditinstitutscode());
        dialog.getClientProductInfo().setUserSystemId(kundensystem_id);
        return StatusCode.OK;
    }

    @Override
    public List<IMessageElement> getReplyMessageElements() {
        return null;
    }

    @Override
    public IMessageElement getStatusElement() {
        return statusElement;
    }
}

