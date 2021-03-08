package net.petafuel.fuelifints.dataaccess.dataobjects;

/**
 * Enthält Daten zu den Konten eines Kunden
 * <p/>
 * FinTS 3.0: Diese Daten werden im Segment HIUPD verwendet
 */
public class AccountDataObject implements IDataObject {

    private String kontonummer;

    private String bankleitzahl;

    private String kunden_id;

    private String kontoart;

    private String kontowaehrung;

    private String name;

    private String produktbzeichnung;

    private String kontolimit;

    private String erlaubteGeschaeftsvorfaelle;

    public AccountDataObject(String kontonummer, String bankleitzahl, String name, String produktbzeichnung) {
        this.kontonummer = kontonummer;
        this.bankleitzahl = bankleitzahl;
        this.name = name;
        this.produktbzeichnung = produktbzeichnung;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public String getBankleitzahl() {
        return bankleitzahl;
    }

    public void setBankleitzahl(String bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
    }

    public String getKunden_id() {
        return kunden_id;
    }

    public void setKunden_id(String kunden_id) {
        this.kunden_id = kunden_id;
    }

    public String getKontoart() {
        return kontoart;
    }

    public void setKontoart(String kontoart) {
        this.kontoart = kontoart;
    }

    public String getKontowaehrung() {
        return kontowaehrung;
    }

    public void setKontowaehrung(String kontowaehrung) {
        this.kontowaehrung = kontowaehrung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduktbzeichnung() {
        return produktbzeichnung;
    }

    public void setProduktbzeichnung(String produktbzeichnung) {
        this.produktbzeichnung = produktbzeichnung;
    }

    public String getKontolimit() {
        return kontolimit;
    }

    public void setKontolimit(String kontolimit) {
        this.kontolimit = kontolimit;
    }

    public String getErlaubteGeschaeftsvorfaelle() {
        return erlaubteGeschaeftsvorfaelle;
    }

    public void setErlaubteGeschaeftsvorfaelle(String erlaubteGeschaeftsvorfaelle) {
        this.erlaubteGeschaeftsvorfaelle = erlaubteGeschaeftsvorfaelle;
    }

    @Override
    public String toString() {
        return "AccountDataObject{" +
                "kontonummer='" + kontonummer + '\'' +
                ", bankleitzahl='" + bankleitzahl + '\'' +
                ", kunden_id='" + kunden_id + '\'' +
                ", kontoart='" + kontoart + '\'' +
                ", kontowaehrung='" + kontowaehrung + '\'' +
                ", name='" + name + '\'' +
                ", produktbzeichnung='" + produktbzeichnung + '\'' +
                ", kontolimit='" + kontolimit + '\'' +
                '}';
    }
}
