package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.segments.Segment;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

public class Segmentkopf extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1,
                    length = -6)})
    @an
    String segmentKennung;

    @Element(
            description = {@ElementDescription(number = 2,
                    length = -3)})
    @num
    Integer segmentNummer;

    @Element(
            description = {@ElementDescription(number = 3,
                    length = -3)})
    @num
    Integer segmentVersion;

    @Element(
            description = {@ElementDescription(number = 4,
                    status = ElementDescription.StatusCode.C,
                    length = -3)})
    @num
    Integer bezugssegment;

    private Segmentkopf() {
        super(new byte[0]);
    }

    public static class Builder {
        private String segmentKennung;
        private Integer segmentNumber;
        private Integer segmentVersion;
        private Integer bezugssegment;

        public static Builder newInstance() {
            return new Builder();
        }

        public Builder setSegmentKennung(String segmentKennung) {
            this.segmentKennung = segmentKennung;
            return this;
        }

        public Builder setSegmentKennung(Class<? extends Segment> cls) {
            this.segmentKennung = cls.getSimpleName();
            return this;
        }

        public Builder setSegmentNumber(int segmentNumber) {
            this.segmentNumber = segmentNumber;
            return this;
        }

        public Builder setSegmentVersion(int segmentVersion) {
            this.segmentVersion = segmentVersion;
            return this;
        }

        public Builder setBezugssegment(int bezugssegment) {
            this.bezugssegment = bezugssegment;
            return this;
        }

        public Segmentkopf build() {
            Segmentkopf segmentkopf = new Segmentkopf();
            segmentkopf.setSegmentKennung(this.segmentKennung);
            segmentkopf.setSegmentNummer(this.segmentNumber);
            segmentkopf.setSegmentVersion(this.segmentVersion);
            segmentkopf.setBezugssegment(this.bezugssegment);
            return segmentkopf;
        }
    }

    public Segmentkopf(byte[] message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Segmentkopf{" +
                "segmentKennung='" + segmentKennung + '\'' +
                ", segmentNummer='" + segmentNummer + '\'' +
                ", segmentVersion='" + segmentVersion + '\'' +
                ", bezugssegment='" + bezugssegment + '\'' +
                '}';
    }

    public String getSegmentKennung() {
        return segmentKennung;
    }

    public void setSegmentKennung(String segmentKennung) {
        this.segmentKennung = segmentKennung;
    }

    public Integer getSegmentNummer() {
        return segmentNummer;
    }

    public void setSegmentNummer(Integer segmentNummer) {
        this.segmentNummer = segmentNummer;
    }

    public Integer getSegmentVersion() {
        return segmentVersion;
    }

    public void setSegmentVersion(Integer segmentVersion) {
        this.segmentVersion = segmentVersion;
    }

    public Integer getBezugssegment() {
        return bezugssegment;
    }

    public void setBezugssegment(Integer bezugssegment) {
        this.bezugssegment = bezugssegment;
    }
}
