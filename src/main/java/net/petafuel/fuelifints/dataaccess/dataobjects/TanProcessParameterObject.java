package net.petafuel.fuelifints.dataaccess.dataobjects;

/**
 * Enthält Informationen zu einem TanProzess, die unter anderem in HITANS (FinTS 3.0) benutzt werden
 * <p/>
 * Damit der TanProzess funktionieren kann, müssen folgende Parameter gesetzt werden:
 * <p/>
 * * sicherheitsfunktion_kodiert
 * * technischeIdentifikationTanVerfahren
 * * nameDesZweiSchrittVerfahrens
 * * belegungstext
 */
public class TanProcessParameterObject implements IDataObject {


    private String sicherheitsfunktion_kodiert = "";

    private String tanProzess = "1";

    private String technischeIdentifikationTanVerfahren = "";

    private String zkaTanVerfahren = "";

    private String versionZkaTanVerfahren = "";

    private String nameDesZweiSchrittVerfahrens = "";

    private String maximaleLaengeDesEingabewertes = "10";

    /*
     * 1: numerisch
     * 2: alphanumerisch
     */
    private String erlaubtesFormat = "2";

    private String belegungsText = "";

    private String maximaleLaengeDesRueckgabewertes = "256";

    private String anzahlUnterstuetzerTanListen = "1";

    private String mehrfachTanErlaubt = "N";

    /*
     * 1: TAN nicht zeitversetzt / dialogübergreifend erlaubt;
     * 2: TAN zeitversetzt / dialogübergreifend erlaubt;
     * 3: beide Verfahren unterstützt;
     * 4: nicht zutreffend
     *
     * Bei Prozessvariante 1 ist der Parameter immer mit „nicht zutreffend“ zu belegen, da hier generell keine zeitversetzte Verarbeitung möglich ist.
     */
    private String tanZeitUndDialogBezug = "4";

    /*
     * 0: TAN-Listennummer darf nicht angegeben werden
     * 1: TAN-Listennummer kann angegeben werden
     * 2: TAN-Listennummer muss angegeben werden
     */
    private String tanListenNummerErforderlich = "0";

    private String auftragsStornoErlaubt = "N";

    private String smsAbbuchungsKontoErforderlich = "0";

    private String auftraggeberKontoErforderlich = "0";

    private String challengeKlasseErforderlich = "N";

    private String challengeBetragErforderlich = "N";

    /*
     * Über diesen BPD-Parameter erhält die Kundenseite die Information,
     * dass im Datenelement „Challenge“ Formatsteuerzeichen enthalten sein können.
     */
    private String challengeStrukturiert = "N";

    /*
     * 00: Initialisierungsverfahren mit Klartext-PIN ohne TAN
     * 01: Verwendung analog der in [HHD] beschriebenen Schablone 01 – ver- schlüsselte PIN und ohne TAN
     * 02: Verwendung analog der in [HHD] beschriebenen Schablone 02 – reser- viert, bei FinTS derzeit nicht verwendet
     */
    private String initialisierungsModus = "00";

    /*
     * 0: Bezeichnung des TAN-Mediums darf nicht angegeben werden
     * 1: Bezeichnung des TAN-Mediums kann angegeben werden
     * 2: Bezeichnung des TAN-Mediums muss angegeben werden
     */
    private String bezeichnungDesTanMediumsErforderlich = "0";

    private String anzahlUnterstuetzterTanMedien = "1";

    public String getElementVersion1String() {


        StringBuilder sb = new StringBuilder("");
        sb.append(sicherheitsfunktion_kodiert + ":");
        sb.append(tanProzess + ":");
        sb.append(technischeIdentifikationTanVerfahren + ":");
        sb.append(nameDesZweiSchrittVerfahrens + ":");
        sb.append(maximaleLaengeDesEingabewertes + ":");
        sb.append(erlaubtesFormat + ":");
        sb.append(belegungsText + ":");
        sb.append(maximaleLaengeDesRueckgabewertes + ":");
        sb.append(anzahlUnterstuetzerTanListen + ":");
        sb.append(mehrfachTanErlaubt + ":");
        sb.append(tanZeitUndDialogBezug.equals("4") ? "N" : "J");
        return sb.toString();
    }

    public String getElementVersion2String() {
        StringBuilder sb = new StringBuilder("");
        sb.append(sicherheitsfunktion_kodiert + ":");
        sb.append(tanProzess + ":");
        sb.append(technischeIdentifikationTanVerfahren + ":");
        sb.append(nameDesZweiSchrittVerfahrens + ":");
        sb.append(maximaleLaengeDesEingabewertes + ":");
        sb.append(erlaubtesFormat + ":");
        sb.append(belegungsText + ":");
        sb.append(maximaleLaengeDesRueckgabewertes + ":");
        sb.append(anzahlUnterstuetzerTanListen + ":");
        sb.append(mehrfachTanErlaubt + ":");
        sb.append(tanZeitUndDialogBezug + ":");
        sb.append(tanListenNummerErforderlich + ":");
        sb.append(auftragsStornoErlaubt + ":");
        sb.append(challengeKlasseErforderlich + ":");
        sb.append(challengeBetragErforderlich);
        return sb.toString();
    }

    public String getElementVersion3String() {
        StringBuilder sb = new StringBuilder("");
        sb.append(sicherheitsfunktion_kodiert + ":");
        sb.append(tanProzess + ":");
        sb.append(technischeIdentifikationTanVerfahren + ":");
        sb.append(nameDesZweiSchrittVerfahrens + ":");
        sb.append(maximaleLaengeDesEingabewertes + ":");
        sb.append(erlaubtesFormat + ":");
        sb.append(belegungsText + ":");
        sb.append(maximaleLaengeDesRueckgabewertes + ":");
        sb.append(anzahlUnterstuetzerTanListen + ":");
        sb.append(mehrfachTanErlaubt + ":");
        sb.append(tanZeitUndDialogBezug + ":");
        sb.append(tanListenNummerErforderlich + ":");
        sb.append(auftragsStornoErlaubt + ":");
        sb.append(challengeKlasseErforderlich + ":");
        sb.append(challengeBetragErforderlich + ":");
        sb.append(initialisierungsModus + ":");
        sb.append(bezeichnungDesTanMediumsErforderlich + ":");
        sb.append(anzahlUnterstuetzterTanMedien);
        return sb.toString();
    }

    public String getElementVersion4String() {
        StringBuilder sb = new StringBuilder("");
        sb.append(sicherheitsfunktion_kodiert + ":");
        sb.append(tanProzess + ":");
        sb.append(technischeIdentifikationTanVerfahren + ":");
        sb.append(zkaTanVerfahren + ":");
        sb.append(versionZkaTanVerfahren + ":");
        sb.append(nameDesZweiSchrittVerfahrens + ":");
        sb.append(maximaleLaengeDesEingabewertes + ":");
        sb.append(erlaubtesFormat + ":");
        sb.append(belegungsText + ":");
        sb.append(maximaleLaengeDesRueckgabewertes + ":");
        sb.append(anzahlUnterstuetzerTanListen + ":");
        sb.append(mehrfachTanErlaubt + ":");
        sb.append(tanZeitUndDialogBezug + ":");
        sb.append(tanListenNummerErforderlich + ":");
        sb.append(auftragsStornoErlaubt + ":");
        sb.append((smsAbbuchungsKontoErforderlich.equals("0") ? "N" : "J") + ":");
        sb.append(challengeKlasseErforderlich + ":");
        sb.append(challengeBetragErforderlich + ":");
        sb.append(challengeStrukturiert + ":");
        sb.append(initialisierungsModus + ":");
        sb.append(bezeichnungDesTanMediumsErforderlich + ":");
        sb.append(anzahlUnterstuetzterTanMedien);
        return sb.toString();
    }

    public String getElementVersion5String() {
        StringBuilder sb = new StringBuilder("");
        sb.append(sicherheitsfunktion_kodiert + ":");
        sb.append(tanProzess + ":");
        sb.append(technischeIdentifikationTanVerfahren + ":");
        sb.append(zkaTanVerfahren + ":");
        sb.append(versionZkaTanVerfahren + ":");
        sb.append(nameDesZweiSchrittVerfahrens + ":");
        sb.append(maximaleLaengeDesEingabewertes + ":");
        sb.append(erlaubtesFormat + ":");
        sb.append(belegungsText + ":");
        sb.append(maximaleLaengeDesRueckgabewertes + ":");
        sb.append(anzahlUnterstuetzerTanListen + ":");
        sb.append(mehrfachTanErlaubt + ":");
        sb.append(tanZeitUndDialogBezug + ":");
        sb.append(tanListenNummerErforderlich + ":");
        sb.append(auftragsStornoErlaubt + ":");
        sb.append(smsAbbuchungsKontoErforderlich + ":");
        sb.append(auftraggeberKontoErforderlich + ":");
        sb.append(challengeKlasseErforderlich + ":");
        sb.append(challengeStrukturiert + ":");
        sb.append(initialisierungsModus + ":");
        sb.append(bezeichnungDesTanMediumsErforderlich + ":");
        sb.append(anzahlUnterstuetzterTanMedien);
        return sb.toString();
    }

    public String getElementVersion6String() {
        StringBuilder sb = new StringBuilder("");
        sb.append(sicherheitsfunktion_kodiert + ":");
        sb.append(tanProzess + ":");
        sb.append(technischeIdentifikationTanVerfahren + ":");
        sb.append(zkaTanVerfahren + ":");
        sb.append(versionZkaTanVerfahren + ":");
        sb.append(nameDesZweiSchrittVerfahrens + ":");
        sb.append(maximaleLaengeDesEingabewertes + ":");
        sb.append(erlaubtesFormat + ":");
        sb.append(belegungsText + ":");
        sb.append(maximaleLaengeDesRueckgabewertes + ":");
        sb.append(mehrfachTanErlaubt + ":");
        sb.append(tanZeitUndDialogBezug + ":");
        sb.append(auftragsStornoErlaubt + ":");
        sb.append(smsAbbuchungsKontoErforderlich + ":");
        sb.append(auftraggeberKontoErforderlich + ":");
        sb.append(challengeKlasseErforderlich + ":");
        sb.append(challengeStrukturiert + ":");
        sb.append(initialisierungsModus + ":");
        sb.append(bezeichnungDesTanMediumsErforderlich + ":");
        sb.append("N:");
        sb.append(anzahlUnterstuetzterTanMedien);
        return sb.toString();
    }

    public String getSicherheitsfunktion_kodiert() {
        return sicherheitsfunktion_kodiert;
    }

    public void setSicherheitsfunktion_kodiert(String sicherheitsfunktion_kodiert) {
        this.sicherheitsfunktion_kodiert = sicherheitsfunktion_kodiert;
    }

    public String getTanProzess() {
        return tanProzess;
    }

    public void setTanProzess(String tanProzess) {
        this.tanProzess = tanProzess;
    }

    public String getTechnischeIdentifikationTanVerfahren() {
        return technischeIdentifikationTanVerfahren;
    }

    public void setTechnischeIdentifikationTanVerfahren(String technischeIdentifikationTanVerfahren) {
        this.technischeIdentifikationTanVerfahren = technischeIdentifikationTanVerfahren;
    }

    public String getZkaTanVerfahren() {
        return zkaTanVerfahren;
    }

    public void setZkaTanVerfahren(String zkaTanVerfahren) {
        this.zkaTanVerfahren = zkaTanVerfahren;
    }

    public String getVersionZkaTanVerfahren() {
        return versionZkaTanVerfahren;
    }

    public void setVersionZkaTanVerfahren(String versionZkaTanVerfahren) {
        this.versionZkaTanVerfahren = versionZkaTanVerfahren;
    }

    public String getNameDesZweiSchrittVerfahrens() {
        return nameDesZweiSchrittVerfahrens;
    }

    public void setNameDesZweiSchrittVerfahrens(String nameDesZweiSchrittVerfahrens) {
        this.nameDesZweiSchrittVerfahrens = nameDesZweiSchrittVerfahrens;
    }

    public String getMaximaleLaengeDesEingabewertes() {
        return maximaleLaengeDesEingabewertes;
    }

    public void setMaximaleLaengeDesEingabewertes(String maximaleLaengeDesEingabewertes) {
        this.maximaleLaengeDesEingabewertes = maximaleLaengeDesEingabewertes;
    }

    public String getErlaubtesFormat() {
        return erlaubtesFormat;
    }

    public void setErlaubtesFormat(String erlaubtesFormat) {
        this.erlaubtesFormat = erlaubtesFormat;
    }

    public String getBelegungsText() {
        return belegungsText;
    }

    public void setBelegungsText(String belegungsText) {
        this.belegungsText = belegungsText;
    }

    public String getMaximaleLaengeDesRueckgabewertes() {
        return maximaleLaengeDesRueckgabewertes;
    }

    public void setMaximaleLaengeDesRueckgabewertes(String maximaleLaengeDesRueckgabewertes) {
        this.maximaleLaengeDesRueckgabewertes = maximaleLaengeDesRueckgabewertes;
    }

    public String getAnzahlUnterstuetzerTanListen() {
        return anzahlUnterstuetzerTanListen;
    }

    public void setAnzahlUnterstuetzerTanListen(String anzahlUnterstuetzerTanListen) {
        this.anzahlUnterstuetzerTanListen = anzahlUnterstuetzerTanListen;
    }

    public String getMehrfachTanErlaubt() {
        return mehrfachTanErlaubt;
    }

    public void setMehrfachTanErlaubt(String mehrfachTanErlaubt) {
        this.mehrfachTanErlaubt = mehrfachTanErlaubt;
    }

    public String getTanZeitUndDialogBezug() {
        return tanZeitUndDialogBezug;
    }

    public void setTanZeitUndDialogBezug(String tanZeitUndDialogBezug) {
        this.tanZeitUndDialogBezug = tanZeitUndDialogBezug;
    }

    public String getTanListenNummerErforderlich() {
        return tanListenNummerErforderlich;
    }

    public void setTanListenNummerErforderlich(String tanListenNummerErforderlich) {
        this.tanListenNummerErforderlich = tanListenNummerErforderlich;
    }

    public String getAuftragsStornoErlaubt() {
        return auftragsStornoErlaubt;
    }

    public void setAuftragsStornoErlaubt(String auftragsStornoErlaubt) {
        this.auftragsStornoErlaubt = auftragsStornoErlaubt;
    }

    public String getSmsAbbuchungsKontoErforderlich() {
        return smsAbbuchungsKontoErforderlich;
    }

    public void setSmsAbbuchungsKontoErforderlich(String smsAbbuchungsKontoErforderlich) {
        this.smsAbbuchungsKontoErforderlich = smsAbbuchungsKontoErforderlich;
    }

    public String getAuftraggeberKontoErforderlich() {
        return auftraggeberKontoErforderlich;
    }

    public void setAuftraggeberKontoErforderlich(String auftraggeberKontoErforderlich) {
        this.auftraggeberKontoErforderlich = auftraggeberKontoErforderlich;
    }

    public String getChallengeKlasseErforderlich() {
        return challengeKlasseErforderlich;
    }

    public void setChallengeKlasseErforderlich(String challengeKlasseErforderlich) {
        this.challengeKlasseErforderlich = challengeKlasseErforderlich;
    }

    public String getChallengeBetragErforderlich() {
        return challengeBetragErforderlich;
    }

    public void setChallengeBetragErforderlich(String challengeBetragErforderlich) {
        this.challengeBetragErforderlich = challengeBetragErforderlich;
    }

    public String getChallengeStrukturiert() {
        return challengeStrukturiert;
    }

    public void setChallengeStrukturiert(String challengeStrukturiert) {
        this.challengeStrukturiert = challengeStrukturiert;
    }

    public String getInitialisierungsModus() {
        return initialisierungsModus;
    }

    public void setInitialisierungsModus(String initialisierungsModus) {
        this.initialisierungsModus = initialisierungsModus;
    }

    public String getBezeichnungDesTanMediumsErforderlich() {
        return bezeichnungDesTanMediumsErforderlich;
    }

    public void setBezeichnungDesTanMediumsErforderlich(String bezeichnungDesTanMediumsErforderlich) {
        this.bezeichnungDesTanMediumsErforderlich = bezeichnungDesTanMediumsErforderlich;
    }

    public String getAnzahlUnterstuetzterTanMedien() {
        return anzahlUnterstuetzterTanMedien;
    }

    public void setAnzahlUnterstuetzterTanMedien(String anzahlUnterstuetzterTanMedien) {
        this.anzahlUnterstuetzterTanMedien = anzahlUnterstuetzterTanMedien;
    }
}
