package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.UnterstuetzteCamtMessages;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;
import net.petafuel.fuelifints.support.Payments;
import net.petafuel.jsepa.SEPAWriter;
import net.petafuel.jsepa.exception.SEPAWriteException;
import net.petafuel.jsepa.model.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * Terminierte SEPA Überweisung Einzel Bestand
 */
public class HKCSB extends Segment implements IExecutableElement {

	private static final Logger LOG = LogManager.getLogger(HKCSB.class);

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
	private HIRMS statusElement = null;

	public HKCSB(byte[] message) {
		super(message);
	}
	LinkedList<IMessageElement> hicsbs = null;
	@Override
	public StatusCode execute(Dialog dialog) {
		statusElement = new HIRMS(new byte[0]);
		statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());
		Payments.versions version = Payments.getBestVersion(getUnterstuetzteSepaPainMessages());
		DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
		LinkedList<Document> terminzahlungEinzelBestand = dataAccessFacade.getTerminzahlungen(dialog.getLegitimationsInfo(), dialog.getClientProductInfo(),false, version);
		if (terminzahlungEinzelBestand == null || terminzahlungEinzelBestand.size() == 0) {
			try {
				statusElement.addRueckmeldung(new Rueckmeldung("3010::Es liegen keine Einträge vor".getBytes("ISO-8859-1")));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			return StatusCode.OK;
		} else {
			hicsbs = new LinkedList<>();
			String sepaDescriptor = version.getDescriptor();
			for(Document d: terminzahlungEinzelBestand)
			{
				try {
					SEPAWriter s = new SEPAWriter(d);
					HICSB hicsb = new HICSB(new byte[0]);
					hicsb.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HICSB.class).setSegmentVersion(1).setBezugssegment(segmentkopf.getSegmentNummer()).build());
					hicsb.setSepaDescriptor(sepaDescriptor);
					hicsb.setKontoverbindungInternational(kontoverbindungInternational);
					hicsb.setSepaPainMessage(SegmentUtil.wrapBinary(s.writeSEPA()));
					hicsb.setAuftragsIdentifikation(d.getCctInitiation().getPmtInfos().get(0).getPmtInfId());
					hicsbs.add(hicsb);
				} catch (SEPAWriteException e) {
					LOG.warn("Fehler beim Erstellen der Bestandsdaten",e);
					try {
						statusElement.addRueckmeldung(new Rueckmeldung("9210::Fehler beim Erstellen der Bestandsdaten".getBytes("ISO-8859-1")));
					} catch (UnsupportedEncodingException e1) {
						throw new RuntimeException(e1);
					}
					hicsbs = null;
					return StatusCode.ERROR;
				}
			}
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

	public List<String> getUnterstuetzteSepaPainMessages() {
		return unterstuetzteSepaPainMessages;
	}

	public String getVonDatum() {
		return vonDatum;
	}

	public String getBisDatum() {
		return bisDatum;
	}

	public Integer getMaximaleAnzahlAuftraege() {
		return maximaleAnzahlAuftraege;
	}

	public String getAufsetzpunkt() {
		return aufsetzpunkt;
	}

	@Override
	public List<IMessageElement> getReplyMessageElements() {
		return hicsbs;
	}

	@Override
	public IMessageElement getStatusElement() {
		return statusElement;
	}
}
