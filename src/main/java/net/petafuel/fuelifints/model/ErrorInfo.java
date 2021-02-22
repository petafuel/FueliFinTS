package net.petafuel.fuelifints.model;

import net.petafuel.fuelifints.protocol.fints3.segments.HIRMS;

public class ErrorInfo {

    private boolean errorOccured = false;
    private HIRMS errorSegment;

    public boolean isErrorOccured() {
        return errorOccured;
    }

    public void setErrorOccured(boolean errorOccured) {
        this.errorOccured = errorOccured;
    }

    public HIRMS getErrorSegment() {
        return errorSegment;
    }

    public void setErrorSegment(HIRMS errorSegment) {
        this.errorSegment = errorSegment;
    }
}
