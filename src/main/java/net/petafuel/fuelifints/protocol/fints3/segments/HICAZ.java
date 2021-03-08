package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.GebuchteCamtUmsaetze;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;

import java.util.List;

/**
 * Name: Kontoumsätze rückmelden/Zeitraum camt
 * Typ: Segment
 * Segmentart: Geschäftsvorfall
 * Kennung: HICAZ
 * Bezugssegment: HKCAZ
 * Version: 1
 * Anzahl: n
 * Sender: Kreditinstitut
 */
public class HICAZ extends Segment {

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(description = {@ElementDescription(number = 2)})
    private KontoverbindungInternational kontoverbindungInternational;

    @Element(description = @ElementDescription(number = 3, length = -256))
    @an
    private String camtDescriptor;

    @Element(description = @ElementDescription(number = 4))
    private GebuchteCamtUmsaetze gebuchteCamtUmsaetze;

    @Element(description = @ElementDescription(number = 5, status = ElementDescription.StatusCode.O))
    @bin
    private byte[] nichtGebuchteCamtUmsaetze;


    public HICAZ(byte[] message) {
        super(message);
    }

    public HICAZ(String camtDescriptor, List<byte[]> gebuchteUmsaetze, byte[] nichtGebuchteUmsaetze, Integer segmentNummer, Integer segmentVersion) {
        super(new byte[0]);
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(getClass()).setSegmentVersion(segmentVersion).setBezugssegment(segmentNummer).build();
        this.camtDescriptor = camtDescriptor;
        if (gebuchteUmsaetze != null && gebuchteUmsaetze.size() > 0) {
            gebuchteCamtUmsaetze = new GebuchteCamtUmsaetze(new byte[0]);
            for (byte[] b : gebuchteUmsaetze) {
                gebuchteCamtUmsaetze.addCamtUmsatz(SegmentUtil.wrapBinary(b));
            }
        }
        nichtGebuchteCamtUmsaetze = SegmentUtil.wrapBinary(nichtGebuchteUmsaetze);
    }

    public Segmentkopf getSegmentkopf() {
        return segmentkopf;
    }

    public void setSegmentkopf(Segmentkopf segmentkopf) {
        this.segmentkopf = segmentkopf;
    }

    public KontoverbindungInternational getKontoverbindungInternational() {
        return kontoverbindungInternational;
    }

    public void setKontoverbindungInternational(KontoverbindungInternational kontoverbindungInternational) {
        this.kontoverbindungInternational = kontoverbindungInternational;
    }

    public String getCamtDescriptor() {
        return camtDescriptor;
    }

    public void setCamtDescriptor(String camtDescriptor) {
        this.camtDescriptor = camtDescriptor;
    }

    public GebuchteCamtUmsaetze getGebuchteCamtUmsaetze() {
        return gebuchteCamtUmsaetze;
    }

    public void setGebuchteCamtUmsaetze(GebuchteCamtUmsaetze gebuchteCamtUmsaetze) {
        this.gebuchteCamtUmsaetze = gebuchteCamtUmsaetze;
    }

    public byte[] getNichtGebuchteCamtUmsaetze() {
        return nichtGebuchteCamtUmsaetze;
    }

    public void setNichtGebuchteCamtUmsaetze(byte[] nichtGebuchteCamtUmsaetze) {
        this.nichtGebuchteCamtUmsaetze = nichtGebuchteCamtUmsaetze;
    }
}
