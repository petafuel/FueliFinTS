package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.OeffentlicherSchluessel;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Schluesselname;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Zertifikat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.id;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Name: Übermittlung eines öffentlichen Schlüssels
 * Typ: Segment
 * Sender: Kreditinstitut
 */
public class HIISA extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = 1)})
    @code(restrictions = {"1"})
    private String nachrichtenbeziehungKodiert;

    @Element(
            description = {@ElementDescription(number = 3, length = 0)})
    @id
    private String austauschkontrollereferenz;

    @Element(
            description = {@ElementDescription(number = 4, length = -4)})
    @num
    private Integer nachrichtenreferenznummer;

    @Element(
            description = {@ElementDescription(number = 5, length = -3)})
    @code(restrictions = {"224"})
    private String bezeichnerFunktionstyp;

    @Element(
            description = {@ElementDescription(number = 6)})
    private Schluesselname schluesselname;

    @Element(
            description = {@ElementDescription(number = 7)})
    private OeffentlicherSchluessel oeffentlicherSchluessel;

    @Element(
            description = {@ElementDescription(number = 8, status = ElementDescription.StatusCode.O)})
    private Zertifikat zertifikat;

    public HIISA(byte[] message) {
        super(message);
    }


    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public void setNachrichtenbeziehungKodiert(String nachrichtenbeziehungKodiert) {
        this.nachrichtenbeziehungKodiert = nachrichtenbeziehungKodiert;
    }

    public void setAustauschkontrollereferenz(String austauschkontrollereferenz) {
        this.austauschkontrollereferenz = austauschkontrollereferenz;
    }

    public void setNachrichtenreferenznummer(Integer nachrichtenreferenznummer) {
        this.nachrichtenreferenznummer = nachrichtenreferenznummer;
    }

    public void setBezeichnerFunktionstyp(String bezeichnerFunktionstyp) {
        this.bezeichnerFunktionstyp = bezeichnerFunktionstyp;
    }

    public void setSchluesselname(Schluesselname schluesselname) {
        this.schluesselname = schluesselname;
    }

    public void setOeffentlicherSchluessel(OeffentlicherSchluessel oeffentlicherSchluessel) {
        this.oeffentlicherSchluessel = oeffentlicherSchluessel;
    }

    public void setZertifikat(Zertifikat zertifikat) {
        this.zertifikat = zertifikat;
    }

    @Override
    public String toString() {
        return "HIISA{" +
                "segmentkopf=" + segmentkopf +
                ", nachrichtenbeziehungKodiert='" + nachrichtenbeziehungKodiert + '\'' +
                ", austauschkontrollereferenz='" + austauschkontrollereferenz + '\'' +
                ", nachrichtenreferenznummer=" + nachrichtenreferenznummer +
                ", bezeichnerFunktionstyp='" + bezeichnerFunktionstyp + '\'' +
                ", schluesselname=" + schluesselname +
                ", oeffentlicherSchluessel=" + oeffentlicherSchluessel +
                ", zertifikat=" + zertifikat +
                '}';
    }
}
