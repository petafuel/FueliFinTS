package net.petafuel.fuelifints.dataaccess.dataobjects;

/**
 * Allgemeine Bankparameter
 *
 * FinTS 3.0: Informationen aus Segment HIBPA
 */
public class CommonBankParameterDataObject implements IDataObject {

    private int bpd_version;

    private String bankleitzahl;

    private String kreditinstitutsbezeichnung;

    private String unterstuetzte_Sprachen;

    private String unterstuetzte_hbci_versionen;

    private Integer maximale_nachrichten_groesse;

    private Integer minimaler_timeout_wert;

    private Integer maximaler_timeout_wert;

    public CommonBankParameterDataObject(int bpd_version, String bankleitzahl, String kreditinstitutsbezeichnung, String unterstuetzte_Sprachen, String unterstuetzte_hbci_versionen) {
        this.bpd_version = bpd_version;
        this.bankleitzahl = bankleitzahl;
        this.kreditinstitutsbezeichnung = kreditinstitutsbezeichnung;
        this.unterstuetzte_Sprachen = unterstuetzte_Sprachen;
        this.unterstuetzte_hbci_versionen = unterstuetzte_hbci_versionen;
    }

    public int getBpd_version() {
        return bpd_version;
    }

    public void setBpd_version(int bpd_version) {
        this.bpd_version = bpd_version;
    }

    public String getKreditinstitutsbezeichnung() {
        return kreditinstitutsbezeichnung;
    }


    public void setKreditinstitutsbezeichnung(String kreditinstitutsbezeichnung) {
        this.kreditinstitutsbezeichnung = kreditinstitutsbezeichnung;
    }

    public String getUnterstuetzte_Sprachen() {
        return unterstuetzte_Sprachen;
    }

    public void setUnterstuetzte_Sprachen(String unterstuetzte_Sprachen) {
        this.unterstuetzte_Sprachen = unterstuetzte_Sprachen;
    }

    public String getUnterstuetzte_hbci_versionen() {
        return unterstuetzte_hbci_versionen;
    }

    public void setUnterstuetzte_hbci_versionen(String unterstuetzte_hbci_versionen) {
        this.unterstuetzte_hbci_versionen = unterstuetzte_hbci_versionen;
    }

    public Integer getMaximale_nachrichten_groesse() {
        return maximale_nachrichten_groesse;
    }

    public void setMaximale_nachrichten_groesse(Integer maximale_nachrichten_groesse) {
        this.maximale_nachrichten_groesse = maximale_nachrichten_groesse;
    }

    public Integer getMinimaler_timeout_wert() {
        return minimaler_timeout_wert;
    }

    public void setMinimaler_timeout_wert(Integer minimaler_timeout_wert) {
        this.minimaler_timeout_wert = minimaler_timeout_wert;
    }

    public Integer getMaximaler_timeout_wert() {
        return maximaler_timeout_wert;
    }

    public void setMaximaler_timeout_wert(Integer maximaler_timeout_wert) {
        this.maximaler_timeout_wert = maximaler_timeout_wert;
    }

    public String getBankleitzahl() {
        return bankleitzahl;
    }

    public void setBankleitzahl(String bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
    }
}
