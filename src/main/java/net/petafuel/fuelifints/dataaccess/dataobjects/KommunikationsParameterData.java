package net.petafuel.fuelifints.dataaccess.dataobjects;

import java.util.LinkedList;
import java.util.List;

public class KommunikationsParameterData {
    private String bankId;
    private List<String> kommunikationszugaenge;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public List<String> getKommunikationszugangsAdressen() {
        return kommunikationszugaenge;
    }

    public void setKommunikationszugaenge(List<String> kommunikationszugaenge) {
        this.kommunikationszugaenge = kommunikationszugaenge;
    }

    public void addKommunikationszugang(String zugangsAdresse) {
        if (kommunikationszugaenge == null) {
            kommunikationszugaenge = new LinkedList<>();
        }
        kommunikationszugaenge.add(zugangsAdresse);
    }
}
