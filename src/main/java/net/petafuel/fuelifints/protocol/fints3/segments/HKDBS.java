package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.DirectDebitDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Name:  Bestand terminierter SEPA-Einzellastschriften anfordern
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKDBS
 * Bezugssegment:  -
 * Version:  1
 * Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKDBS extends Segment implements IExecutableElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @ApplicantAccount
    private KontoverbindungInternational kontoverbindungInternational;

    @Element(
            description = {@ElementDescription(number = 3, length = -99)})
    private List<String> unterstuetzteSepaPainMessages;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C)})
    @dat
    private String vonDatum;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C)})
    private String bisDatum;

    @Element(
            description = {@ElementDescription(number = 6, status = ElementDescription.StatusCode.C, length = -4)})
    @num
    private Integer maximaleAnzahlAuftraege;

    @Element(
            description = {@ElementDescription(number = 7, status = ElementDescription.StatusCode.C, length = -35)})
    private String aufsetzpunkt;

    public HKDBS(byte[] message) {
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

    public List<String> getUnterstuetzteSepaPainMessages() {
        return unterstuetzteSepaPainMessages;
    }

    public void setUnterstuetzteSepaPainMessages(List<String> unterstuetzteSepaPainMessages) {
        this.unterstuetzteSepaPainMessages = unterstuetzteSepaPainMessages;
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

    private HIRMS statusElement;
    private List<IMessageElement> replyElements;

    @Override
    public StatusCode execute(Dialog dialog) {
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());

        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        List<DirectDebitDataObject> lastschriftBestand = dataAccessFacade.getEinzelLastschriftBestand(dialog.getLegitimationsInfo());
        if (lastschriftBestand == null || lastschriftBestand.size() == 0) {
			try {
				statusElement.addRueckmeldung(new Rueckmeldung("3010::Es liegen keine Einträge vor".getBytes("ISO-8859-1")));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			return StatusCode.INFO;
        } else {
            //TODO einbauen!
        }

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
