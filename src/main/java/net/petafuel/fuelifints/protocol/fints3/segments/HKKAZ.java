package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungNational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.jn;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Name:  Kontoumsätze anfordern/Zeitraum
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKKAZ
 * Bezugssegment:  -
 * Version:  6
 * Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKKAZ extends Segment implements IExecutableElement, IDependentElement {

    @Element(
            description = {@ElementDescription(number = 1)}
    )
    private Segmentkopf segmentkopf;
    @Element(
            description = {@ElementDescription(number = 2, segmentVersion = 6),
                    @ElementDescription(number = 2, segmentVersion = 4),
                    @ElementDescription(number = 2, segmentVersion = 5)})
    @ApplicantAccount
    private KontoverbindungNational kontoverbindungNational;
    @Element(
            description = {@ElementDescription(number = 2, segmentVersion = 7)})
    @ApplicantAccount
    private KontoverbindungInternational kontoverbindungAuftraggeber;
    @Element(
            description = {@ElementDescription(number = 3, length = 1)})
    @jn
    private String alleKonten;
    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O)})
    @dat
    private String vonDatum;
    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.O)})
    @dat
    private String bisDatum;
    @Element(
            description = {@ElementDescription(number = 6, status = ElementDescription.StatusCode.C, length = -4)})
    @num
    private Integer maximaleAnzahlEintraege;
    @Element(
            description = {@ElementDescription(number = 7, status = ElementDescription.StatusCode.C, length = -35)})
    @an
    private String Aufsetzpunkt;
    private HIRMS statusElement;
    private List<IMessageElement> replyMessageElements;

    public HKKAZ(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public KontoverbindungInternational getKontoverbindungAuftraggeber() {
        return kontoverbindungAuftraggeber;
    }

    public void setKontoverbindungAuftraggeber(KontoverbindungInternational kontoverbindungAuftraggeber) {
        this.kontoverbindungAuftraggeber = kontoverbindungAuftraggeber;
    }

    public String getAlleKonten() {
        return alleKonten;
    }

    public void setAlleKonten(String alleKonten) {
        this.alleKonten = alleKonten;
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

    public Integer getMaximaleAnzahlEintraege() {
        return maximaleAnzahlEintraege;
    }

    public void setMaximaleAnzahlEintraege(Integer maximaleAnzahlEintraege) {
        this.maximaleAnzahlEintraege = maximaleAnzahlEintraege;
    }

    public String getAufsetzpunkt() {
        return Aufsetzpunkt;
    }

    public void setAufsetzpunkt(String aufsetzpunkt) {
        Aufsetzpunkt = aufsetzpunkt;
    }

    public KontoverbindungNational getKontoverbindungNational() {
        return kontoverbindungNational;
    }

    public void setKontoverbindungNational(KontoverbindungNational kontoverbindungNational) {
        this.kontoverbindungNational = kontoverbindungNational;
    }

    @Override
    public StatusCode execute(Dialog dialog) {
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());

        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        boolean operationAllowed;
        String accountNr = "";
        if (segmentkopf.getSegmentVersion() < 7) {
            accountNr = kontoverbindungNational.getKontonummer();
            operationAllowed = dataAccessFacade.operationAllowedForAccount(dialog.getLegitimationsInfo(), kontoverbindungNational.getKontonummer(), this.getClass());
        } else {
            accountNr = SegmentUtil.ibanToAccountNr(kontoverbindungAuftraggeber.getIban());
            operationAllowed = dataAccessFacade.operationAllowedForAccount(dialog.getLegitimationsInfo(), SegmentUtil.ibanToAccountNr(kontoverbindungAuftraggeber.getIban()), this.getClass());
        }

        if (!operationAllowed) {
            Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("9380");
            statusElement.addRueckmeldung(rueckmeldung);
            return StatusCode.ERROR;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date vonDatum = null;
        Date bisDatum = null;
        if (this.vonDatum != null) {
            try {
                vonDatum = simpleDateFormat.parse(this.vonDatum);
                if (this.bisDatum != null && !this.bisDatum.isEmpty()) {
                    bisDatum = simpleDateFormat.parse(this.bisDatum);
                }
            } catch (ParseException e) {
                //ignored sollten bereits geprüft sein
            }
        }

        byte[] gebuchteUmsaetze = dataAccessFacade.getGebuchteUmsaetze(accountNr, vonDatum, bisDatum, dialog.getLegitimationsInfo());


		if ((gebuchteUmsaetze == null || gebuchteUmsaetze.length == 0)) {
				Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("3010");
				statusElement.addRueckmeldung(rueckmeldung);
				return StatusCode.WARNING;
			}
		byte[] nichtGebuchteUmsaetze = dataAccessFacade.getNichtGebuchteUmsaetze(accountNr, vonDatum, bisDatum, dialog.getLegitimationsInfo());
        HIKAZ hikaz = new HIKAZ(gebuchteUmsaetze, nichtGebuchteUmsaetze, getSegmentkopf().getSegmentNummer(), getSegmentkopf().getSegmentVersion());
        replyMessageElements = new LinkedList<IMessageElement>();
        replyMessageElements.add(hikaz);

        Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("0020");
        statusElement.addRueckmeldung(rueckmeldung);

        return StatusCode.OK;
    }

    @Override
    public List<IMessageElement> getReplyMessageElements() {
        return replyMessageElements;
    }

    @Override
    public IMessageElement getStatusElement() {
        return statusElement;
    }
}
