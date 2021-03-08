package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.UnterstuetzteCamtMessages;
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
 * Name: Kontoumsätze anfordern/Zeitraum camt
 * Typ: Segment
 * Segmentart: Geschäftsvorfall
 * Kennung: HKCAZ
 * Bezugssegment: -
 * Version: 1
 * Sender: Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED})
public class HKCAZ extends Segment implements IExecutableElement, IDependentElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(description = {@ElementDescription(number = 2)})
    private KontoverbindungInternational kontoverbindungInternational;

    @Element(description = {
            @ElementDescription(number = 3)
    })
    private UnterstuetzteCamtMessages unterstuetzteCamtMessages;

    @Element(description =  @ElementDescription(number = 4))
    @jn
    private String alleKonten;

    @Element(description = @ElementDescription(number = 5,status = ElementDescription.StatusCode.O))
    @dat
    private String vonDatum;

    @Element(description = @ElementDescription(number = 6,status = ElementDescription.StatusCode.O))
    @dat
    private String bisDatum;

    @Element(description = @ElementDescription(number = 7,status = ElementDescription.StatusCode.C,length = -4))
    @num
    private Integer maximaleAnzahlEintraege;

    @Element(description = @ElementDescription(number = 8, status = ElementDescription.StatusCode.C,length = -35))
    @an
    private String aufsetzpunkt;


    private HIRMS statusElement;
    private List<IMessageElement> replyMessageElements;

    public HKCAZ(byte[] message) {
        super(message);
    }

    @Override
    public StatusCode execute(Dialog dialog) {
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());

        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        boolean operationAllowed;
        String accountNr = "";
            accountNr = SegmentUtil.ibanToAccountNr(kontoverbindungInternational.getIban());
            operationAllowed = dataAccessFacade.operationAllowedForAccount(dialog.getLegitimationsInfo(), SegmentUtil.ibanToAccountNr(kontoverbindungInternational.getIban()), this.getClass());


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
                if (this.bisDatum != null && this.bisDatum.isEmpty()) {
                    bisDatum = simpleDateFormat.parse(this.bisDatum);
                }
            } catch (ParseException e) {
                //ignored sollten bereits geprüft sein
            }
        }

        List<byte[]> gebuchteUmsaetze = dataAccessFacade.getGebuchteCamtUmsaetze(accountNr, vonDatum, bisDatum, dialog.getLegitimationsInfo(), unterstuetzteCamtMessages.getCamtDescriptor().get(0));


        if ((gebuchteUmsaetze == null || gebuchteUmsaetze.size() == 0)) {
            Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("3010");
            statusElement.addRueckmeldung(rueckmeldung);
            return StatusCode.WARNING;
        }
        byte[] nichtGebuchteUmsaetze = dataAccessFacade.getNichtGebuchteCamtUmsaetze(accountNr, vonDatum, bisDatum, dialog.getLegitimationsInfo());
        HICAZ hicaz = new HICAZ(unterstuetzteCamtMessages.getCamtDescriptor().get(0),gebuchteUmsaetze, nichtGebuchteUmsaetze, getSegmentkopf().getSegmentNummer(), getSegmentkopf().getSegmentVersion());
        hicaz.setKontoverbindungInternational(kontoverbindungInternational);
        replyMessageElements = new LinkedList<>();
        replyMessageElements.add(hicaz);

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
