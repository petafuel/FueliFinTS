package net.petafuel.fuelifints.model.client;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TransactionInfo {

    private byte[] auftragsHashwert;
    private Date gueltigkeitsDatum;
    private String auftragsreferenz;
    private String tanProzess;
    private String tanListennummer;
    private String challenge;
    private List<String> parameterChallengeKlasse;
    private boolean tanUsed = false;

    public byte[] getAuftragsHashwert() {
        return auftragsHashwert;
    }

    public void setAuftragsHashwert(byte[] auftragsHashwert) {
        this.auftragsHashwert = auftragsHashwert;
    }

    public Date getGueltigkeitsDatum() {
        return gueltigkeitsDatum;
    }

    public void setGueltigkeitsDatum(Date gueltigkeitsDatum) {
        this.gueltigkeitsDatum = gueltigkeitsDatum;
    }

    public String getAuftragsreferenz() {
        return auftragsreferenz;
    }

    public void setAuftragsreferenz(String auftragsreferenz) {
        this.auftragsreferenz = auftragsreferenz;
    }

    public String getTanProzess() {
        return tanProzess;
    }

    public void setTanProzess(String tanProzess) {
        this.tanProzess = tanProzess;
    }

    public void setTanListennummer(String tanListennummer) {
        this.tanListennummer = tanListennummer;
    }

    public String getTanListennummer() {
        return tanListennummer;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getChallenge() {
        return challenge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionInfo that = (TransactionInfo) o;

        if (!Arrays.equals(auftragsHashwert, that.auftragsHashwert)) return false;
        if (auftragsreferenz != null ? !auftragsreferenz.equals(that.auftragsreferenz) : that.auftragsreferenz != null)
            return false;
        if (gueltigkeitsDatum != null ? !gueltigkeitsDatum.equals(that.gueltigkeitsDatum) : that.gueltigkeitsDatum != null)
            return false;
        if (tanListennummer != null ? !tanListennummer.equals(that.tanListennummer) : that.tanListennummer != null)
            return false;
        if (tanProzess != null ? !tanProzess.equals(that.tanProzess) : that.tanProzess != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gueltigkeitsDatum != null ? gueltigkeitsDatum.hashCode() : 0;
        result = 31 * result + (auftragsreferenz != null ? auftragsreferenz.hashCode() : 0);
        return result;
    }

    public List<String> getParameterChallengeKlasse() {
        return parameterChallengeKlasse;
    }

    public void setParameterChallengeKlasse(List<String> parameterChallengeKlasse) {
        this.parameterChallengeKlasse = parameterChallengeKlasse;
    }

    public boolean isTanUsed() {
        return tanUsed;
    }

    public void setTanUsed(boolean tanUsed) {
        this.tanUsed = tanUsed;
    }
}
