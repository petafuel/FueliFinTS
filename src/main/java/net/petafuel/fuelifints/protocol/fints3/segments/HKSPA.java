package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.DataAccessFacade;
import net.petafuel.fuelifints.dataaccess.DataAccessFacadeManager;
import net.petafuel.fuelifints.dataaccess.dataobjects.AccountDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IDependentElement;
import net.petafuel.fuelifints.model.IExecutableElement;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Kik;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungNational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungZvInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Name:  SEPA-Kontoverbindung anfordern
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKSPA
 * Bezugssegment:  -
 * Segmentversion:  1
 * Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKSPA extends Segment implements IExecutableElement, IDependentElement {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    private List<KontoverbindungNational> kontoverbindungenNational;

    private HIRMS statusElement;
    private List<IMessageElement> replyElements;

    public HKSPA(byte[] message) {
        super(message);
    }

    @Override
    public StatusCode execute(Dialog dialog) {
        DataAccessFacade dataAccessFacade = DataAccessFacadeManager.getAccessFacade(dialog.getBankId());
        HISPA hispa = new HISPA(new byte[0]);
        hispa.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentVersion(segmentkopf.getSegmentVersion()).setSegmentKennung(HISPA.class).setBezugssegment(segmentkopf.getSegmentNummer()).build());
        if (kontoverbindungenNational != null) {

            for (KontoverbindungNational kontoverbindungNational : kontoverbindungenNational) {
                if (kontoverbindungNational == null) {
                    continue;
                }
                KontoverbindungZvInternational ktz = new KontoverbindungZvInternational(new byte[0]);
                ktz.setKontoverwendungSepa("J");
                ktz.setIban(SegmentUtil.accountToIban(kontoverbindungNational.getKontonummer(), kontoverbindungNational.getKreditinstitutskennung().getKreditinstitutscode()));
                ktz.setBic(dataAccessFacade.getBic());
                ktz.setKonto_depot_nummer(kontoverbindungNational.getKontonummer());
                ktz.setKreditsinstitutskennung(kontoverbindungNational.getKreditinstitutskennung());
                hispa.addSepaKontoverbindung(ktz);
            }
        } else {
            ArrayList<AccountDataObject> accountData = dataAccessFacade.getAccountData(dialog.getLegitimationsInfo());
            if (accountData != null) {
                for (AccountDataObject accountDataObject : accountData) {
                    if (!accountDataObject.getErlaubteGeschaeftsvorfaelle().contains("HKSPA:1")) {
                        continue;
                    }
                    KontoverbindungZvInternational ktz = new KontoverbindungZvInternational(new byte[0]);
                    ktz.setKontoverwendungSepa("J");
                    ktz.setIban(SegmentUtil.accountToIban(accountDataObject.getKontonummer(), dialog.getLegitimationsInfo().getBankId()));
                    ktz.setBic(dataAccessFacade.getBic());
                    ktz.setKonto_depot_nummer(accountDataObject.getKontonummer());
                    Kik kik = new Kik(("280:" + dialog.getLegitimationsInfo().getBankId()).getBytes());
                    ktz.setKreditsinstitutskennung(kik);
                    hispa.addSepaKontoverbindung(ktz);
                }
            }
        }
        replyElements = new LinkedList<IMessageElement>();
        replyElements.add(hispa);
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

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public List<KontoverbindungNational> getKontoverbindungenNational() {
        return kontoverbindungenNational;
    }

    public void setKontoverbindungenNational(List<KontoverbindungNational> kontoverbindungenNational) {
        this.kontoverbindungenNational = kontoverbindungenNational;
    }

    public void addKontoverbindungNational(KontoverbindungNational kontoverbindungNational) {
        if (kontoverbindungenNational == null) {
            kontoverbindungenNational = new LinkedList<KontoverbindungNational>();
        }
        kontoverbindungenNational.add(kontoverbindungNational);
    }
}
