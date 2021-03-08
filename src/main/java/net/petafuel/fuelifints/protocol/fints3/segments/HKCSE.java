package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.ReturnDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.support.Payments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Terminierte SEPA Überweisung Einzel Einreichen
 */
public class HKCSE extends Segment implements IExecutableElement, IDependentElement {

	private static final Logger LOG = LogManager.getLogger(HKCSE.class);

	@Element(
			description = {@ElementDescription(number = 1)})
	private Segmentkopf segmentkopf;

	@Element(
			description = {@ElementDescription(number = 2)})
	@ApplicantAccount
	private KontoverbindungInternational kontoverbindungInternational;

	@Element(
			description = {@ElementDescription(number = 3)})
	@an
	private String sepaDescriptor;

	@Element(
			description = {@ElementDescription(number = 4)})
	@bin
	private byte[] sepaPainMessage;
	private HIRMS statusElement;


	public HKCSE(byte[] message) {
        super(message);
    }
	List<IMessageElement> hicses = null;
    @Override
    public StatusCode execute(Dialog dialog)
    {
		statusElement = new HIRMS(new byte[0]);
		statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(getSegmentkopf().getSegmentNummer()).build());

		/*
		 * Reiche die Sepa Pain Message an die DataAccessFacade zur weiteren Auswertung weiter:
		 */
		LOG.info("Terminüberweisung bekommen");
		DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
		ReturnDataObject transactionSubmitted = dataAccessFacade.submitNewTransactionSchedule(dialog.getLegitimationsInfo(), getSepaDescriptor(), SegmentUtil.getBytes(getSepaPainMessage()), false);

		LOG.info("Terminüberweisung verarbeitet: {}", transactionSubmitted.getMessage());
		StatusCode statusCode;
		if (transactionSubmitted.isSuccess()) {
			//die Lastschrift wurde angenommen
			hicses = new ArrayList<>();
			HICSE hicse = new HICSE(new byte[0]);
			hicse.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HICSE.class).setSegmentVersion(1).setBezugssegment(getSegmentkopf().getSegmentNummer()).build());
			Object id = transactionSubmitted.getAdditionalData();
			if(id != null && id instanceof Payments.AuftragsId) {
				hicse.setAuftragsidentifikation(id.toString());
			}
			hicses.add(hicse);
			statusCode = StatusCode.OK;
		} else {
			//die Lastschrift wurde abgelehnt
			statusCode = StatusCode.ERROR;
		}
		Rueckmeldung rueckmeldung = new Rueckmeldung(new byte[0]);
		rueckmeldung.setRueckmeldungscode(transactionSubmitted.getReturnCode());
		rueckmeldung.setRueckmeldungstext(transactionSubmitted.getMessage());
		statusElement.addRueckmeldung(rueckmeldung);
		//entferne TansactionInfo object vom Dialog
		dialog.setTransactionInfo(null);
		return statusCode;
    }

	public Segmentkopf getSegmentkopf() {
		return segmentkopf;
	}

	public KontoverbindungInternational getKontoverbindungInternational() {
		return kontoverbindungInternational;
	}

	public String getSepaDescriptor() {
		return sepaDescriptor;
	}

	public byte[] getSepaPainMessage() {
		return sepaPainMessage;
	}

	@Override
	public List<IMessageElement> getReplyMessageElements() {
		return hicses;
	}

	@Override
	public IMessageElement getStatusElement() {
		return statusElement;
	}
}
