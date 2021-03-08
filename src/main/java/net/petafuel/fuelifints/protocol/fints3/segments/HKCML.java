package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.ReturnDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Betrag;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.util.List;

/**
 * Terminierte SEPA Überweisung Sammel Löschen
 */
public class HKCML extends Segment implements IExecutableElement {
	@Element(description = {@ElementDescription(number = 1, status = ElementDescription.StatusCode.M)})
	private Segmentkopf segmentkopf;
	@Element(description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.M)})
	private String auftragsidentifikation;
	@Element(description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.O)})
	@ApplicantAccount
	private KontoverbindungInternational kontoverbindungInternational;
	@Element(description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O)})
	@dat
	private String einreichungsdatum;
	@Element(description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.O)})
	@dat
	private String ausfuehrungsdatum;
	@Element(description = {@ElementDescription(number = 6, status = ElementDescription.StatusCode.O)})
	@num
	private String anzahlderauftraege;
	@Element(description = {@ElementDescription(number = 7, status = ElementDescription.StatusCode.O)})
	private Betrag summederbetraege;
	private HIRMS statusElement = null;

	public HKCML(byte[] message) {
        super(message);
    }
	@Override
	public StatusCode execute(Dialog dialog) {
		statusElement = new HIRMS(new byte[0]);
		statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(getSegmentkopf().getSegmentNummer()).build());
		ReturnDataObject deleteTransaction = DataAccessFacadeManager.getAccessFacade(dialog.getBankId()).deleteTerminzahlung(dialog.getLegitimationsInfo(), dialog.getClientProductInfo(), getAuftragsidentifikation(), true);
		StatusCode statusCode;
		if(deleteTransaction.isSuccess()) {
			statusCode = StatusCode.OK;
		} else {
			statusCode = StatusCode.ERROR;
		}
		Rueckmeldung rueckmeldung = new Rueckmeldung(new byte[0]);
		rueckmeldung.setRueckmeldungscode(deleteTransaction.getReturnCode());
		rueckmeldung.setRueckmeldungstext(deleteTransaction.getMessage());
		statusElement.addRueckmeldung(rueckmeldung);
		//entferne TansactionInfo object vom Dialog
		dialog.setTransactionInfo(null);
		return statusCode;
	}
	public Segmentkopf getSegmentkopf() {
		return segmentkopf;
	}

	public void setSegmentkopf(Segmentkopf segmentkopf) {
		this.segmentkopf = segmentkopf;
	}

	public String getAuftragsidentifikation() {
		return auftragsidentifikation;
	}

	public void setAuftragsidentifikation(String auftragsidentifikation) {
		this.auftragsidentifikation = auftragsidentifikation;
	}

	public KontoverbindungInternational getKontoverbindungInternational() {
		return kontoverbindungInternational;
	}

	public void setKontoverbindungInternational(KontoverbindungInternational kontoverbindungInternational) {
		this.kontoverbindungInternational = kontoverbindungInternational;
	}

	public String getEinreichungsdatum() {
		return einreichungsdatum;
	}

	public void setEinreichungsdatum(String einreichungsdatum) {
		this.einreichungsdatum = einreichungsdatum;
	}

	public String getAusfuehrungsdatum() {
		return ausfuehrungsdatum;
	}

	public void setAusfuehrungsdatum(String ausfuehrungsdatum) {
		this.ausfuehrungsdatum = ausfuehrungsdatum;
	}

	public String getAnzahlderauftraege() {
		return anzahlderauftraege;
	}

	public void setAnzahlderauftraege(String anzahlderauftraege) {
		this.anzahlderauftraege = anzahlderauftraege;
	}

	public Betrag getSummederbetraege() {
		return summederbetraege;
	}

	public void setSummederbetraege(Betrag summederbetraege) {
		this.summederbetraege = summederbetraege;
	}



	@Override
	public List<IMessageElement> getReplyMessageElements() {
		return null;
	}

	@Override
	public IMessageElement getStatusElement() {
		return statusElement;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
