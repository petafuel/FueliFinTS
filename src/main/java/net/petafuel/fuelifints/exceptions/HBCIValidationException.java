package net.petafuel.fuelifints.exceptions;

import net.petafuel.fuelifints.protocol.fints3.segments.HIRMS;

public class HBCIValidationException extends Exception {

    private HIRMS rueckmeldung;
    private boolean showDetails = true;

    public HBCIValidationException(String reason) {
        super(reason);
    }

    public HBCIValidationException(String reason, boolean showDetails) {
        super(reason);
        this.showDetails = showDetails;
    }

    public HIRMS getRueckmeldung() {
        return rueckmeldung;
    }

    public void setRueckmeldung(HIRMS rueckmeldung) {
        this.rueckmeldung = rueckmeldung;
    }

    public boolean isShowDetails() {
        return showDetails;
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }
}
