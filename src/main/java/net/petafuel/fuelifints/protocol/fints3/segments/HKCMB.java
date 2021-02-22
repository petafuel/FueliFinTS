package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Betrag;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.support.Payments;
import net.petafuel.jsepa.model.Document;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Terminierte SEPA Überweisung Sammel Bestand
 */
public class HKCMB extends HKCSB {

	private static final Logger LOG = LogManager.getLogger(HKCMB.class);

    private HIRMS statusElement;

    public HKCMB(byte[] message) {
        super(message);
    }
	LinkedList<IMessageElement> hicmbs = null;
	@Override
	public StatusCode execute(Dialog dialog) {
		SimpleDateFormat output = new SimpleDateFormat("yyyyMMdd");
		statusElement = new HIRMS(new byte[0]);
		statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(getSegmentkopf().getSegmentNummer()).build());
		Payments.versions version = Payments.getBestVersion(getUnterstuetzteSepaPainMessages());
		DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
		LinkedList<Document> terminzahlungSammelBestand = dataAccessFacade.getTerminzahlungen(dialog.getLegitimationsInfo(), dialog.getClientProductInfo(),true, version);
		Betrag betrag = new Betrag(new byte[0]);
		betrag.setWaehrung("EUR");

		if (terminzahlungSammelBestand == null || terminzahlungSammelBestand.size() == 0) {
			try {
				statusElement.addRueckmeldung(new Rueckmeldung("3010::Es liegen keine Einträge vor".getBytes("ISO-8859-1")));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			return StatusCode.OK;
		} else {
			hicmbs= new LinkedList<>();
			LOG.info("Terminzahlungen-Sammler gefunden: {}", terminzahlungSammelBestand.size());
			for(Document d: terminzahlungSammelBestand)
			{
				try {
					HICMB hicmb = new HICMB(new byte[0]);
					hicmb.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HICMB.class).setSegmentVersion(1).setBezugssegment(getSegmentkopf().getSegmentNummer()).build());
					hicmb.setKontoverbindungInternational(getKontoverbindungInternational());
					hicmb.setEinreichungsdatum(output.format(Payments.creationFormat.parse(d.getCctInitiation().getGrpHeader().getCreationTime())));
					hicmb.setAusfuehrungsdatum(output.format(Payments.executionFormat.parse(d.getCctInitiation().getPmtInfos().get(0).getRequestedExecutionDate())));
					hicmb.setAnzahlAuftraege(d.getCctInitiation().getGrpHeader().getNoOfTransactions());
					betrag.setWert(String.format(Locale.GERMAN, "%.2f", d.getCctInitiation().getGrpHeader().getControlSum()));
					hicmb.setSummeDerBetrage(betrag);
					hicmb.setAuftragsidentifikation(d.getCctInitiation().getPmtInfos().get(0).getPmtInfId());
					hicmbs.add(hicmb);
				} catch (ParseException e) {
					LOG.info("Einrichtungsdatum: {}", d.getCctInitiation().getGrpHeader().getCreationTime());
					LOG.info("Ausführungsdatum: {}", d.getCctInitiation().getPmtInfos().get(0).getRequestedExecutionDate());
					LOG.warn(e.getMessage(),e);
					hicmbs = null;
					try {
						statusElement.addRueckmeldung(new Rueckmeldung("9210::Fehler beim Erstellen der Bestandsdaten".getBytes("ISO-8859-1")));
					} catch (UnsupportedEncodingException e1) {
						throw new RuntimeException(e1);
					}
					return StatusCode.ERROR;
				}
			}
		}
		statusElement.addRueckmeldung(Rueckmeldung.getRueckmeldung("0020"));
		return StatusCode.OK;
	}
	@Override
	public List<IMessageElement> getReplyMessageElements() {
		return hicmbs;
	}

	@Override
	public IMessageElement getStatusElement() {
		return statusElement;
	}
}
