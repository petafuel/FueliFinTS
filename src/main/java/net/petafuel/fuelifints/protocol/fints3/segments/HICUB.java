package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.AngabenEmpfaengerkonten;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

import java.util.List;

/**
 * Name: Empfängerkontenbestand rückmelden
 * Kennung: HICUB
 * Version: 1
 * Sender: Kreditinstitut
 */
public class HICUB extends Segment {

    @Element(description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;
    @Element(description = {@ElementDescription(number = 2)})
    private KontoverbindungInternational kontoverbindungInternational;
    @Element(description = {@ElementDescription(number = 3)})
    private List<AngabenEmpfaengerkonten> angabenEmpfaengerkonten;

    public HICUB(byte[] message) {
        super(message);
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

    public List<AngabenEmpfaengerkonten> getAngabenEmpfaengerkonten() {
        return angabenEmpfaengerkonten;
    }

    public void setAngabenEmpfaengerkonten(List<AngabenEmpfaengerkonten> angabenEmpfaengerkonten) {
        this.angabenEmpfaengerkonten = angabenEmpfaengerkonten;
    }
}
