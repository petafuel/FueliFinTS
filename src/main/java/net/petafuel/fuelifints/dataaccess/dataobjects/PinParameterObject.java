package net.petafuel.fuelifints.dataaccess.dataobjects;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.GeschaeftsvorfallspezifischePinTanInformation;

import java.util.LinkedList;
import java.util.List;

public class PinParameterObject {
    private Integer maximaleAnzahlAuftraege;
    private Integer anzahlSignaturenMindestens;
    private Integer sicherheitsklasse = 0;
    private Integer minPinLaenge;
    private Integer maxPinLaenge;
    private Integer maxTanLaenge;
    private String textBelegungBenutzerkennung;
    private String textBelegungKundenId;
    private List<GeschaeftsvorfallspezifischePinTanInformation> tanInfo = new LinkedList<GeschaeftsvorfallspezifischePinTanInformation>();

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
        return minPinLaenge;
    }

    public void setMinPinLaenge(Integer minPinLaenge) {
        this.minPinLaenge = minPinLaenge;
    }

    public Integer getMaxPinLaenge() {
        return maxPinLaenge;
    }

    public void setMaxPinLaenge(Integer maxPinLaenge) {
        this.maxPinLaenge = maxPinLaenge;
    }

    public Integer getMaxTanLaenge() {
        return maxTanLaenge;
    }

    public void setMaxTanLaenge(Integer maxTanLaenge) {
        this.maxTanLaenge = maxTanLaenge;
    }

    public String getTextBelegungBenutzerkennung() {
        return textBelegungBenutzerkennung;
    }

    public void setTextBelegungBenutzerkennung(String textBelegungBenutzerkennung) {
        this.textBelegungBenutzerkennung = textBelegungBenutzerkennung;
    }

    public String getTextBelegungKundenId() {
        return textBelegungKundenId;
    }

    public void setTextBelegungKundenId(String textBelegungKundenId) {
        this.textBelegungKundenId = textBelegungKundenId;
    }

    public void addSegment(String segmentkennung, boolean tanErforderlich) {
        GeschaeftsvorfallspezifischePinTanInformation geschaeftsvorfallspezifischePinTanInformation = new GeschaeftsvorfallspezifischePinTanInformation(segmentkennung, tanErforderlich);
        tanInfo.add(geschaeftsvorfallspezifischePinTanInformation);
    }

    public List<GeschaeftsvorfallspezifischePinTanInformation> getGeschaeftsvorfallspezifischePinTanInformationen() {
        return tanInfo;
    }

    public Integer getSicherheitsklasse() {
        return sicherheitsklasse;
    }

    public void setSicherheitsklasse(Integer sicherheitsklasse) {
        this.sicherheitsklasse = sicherheitsklasse;
    }

    public boolean isTanErforderlich(String segmentkennung) {
        for (GeschaeftsvorfallspezifischePinTanInformation info: tanInfo) {
            if (info.getSegmentkennung().equals(segmentkennung))
                return info.getTanErforderlich().equals("J");
        }
        return true;
    }

}
