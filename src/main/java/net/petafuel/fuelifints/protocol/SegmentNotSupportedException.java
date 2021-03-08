package net.petafuel.fuelifints.protocol;

public class SegmentNotSupportedException extends Exception {

    public SegmentNotSupportedException(String segmentName) {
        super(segmentName + " not supported");
    }

}
