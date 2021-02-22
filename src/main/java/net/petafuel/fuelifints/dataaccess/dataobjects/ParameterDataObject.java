package net.petafuel.fuelifints.dataaccess.dataobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Klasse, um Parameterdaten aus den entsprechenden Facade Implementierungen zurückzugeben
 * <p/>
 * FinTS 3.0: Siehe Formals, D.1
 */
public class ParameterDataObject implements IDataObject {
    private static final Logger LOG = LogManager.getLogger(ParameterDataObject.class);

    private String segmentName;

    private int segmentVersion;

    private int maximaleAnzahlAuftraege;

    private Integer anzahlSignaturenMindestens;

    private String sicherheitsKlasse;

    private String parameter;

    public ParameterDataObject(String segmentName, int segmentVersion, int maximaleAnzahlAuftraege, int anzahlSignaturenMindestens, String sicherheitsKlasse, String parameter) {
        this.segmentName = segmentName;
        this.segmentVersion = segmentVersion;
        this.maximaleAnzahlAuftraege = maximaleAnzahlAuftraege;
        LOG.debug("Anzahl signaturen mindestens: {} {}", anzahlSignaturenMindestens, (anzahlSignaturenMindestens < 0));
        this.anzahlSignaturenMindestens = anzahlSignaturenMindestens < 0 ? null : anzahlSignaturenMindestens;
        this.sicherheitsKlasse = sicherheitsKlasse;
        this.parameter = parameter;
    }

    public Integer getAnzahlSignaturenMindestens() {
        return anzahlSignaturenMindestens;
    }

    public void setAnzahlSignaturenMindestens(int anzahlSignaturenMindestens) {
        this.anzahlSignaturenMindestens = anzahlSignaturenMindestens;
    }

    public int getMaximaleAnzahlAuftraege() {
        return maximaleAnzahlAuftraege;
    }

    public void setMaximaleAnzahlAuftraege(int maximaleAnzahlAuftraege) {
        this.maximaleAnzahlAuftraege = maximaleAnzahlAuftraege;
    }


    public int getSegmentVersion() {
        return segmentVersion;
    }

    public void setSegmentVersion(int segmentVersion) {
        this.segmentVersion = segmentVersion;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public String getSicherheitsKlasse() {
        return sicherheitsKlasse;
    }

    public void setSicherheitsKlasse(String sicherheitsKlasse) {
        this.sicherheitsKlasse = sicherheitsKlasse;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public String toString() {
        return "ParameterDataObject{" +
                "segmentName='" + segmentName + '\'' +
                ", segmentVersion=" + segmentVersion +
                ", maximaleAnzahlAuftraege=" + maximaleAnzahlAuftraege +
                ", anzahlSignaturenMindestens=" + anzahlSignaturenMindestens +
                ", sicherheitsKlasse='" + sicherheitsKlasse + '\'' +
                ", parameter='" + parameter + '\'' +
                '}';
    }
}
