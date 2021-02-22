package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.dataaccess.dataobjects.KommunikationsParameterData;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Kik;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KommunikationsParameter;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

import java.util.LinkedList;
import java.util.List;

/**
 * Name:  Kommunikationszugang rückmelden
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HIKOM
 * Bezugssegment:  HKKOM
 * Version:  4
 * Sender:  Kreditinstitut
 */
public class HIKOM extends Segment {

    @Element(description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(description = {@ElementDescription(number = 2)})
    private Kik kreditinstitutskennung;

    /**
     * Standardsprache
     * Es  ist  ein  Sprachkennzeichen  einzustellen,  welches  Standardsprache  und
     * -zeichensatz  des  Kreditinstituts  festlegt  (s.  auch  DE  „Dialogsprache“).  Die-
     * ses  Kennzeichen  bestimmt,  mit  welchem  Zeichensatz  die  Dialoginitialisie-
     * rungsnachricht  des  Kunden  gebildet  werden  muss.  Nach  dieser  Nachricht
     * verliert die Standardsprache ihre Gültigkeit, da der Kunde in der Dialoginitia
     * lisierung die Dialogsprache wählt, welche evtl. von der Standardsprache ab-
     * weicht.
     * Codierung:
     * 1: Deutsch, Code ‚de’ (German), Subset Deutsch, Codeset 1 (Latin 1)
     * 2: Englisch, Code ‚en’ (English), Subset Englisch, Codeset 1 (Latin 1)
     * 3: Französisch, Code ‚fr’ (French), Subset Französisch, Codeset 1 (Latin 1)
     */
    @Element(description = {@ElementDescription(number = 3)})
    private Integer standardsprache;

    @Element(description = {@ElementDescription(number = 4)})
    private List<KommunikationsParameter> kommunikationsParameter;

    public HIKOM(int segmentVersion, int bezugssegment, KommunikationsParameterData kommunikationsParameterData) {
        super(new byte[0]);
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(HIKOM.class).setSegmentVersion(segmentVersion).setBezugssegment(bezugssegment).build();
        standardsprache = 1;
        kreditinstitutskennung = new Kik(new byte[0]);
        kreditinstitutskennung.setKreditinstitutscode(kommunikationsParameterData.getBankId());
        kreditinstitutskennung.setLaenderkennzeichen("280");
        kommunikationsParameter = new LinkedList<>();
        for (String kommunikationszugangsAdresse : kommunikationsParameterData.getKommunikationszugangsAdressen()) {
            kommunikationsParameter.add(new KommunikationsParameter(kommunikationszugangsAdresse));
        }
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public Kik getKreditinstitutskennung() {
        return kreditinstitutskennung;
    }

    public void setKreditinstitutskennung(Kik kreditinstitutskennung) {
        this.kreditinstitutskennung = kreditinstitutskennung;
    }

    public Integer getStandardsprache() {
        return standardsprache;
    }

    public void setStandardsprache(Integer standardsprache) {
        this.standardsprache = standardsprache;
    }

    public List<KommunikationsParameter> getKommunikationsParameter() {
        return kommunikationsParameter;
    }

    public void setKommunikationsParameter(List<KommunikationsParameter> kommunikationsParameter) {
        this.kommunikationsParameter = kommunikationsParameter;
    }
}
