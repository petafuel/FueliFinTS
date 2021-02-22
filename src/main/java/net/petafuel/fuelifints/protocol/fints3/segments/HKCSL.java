package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.ReturnDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;

import java.util.List;

/**
 * Terminierte SEPA Überweisung Einzel Löschen
 */
public class HKCSL extends Segment implements IExecutableElement {
	@Element(description = {@ElementDescription(number = 1, status = ElementDescription.StatusCode.M)})
	private Segmentkopf segmentkopf;
	@Element(description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.M)})
	@ApplicantAccount
	private KontoverbindungInternational kontoverbindungInternational;
	@Element(description = {@ElementDescription(number = 3, status = ElementDescription.StatusCode.C)})
	@an
	private String sepaDescriptor;
	@Element(description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C)})
	@bin
	private byte[] sepaMessage;
	@Element(description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.M)})
	private String auftragsidentifikation;

    public HKCSL(byte[] message) {
        super(message);
    }


	private HIRMS statusElement = null;
	@Override
	public StatusCode execute(Dialog dialog) {
		statusElement = new HIRMS(new byte[0]);
		statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(getSegmentkopf().getSegmentNummer()).build());
		ReturnDataObject deleteTransaction = DataAccessFacadeManager.getAccessFacade(dialog.getBankId()).deleteTerminzahlung(dialog.getLegitimationsInfo(), dialog.getClientProductInfo(), getAuftragsidentifikation(), false);
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

	@Override
	public List<IMessageElement> getReplyMessageElements() {
		return null;
	}

	@Override
	public IMessageElement getStatusElement() {
		return statusElement;
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

	public byte[] getSepaMessage() {
		return sepaMessage;
	}

	public void setSepaMessage(byte[] sepaMessage) {
		this.sepaMessage = sepaMessage;
	}

	public String getAuftragsidentifikation() {
		return auftragsidentifikation;
	}

	public void setAuftragsidentifikation(String auftragsidentifikation) {
		this.auftragsidentifikation = auftragsidentifikation;
	}
}
