package net.petafuel.fuelifints.protocol.fints3.segments.parameter;

import net.petafuel.fuelifints.dataaccess.dataobjects.CommonBankParameterDataObject;
import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.segments.Segment;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Kik;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Name: Bankparameter allgemein
 * Typ: Segment
 * Segmentart:Administration
 * Kennung: HIBPA
 * Bezugssegment: HKVVB
 * Version: 3
 * Sender: Kreditinstitut
 */
public class HIBPA extends Segment {
    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = -3)})
    @num
    private Integer bpd_version;

    @Element(
            description = {@ElementDescription(number = 3)})
    private Kik kreditInstitutskennung;

    @Element(
            description = {@ElementDescription(number = 4, length = -60)})
    @an
    private String kreditinstitutsbezeichnung;

    @Element(
            description = {@ElementDescription(number = 5, length = -3)})
    @num
    private Integer anzahl_geschaeftsvorfallarten;

    @Element(
            description = {@ElementDescription(number = 6)})
    @an
    private String unterstuetzte_sprachen;

    @Element(
            description = {@ElementDescription(number = 7)})
    @an
    private String unterstuetzte_hbci_versionen;

    @Element(
            description = {@ElementDescription(number = 8, length = -4, status = ElementDescription.StatusCode.O)})
    @num
    private Integer maximale_nachrichtengroesse;

    @Element(
            description = {@ElementDescription(number = 9, length = -4, status = ElementDescription.StatusCode.O)})
    @num
    private Integer minimaler_timeoutwert;

    @Element(
            description = {@ElementDescription(number = 10, length = -4, status = ElementDescription.StatusCode.O)})
    @num
    private Integer maximaler_timeoutwert;

    public HIBPA(CommonBankParameterDataObject paramData, int bezugsSegment) {
        super(new byte[0]);
        /*
        this.segmentkopf = new Segmentkopf(
                ("HIBPA:0:3:" +
                        bezugsSegment).getBytes()
        );
        */
        this.segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(HIBPA.class).setSegmentVersion(3).setBezugssegment(bezugsSegment).build();

        this.bpd_version = paramData.getBpd_version();
        this.kreditInstitutskennung = new Kik(("280:" + paramData.getBankleitzahl()).getBytes());
        this.kreditinstitutsbezeichnung = paramData.getKreditinstitutsbezeichnung();
        this.anzahl_geschaeftsvorfallarten = 0; //Wird in HKVVB.execute() gesetzt, abhängig von der Anzahl der unterstützten Geschäftsvorfallarten
        this.unterstuetzte_sprachen = paramData.getUnterstuetzte_Sprachen();
        this.unterstuetzte_hbci_versionen = paramData.getUnterstuetzte_hbci_versionen();
        this.maximale_nachrichtengroesse = paramData.getMaximale_nachrichten_groesse();
        this.minimaler_timeoutwert = paramData.getMinimaler_timeout_wert();
        this.maximaler_timeoutwert = paramData.getMaximaler_timeout_wert();
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public Integer getBpd_version() {
        return bpd_version;
    }

    public void setBpd_version(Integer bpd_version) {
        this.bpd_version = bpd_version;
    }

    public Kik getKreditInstitutskennung() {
        return kreditInstitutskennung;
    }

    public void setKreditInstitutskennung(Kik kreditInstitutskennung) {
        this.kreditInstitutskennung = kreditInstitutskennung;
    }

    public String getKreditinstitutsbezeichnung() {
        return kreditinstitutsbezeichnung;
    }

    public void setKreditinstitutsbezeichnung(String kreditinstitutsbezeichnung) {
        this.kreditinstitutsbezeichnung = kreditinstitutsbezeichnung;
    }

    public Integer getAnzahl_geschaeftsvorfallarten() {
        return anzahl_geschaeftsvorfallarten;
    }

    public void setAnzahl_geschaeftsvorfallarten(Integer anzahl_geschaeftsvorfallarten) {
        this.anzahl_geschaeftsvorfallarten = anzahl_geschaeftsvorfallarten;
    }

    public String getUnterstuetzte_sprachen() {
        return unterstuetzte_sprachen;
    }

    public void setUnterstuetzte_sprachen(String unterstuetzte_sprachen) {
        this.unterstuetzte_sprachen = unterstuetzte_sprachen;
    }

    public String getUnterstuetzte_hbci_versionen() {
        return unterstuetzte_hbci_versionen;
    }

    public void setUnterstuetzte_hbci_versionen(String unterstuetzte_hbci_versionen) {
        this.unterstuetzte_hbci_versionen = unterstuetzte_hbci_versionen;
    }

    public Integer getMaximale_nachrichtengroesse() {
        return maximale_nachrichtengroesse;
    }

    public void setMaximale_nachrichtengroesse(Integer maximale_nachrichtengroesse) {
        this.maximale_nachrichtengroesse = maximale_nachrichtengroesse;
    }

    public Integer getMinimaler_timeoutwert() {
        return minimaler_timeoutwert;
    }

    public void setMinimaler_timeoutwert(Integer minimaler_timeoutwert) {
        this.minimaler_timeoutwert = minimaler_timeoutwert;
    }

    public Integer getMaximaler_timeoutwert() {
        return maximaler_timeoutwert;
    }

    public void setMaximaler_timeoutwert(Integer maximaler_timeoutwert) {
        this.maximaler_timeoutwert = maximaler_timeoutwert;
    }
}
