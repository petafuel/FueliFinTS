package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.RecipientAccountDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.*;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;
import net.petafuel.fuelifints.support.Payments;
import net.petafuel.jsepa.SEPAWriter;
import net.petafuel.jsepa.exception.SEPAWriteException;
import net.petafuel.jsepa.model.Document;
import net.petafuel.mt94x.Konto;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * Name: Bestand Empfängerkonten anfordern
 * Kennung: HKCUB
 * Version: 1
 * Sender: Kunde
 */
public class HKCUB extends Segment implements IExecutableElement {
    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @ApplicantAccount
    private KontoverbindungInternational kontoverbindungInternational;

    @Element(
            description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.C, length = -4)})
    @num
    private Integer maximaleAnzahlAuftraege;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C, length = -35)})
    private String aufsetzpunkt;
    private HIRMS statusElement = null;

    public HKCUB(byte[] message) {
        super(message);
    }

    LinkedList<IMessageElement> hicubList = null;

    @Override
    public StatusCode execute(Dialog dialog) {
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        List<RecipientAccountDataObject> empfaengerBestand = dataAccessFacade.getEmpfaengerkontenbestand(dialog.getLegitimationsInfo(), kontoverbindungInternational);
        if (empfaengerBestand == null || empfaengerBestand.size() == 0) {
            try {
                statusElement.addRueckmeldung(new Rueckmeldung("3010::Es liegen keine Einträge vor".getBytes("ISO-8859-1")));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return StatusCode.OK;
        } else {
            hicubList = new LinkedList<>();
            HICUB hicub = new HICUB(new byte[0]);
            hicub.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HICUB.class).setSegmentVersion(1).setBezugssegment(segmentkopf.getSegmentNummer()).build());
            hicub.setKontoverbindungInternational(kontoverbindungInternational);
            List<AngabenEmpfaengerkonten> angabenEmpfaengerkontenList = new LinkedList<>();
            for (RecipientAccountDataObject recipientAccountDataObject : empfaengerBestand) {
                KontoverbindungInternational ktie = new KontoverbindungInternational(new byte[0]);
                ktie.setIban(recipientAccountDataObject.getIBAN());
                ktie.setBic(recipientAccountDataObject.getBIC());
                AngabenEmpfaengerkonten ae = new AngabenEmpfaengerkonten(new byte[0]);
                if(recipientAccountDataObject.getName() != null && !recipientAccountDataObject.getName().isEmpty()) {
                    Kik kik = new Kik(new byte[0]);
                    kik.setKreditinstitutscode("");
                    kik.setLaenderkennzeichen("");
                    kik.setStopSkippingUnusedElements();
                    ktie.setStopSkippingUnusedElements();
                    ktie.setKreditsinstitutskennung(kik);
                    ae.setName1Empfaenger(recipientAccountDataObject.getName());
                }
                ae.setKontoverbindungInternationalEmpfaenger(ktie);
                angabenEmpfaengerkontenList.add(ae);
            }
            hicub.setAngabenEmpfaengerkonten(angabenEmpfaengerkontenList);
            hicubList.add(hicub);
        }
        statusElement.addRueckmeldung(Rueckmeldung.getRueckmeldung("0020"));
        return StatusCode.OK;
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public KontoverbindungInternational getKontoverbindungInternational() {
        return kontoverbindungInternational;
    }

    public Integer getMaximaleAnzahlAuftraege() {
        return maximaleAnzahlAuftraege;
    }

    public String getAufsetzpunkt() {
        return aufsetzpunkt;
    }

    @Override
    public List<IMessageElement> getReplyMessageElements() {
        return hicubList;
    }

    @Override
    public IMessageElement getStatusElement() {
        return statusElement;
    }
}
