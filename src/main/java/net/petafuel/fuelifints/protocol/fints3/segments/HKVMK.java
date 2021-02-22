package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungNational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.jn;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Name:  Vormerkposten anfordern
 Typ:  Segment
 Segmentart:  Geschäftsvorfall
 Kennung:  HKVMK
 Bezugssegment:  -
 Version:  1
 Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKVMK extends Segment implements IExecutableElement, IDependentElement {

    @Element(
            description = {@ElementDescription(number = 1)}
    )
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @ApplicantAccount
    private KontoverbindungNational kontoverbindungAuftraggeber;

    @Element(
            description = {@ElementDescription(number = 3, length = 1)})
    @jn
    private String alleKonten;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.C, length = -4)})
    @num
    private Integer maximaleAnzahlAuftraege;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.C, length = -35)})
    @an
    private String aufsetzpunkt;

    public HKVMK(byte[] message) {
        super(message);
    }


    private HIRMS statusElement;
    private List<IMessageElement> replyMessageElements;

    @Override
    public StatusCode execute(Dialog dialog) {
        statusElement = new HIRMS(new byte[0]);
        statusElement.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(segmentkopf.getSegmentNummer()).build());

        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());

        byte[] nichtGebuchteUmsaetze = dataAccessFacade.getNichtGebuchteUmsaetze(kontoverbindungAuftraggeber.getKontonummer(), null, null, dialog.getLegitimationsInfo());
        if ((nichtGebuchteUmsaetze == null || nichtGebuchteUmsaetze.length == 0)) {
            Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("3010");
            statusElement.addRueckmeldung(rueckmeldung);
            return StatusCode.WARNING;
        }

        HIVMK hivmk = new HIVMK(nichtGebuchteUmsaetze, getSegmentkopf().getSegmentNummer(), getSegmentkopf().getSegmentVersion());
        replyMessageElements = new LinkedList<IMessageElement>();
        replyMessageElements.add(hivmk);

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
