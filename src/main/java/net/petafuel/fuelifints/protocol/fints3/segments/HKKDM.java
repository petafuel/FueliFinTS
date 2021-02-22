package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungNational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;

import java.util.List;

/**
 * Freitextmeldungen
 *
 * Dem Kunden wird die Möglichkeit gegeben, eine unstrukturierte, unformatierte Mel-
 * dung  (Freitext)  an  das  Kreditinstitut  zu  senden.  Gegenstand  der  Freitextmeldung
 * können alle Aufträge sein, zu denen (noch) keine expliziten Geschäftsvorfälle exis-
 * tieren. Ferner können per Kundenmeldung beliebige Informationen ohne Auftrags-
 * charakter an das Kreditinstitut übermittelt werden.
 * Da  der  Kunde  auch  Aufträge  in  der  Meldung  übermitteln  kann,  ist  die  allgemeine
 * Kundenmeldung stets zu signieren.
 * Die Behandlung der Meldung sowie die Reaktion auf die Meldung sind kreditinsti-
 * tutsspezifisch  und  den  entsprechenden  Bedingungen  des  Kunde-Bank-Verhältnis-
 * ses zu entnehmen. Insbesondere sollten mit Hilfe der Freitextmeldung keine zeitkri-
 * tischen Aufträge gesendet werden.
 *
 * Die  Kundenmeldung  soll  nicht  die  Funktionalität  eines  Email-
 * Dienstes  (Adressierung,  Anhängen  von  Anlagen,  Formatierungs-
 * möglichkeiten  etc.)  bieten,  sondern  diesen  ergänzen.  Es  ist  dem
 * Kundenprodukt freigestellt, über die Kundenmeldung hinaus zu
 * sätz-
 * lich die Mailkomponente des jeweiligen Online-Dienstes anzubieten.
 *
 * Kundenmeldungen  sollten  im  Kundenprodukt  gespeichert  werden,
 * um dem Kunden auch zu einem späteren Zeitpunkt einen Zugriff auf
 * seine Meldungen zu ermöglichen.
 *
 * Der Kunde erhält lediglich eine Bestätigung des Eingangs seiner Meldung, jedoch
 * keine Rückmeldung bzgl. der Bearbeitung oder Ausführung (sofern es sich um ei-
 * nen Auftrag handelt). Ebenso sind keine weiteren Informationen über den Verarbei-
 * tungsvorgang im Statusprotokoll abrufbar.
 *
 * Format
 * Name:  Kundenmeldung
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKKDM
 * Bezugssegment:  -
 * Version:  5
 * Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED})
public class HKKDM extends Segment implements IExecutableElement,IDependentElement
{

    @Element(description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.O)})
    private KontoverbindungNational kontoverbindungAuftraggeber;

    @Element(description = {@ElementDescription(number = 3, length = -2048)})
    @an
    private String freitextmeldung;

    @Element(description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O, length = -35)})
    @an
    private String betreff;

    @Element(description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.O, length = -35)})
    @an
    private String empfaengerangaben;

    public HKKDM(byte[] message) {
        super(message);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public KontoverbindungNational getKontoverbindungAuftraggeber() {
        return kontoverbindungAuftraggeber;
    }

    public void setKontoverbindungAuftraggeber(KontoverbindungNational kontoverbindungAuftraggeber) {
        this.kontoverbindungAuftraggeber = kontoverbindungAuftraggeber;
    }

    public String getFreitextmeldung() {
        return freitextmeldung;
    }

    public void setFreitextmeldung(String freitextmeldung) {
        this.freitextmeldung = freitextmeldung;
    }

    public String getBetreff() {
        return betreff;
    }

    public void setBetreff(String betreff) {
        this.betreff = betreff;
    }

    public String getEmpfaengerangaben() {
        return empfaengerangaben;
    }

    public void setEmpfaengerangaben(String empfaengerangaben) {
        this.empfaengerangaben = empfaengerangaben;
    }

    private HIRMS statusElement;

    @Override
    public StatusCode execute(Dialog dialog) {
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        boolean accepted = dataAccessFacade.userMessageSubmitted(dialog.getLegitimationsInfo(),kontoverbindungAuftraggeber,freitextmeldung,betreff,empfaengerangaben);
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(getSegmentkopf().getSegmentNummer()).build());
        StatusCode result;
        Rueckmeldung rueckmeldung;
        if(accepted) {
            rueckmeldung = Rueckmeldung.getRueckmeldung("0010");
            result =  StatusCode.OK;
        } else {
            rueckmeldung = Rueckmeldung.getRueckmeldung("9210");
            result = StatusCode.ERROR;
        }
        statusElement.addRueckmeldung(rueckmeldung);
        return result;
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
