package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Bezugsnachricht;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dig;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Name: Nachrichtenkopf
 * Typ: Segment
 * Segmentart: Administration
 * Kennung: HNHBK
 * Sender: Kunde/Kreditinstitut
 */
public class HNHBK extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2,
                    length = 12)})
    @dig
    private String nachrichtengroesse;

    @Element(
            description = {@ElementDescription(number = 3,
                    length = -3)})
    @num
    private Integer hbci_version;
    @Element(
            description = {@ElementDescription(number = 4,
                    length = 0)})
    @id
    private String dialog_id;
    @Element(
            description = {@ElementDescription(number = 5,
                    length = -4)})
    @num
    private Integer nachrichtennummer;

    @Element(
            description = {@ElementDescription(number = 6,
                    status = ElementDescription.StatusCode.C/*,
           length = 0*/)})
    private Bezugsnachricht bezugsnachricht;


    // HNHBK Example:
    // HNHBK:1:3+MESSAGESIZE0+220+0+1'


    public HNHBK(byte[] message) {
        super(message);
    }

    @Override
    public String toString() {
        return "HNHBK{" +
                "segmentkopf=" + segmentkopf +
                ", nachrichtengroesse='" + nachrichtengroesse + '\'' +
                ", hbci_version='" + hbci_version + '\'' +
                ", dialog_id='" + dialog_id + '\'' +
                ", nachrichtennummer='" + nachrichtennummer + '\'' +
                ", bezugsnachricht='" + bezugsnachricht + '\'' +
                '}';
    }

    public String getNachrichtengroesse() {
        return nachrichtengroesse;
    }

    public Integer getNachrichtennummer() {
        return nachrichtennummer;
    }

    public Bezugsnachricht getBezugsnachricht() {
        return bezugsnachricht;
    }

    public String getDialogId() {
        return dialog_id;
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public void setNachrichtengroesse(String nachrichtengroesse) {
        this.nachrichtengroesse = nachrichtengroesse;
    }

    public Integer getHbci_version() {
        return hbci_version;
    }

    public void setHbci_version(Integer hbci_version) {
        this.hbci_version = hbci_version;
    }

    public String getDialog_id() {
        return dialog_id;
    }

    public void setDialog_id(String dialog_id) {
        this.dialog_id = dialog_id;
    }

    public void setNachrichtennummer(Integer nachrichtennummer) {
        this.nachrichtennummer = nachrichtennummer;
    }

    public void setBezugsnachricht(Bezugsnachricht bezugsnachricht) {
        this.bezugsnachricht = bezugsnachricht;
    }
}
