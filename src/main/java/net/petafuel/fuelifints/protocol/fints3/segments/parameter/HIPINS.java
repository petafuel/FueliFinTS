package net.petafuel.fuelifints.protocol.fints3.segments.parameter;

import net.petafuel.fuelifints.dataaccess.dataobjects.PinParameterObject;
import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.segments.Segment;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.GeschaeftsvorfallspezifischePinTanInformation;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.PinTanSpezifischeInformationen;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

import java.util.LinkedList;
import java.util.List;

/**
 * PIN/TAN-spezifische Informationen (HIPINS)
 * Die  für  die  Kennzeichnung  des  PIN/TAN-Verfahrens  notwendige  BPD-/UPD-
 * Erweiterung wird in Form eines speziellen Parametersegmentes realisiert, welches
 * sich auf keinen echten Geschäftsvorfall bezieht, sondern Daten zu allen unterstütz-
 * ten Geschäftsvorfällen aufnehmen kann.
 * Das  Spezialsegment  HIPINS  wird  verwendet,  um  in  die  BPD-Segmentfolge
 * PIN/TAN-spezifische Daten einzufügen. Aufgrund seines Aufbaus analog zu einem
 * Segmentparametersegment  wird  es  von  Kundenprodukten,  die  das  PIN/TAN-
 * Verfahren nicht unterstützen, ignoriert, da es sich auf einen ihnen unbekannten Ge-
 * schäftsvorfall zu beziehen scheint.
 * Die in HIPINS aufgeführten Geschäftsvorfälle dürfen vom Kunden in über PIN/TAN
 * abgesicherte  Nachrichten  eingestellt  werden,  sofern  sie  in  den  BPD  und  UPD  als
 * generell  erlaubt  hinterlegt  sind.  Alle  übrigen  Geschäftsvorfälle  können  mit  dem
 * PIN/TAN-Verfahren nicht verwendet werden.
 */
public class HIPINS extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = -3)})
    @num
    private Integer maximaleAnzahlAuftraege;

    @Element(
            description = {@ElementDescription(number = 3, length = 1)})
    @num
    private Integer anzahlSignaturenMindestens;

    @Element(
            description = {@ElementDescription(number = 4, length = 1)})
    @code(restrictions = {"0", "1", "2", "3", "4"})
    protected String sicherheitsKlasse;

    @Element(
            description = {@ElementDescription(number = 4)})
    private PinTanSpezifischeInformationen pinTanSpezifischeInformationen;

    public HIPINS(byte[] message) {
        super(message);
    }

    public HIPINS(PinParameterObject pinParameterObject, int bezugssegment) {
        super(new byte[0]);

        this.segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(HIPINS.class).setSegmentVersion(1).setBezugssegment(bezugssegment).build();

        this.maximaleAnzahlAuftraege = pinParameterObject.getMaximaleAnzahlAuftraege();
        this.anzahlSignaturenMindestens = pinParameterObject.getAnzahlSignaturenMindestens();
        this.sicherheitsKlasse = pinParameterObject.getSicherheitsklasse().toString();

        this.pinTanSpezifischeInformationen = new PinTanSpezifischeInformationen();

        this.pinTanSpezifischeInformationen.minPinLaenge = pinParameterObject.getMinPinLaenge();
        this.pinTanSpezifischeInformationen.maxPinLaenge = pinParameterObject.getMaxPinLaenge();
        this.pinTanSpezifischeInformationen.maxTanLaenge = pinParameterObject.getMaxTanLaenge();
        this.pinTanSpezifischeInformationen.textBelegungBenutzerkennung = pinParameterObject.getTextBelegungBenutzerkennung();
        this.pinTanSpezifischeInformationen.textBelegungKundenId = pinParameterObject.getTextBelegungKundenId();
        this.pinTanSpezifischeInformationen.geschaeftsvorfallspezifischePinTanInformationen = pinParameterObject.getGeschaeftsvorfallspezifischePinTanInformationen();
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public Integer getMaximaleAnzahlAuftraege() {
        return maximaleAnzahlAuftraege;
    }

    public void setMaximaleAnzahlAuftraege(Integer maximaleAnzahlAuftraege) {
        this.maximaleAnzahlAuftraege = maximaleAnzahlAuftraege;
    }

    public Integer getAnzahlSignaturenMindestens() {
        return anzahlSignaturenMindestens;
    }

    public void setAnzahlSignaturenMindestens(Integer anzahlSignaturenMindestens) {
        this.anzahlSignaturenMindestens = anzahlSignaturenMindestens;
    }

    public Integer getMinPinLaenge() {
        return pinTanSpezifischeInformationen.minPinLaenge;
    }

    public void setMinPinLaenge(Integer minPinLaenge) {
        this.pinTanSpezifischeInformationen.minPinLaenge = minPinLaenge;
    }

    public Integer getMaxPinLaenge() {
        return pinTanSpezifischeInformationen.maxPinLaenge;
    }

    public void setMaxPinLaenge(Integer maxPinLaenge) {
        this.pinTanSpezifischeInformationen.maxPinLaenge = maxPinLaenge;
    }

    public Integer getMaxTanLaenge() {
        return pinTanSpezifischeInformationen.maxTanLaenge;
    }

    public void setMaxTanLaenge(Integer maxTanLaenge) {
        this.pinTanSpezifischeInformationen.maxTanLaenge = maxTanLaenge;
    }

    public String getTextBelegungBenutzerkennung() {
        return pinTanSpezifischeInformationen.textBelegungBenutzerkennung;
    }

    public void setTextBelegungBenutzerkennung(String textBelegungBenutzerkennung) {
        this.pinTanSpezifischeInformationen.textBelegungBenutzerkennung = textBelegungBenutzerkennung;
    }

    public String getTextBelegungKundenId() {
        return pinTanSpezifischeInformationen.textBelegungKundenId;
    }

    public void setTextBelegungKundenId(String textBelegungKundenId) {
        this.pinTanSpezifischeInformationen.textBelegungKundenId = textBelegungKundenId;
    }

    public List<GeschaeftsvorfallspezifischePinTanInformation> getGeschaeftsvorfallspezifischePinTanInformationen() {
        return pinTanSpezifischeInformationen.geschaeftsvorfallspezifischePinTanInformationen;
    }

    public void setGeschaeftsvorfallspezifischePinTanInformationen(List<GeschaeftsvorfallspezifischePinTanInformation> geschaeftsvorfallspezifischePinTanInformationen) {
        this.pinTanSpezifischeInformationen.geschaeftsvorfallspezifischePinTanInformationen = geschaeftsvorfallspezifischePinTanInformationen;
    }

    public void addGeschaeftsvorfallspezifischePinTanInformation(GeschaeftsvorfallspezifischePinTanInformation geschaeftsvorfallspezifischePinTanInformation) {
        if (pinTanSpezifischeInformationen.geschaeftsvorfallspezifischePinTanInformationen == null) {
            pinTanSpezifischeInformationen.geschaeftsvorfallspezifischePinTanInformationen = new LinkedList<GeschaeftsvorfallspezifischePinTanInformation>();
        }
        pinTanSpezifischeInformationen.geschaeftsvorfallspezifischePinTanInformationen.add(geschaeftsvorfallspezifischePinTanInformation);
    }
}
