package net.petafuel.fuelifints.dataaccess.dataobjects;

/**
 * Enhält Daten für Empfängerkonten. (SEPA-Übertrag)
 */
public class RecipientAccountDataObject {
    private String IBAN;
    private String BIC;
    private String name;

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
