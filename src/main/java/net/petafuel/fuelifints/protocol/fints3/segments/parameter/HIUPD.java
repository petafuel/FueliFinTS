package net.petafuel.fuelifints.protocol.fints3.segments.parameter;

import net.petafuel.fuelifints.dataaccess.dataobjects.AccountDataObject;
import net.petafuel.fuelifints.exceptions.HBCISyntaxException;
import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.segments.Segment;
import net.petafuel.fuelifints.protocol.fints3.segments.SegmentUtil;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.ErlaubteGeschaeftsvorfaelle;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Kontolimit;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungNational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.cur;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;
import net.petafuel.fuelifints.support.ByteSplit;

import java.util.LinkedList;
import java.util.List;

/**
 * Name:  Kontoinformation
 * Typ:   Segment
 * Segmentart: Administration
 * Kennung:  HIUPD
 * Bezugssegment:  HKVVB
 * Version:  6
 * Sender:   Kreditinstitut
 */
public class HIUPD extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    private KontoverbindungNational kontoverbindung;

    @Element(
            description = {@ElementDescription(number = 3, length = -34, status = ElementDescription.StatusCode.O)})
    @an
    private String iban;

    @Element(
            description = {@ElementDescription(number = 4)})
    @id
    private String kunden_id;

    @Element(
            description = {@ElementDescription(number = 5, length = -2, status = ElementDescription.StatusCode.O)})
    @num
    private String kontoart;

    @Element(
            description = {@ElementDescription(number = 6, status = ElementDescription.StatusCode.O)})
    @cur
    private String kontowaehrung = "EUR";

    @Element(
            description = {@ElementDescription(number = 7, length = -27)})
    @an
    private String name_des_kontoinhabers_1;

    @Element(
            description = {@ElementDescription(number = 8, length = -27, status = ElementDescription.StatusCode.O)})
    @an
    private String name_des_kontoinhabers_2;

    @Element(
            description = {@ElementDescription(number = 9, length = -30, status = ElementDescription.StatusCode.O)})
    @an
    private String kontoproduktbezeichnung;

    @Element(
            description = {@ElementDescription(number = 10, status = ElementDescription.StatusCode.O)})
    private Kontolimit kontolimit;

    @Element(
            description = {@ElementDescription(number = 11, status = ElementDescription.StatusCode.O)})
    private List<ErlaubteGeschaeftsvorfaelle> erlaubte_Geschaeftsvorfaelle;

    @Element(
            description = {@ElementDescription(number = 12, length = -2048, status = ElementDescription.StatusCode.O)})
    @an
    private String erweiterung_kontobezogen;

    public HIUPD(AccountDataObject accountDataObject, int bezugsSegment) {
        super(new byte[0]);

        /*
        this.segmentkopf = new Segmentkopf(
                ("HIUPD:0:6:" +
                        bezugsSegment).getBytes()
        );
        */

        this.segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(HIUPD.class).setSegmentVersion(6).setBezugssegment(bezugsSegment).build();

        this.kontoverbindung = new KontoverbindungNational((accountDataObject.getKontonummer() + "::280:" + accountDataObject.getBankleitzahl()).getBytes());
        this.kunden_id = accountDataObject.getKunden_id();

        this.iban = SegmentUtil.accountToIban(accountDataObject.getKontonummer(), accountDataObject.getBankleitzahl());

        String name = accountDataObject.getName();
        if (name.length() > 27) {
            this.name_des_kontoinhabers_1 = name.substring(0, 27);
            this.name_des_kontoinhabers_2 = name.substring(27);
        } else
            this.name_des_kontoinhabers_1 = name;

        this.kontoproduktbezeichnung = accountDataObject.getProduktbzeichnung();
        List<byte[]> splitted = null;
        try {
            splitted = ByteSplit.split(accountDataObject.getErlaubteGeschaeftsvorfaelle().getBytes(), ByteSplit.MODE_DEG);
        } catch (HBCISyntaxException e) {
            //ignored here
        }
        this.erlaubte_Geschaeftsvorfaelle = new LinkedList<>();
        if (splitted != null) {
            for (byte[] split : splitted) {
                ErlaubteGeschaeftsvorfaelle erlaubteGeschaeftsvorfaelle = new ErlaubteGeschaeftsvorfaelle(split);
                erlaubte_Geschaeftsvorfaelle.add(erlaubteGeschaeftsvorfaelle);
            }
        }
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public KontoverbindungNational getKontoverbindung() {
        return kontoverbindung;
    }

    public void setKontoverbindung(KontoverbindungNational kontoverbindung) {
        this.kontoverbindung = kontoverbindung;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getKunden_id() {
        return kunden_id;
    }

    public void setKunden_id(String kunden_id) {
        this.kunden_id = kunden_id;
    }

    public String getKontoart() {
        return kontoart;
    }

    public void setKontoart(String kontoart) {
        this.kontoart = kontoart;
    }

    public String getKontowaehrung() {
        return kontowaehrung;
    }

    public void setKontowaehrung(String kontowaehrung) {
        this.kontowaehrung = kontowaehrung;
    }

    public String getName_des_kontoinhabers_1() {
        return name_des_kontoinhabers_1;
    }

    public void setName_des_kontoinhabers_1(String name_des_kontoinhabers_1) {
        this.name_des_kontoinhabers_1 = name_des_kontoinhabers_1;
    }

    public String getName_des_kontoinhabers_2() {
        return name_des_kontoinhabers_2;
    }

    public void setName_des_kontoinhabers_2(String name_des_kontoinhabers_2) {
        this.name_des_kontoinhabers_2 = name_des_kontoinhabers_2;
    }

    public String getKontoproduktbezeichnung() {
        return kontoproduktbezeichnung;
    }

    public void setKontoproduktbezeichnung(String kontoproduktbezeichnung) {
        this.kontoproduktbezeichnung = kontoproduktbezeichnung;
    }

    public List<ErlaubteGeschaeftsvorfaelle> getErlaubte_Geschaeftsvorfaelle() {
        return erlaubte_Geschaeftsvorfaelle;
    }

    public void setErlaubte_Geschaeftsvorfaelle(List<ErlaubteGeschaeftsvorfaelle> erlaubte_Geschaeftsvorfaelle) {
        this.erlaubte_Geschaeftsvorfaelle = erlaubte_Geschaeftsvorfaelle;
    }

    public String getErweiterung_kontobezogen() {
        return erweiterung_kontobezogen;
    }

    public void setErweiterung_kontobezogen(String erweiterung_kontobezogen) {
        this.erweiterung_kontobezogen = erweiterung_kontobezogen;
    }

    public Kontolimit getKontolimit() {
        return kontolimit;
    }

    public void setKontolimit(Kontolimit kontolimit) {
        this.kontolimit = kontolimit;
    }
}
