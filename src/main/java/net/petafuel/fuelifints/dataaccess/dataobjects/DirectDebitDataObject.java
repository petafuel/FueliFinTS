package net.petafuel.fuelifints.dataaccess.dataobjects;

import java.util.Date;

/**
 * Objekt für die Rückmeldung von Lastschrift Beständen
 */
public class DirectDebitDataObject implements IDataObject {

    private String auftragsIdentifikation;   //Genutzt im Einzel- und Sammellastschrift-Bestand

    private Date einreichungsdatum;     //Genutzt im Sammellastschrift-Bestand

    private Date ausfuehrungsDatum;     //Genutzt im Sammellastschrift-Bestand

    private Integer anzahlDerAuftraege;  //Genutzt im Sammellastschrift-Bestand

    private Double summeDerBetraege;   //Genutzt im Sammellastschrift-Bestand

    private String sepaPainMessage;  //Genutzt im Einzellastschrift-Bestand

    private String sepaDescriptor;    //Genutzt im Einzellastschrift-Bestand

    public String getAuftragsIdentifikation() {
        return auftragsIdentifikation;
    }

    public void setAuftragsIdentifikation(String auftragsIdentifikation) {
        this.auftragsIdentifikation = auftragsIdentifikation;
    }

    public Date getEinreichungsdatum() {
        return einreichungsdatum;
    }

    public void setEinreichungsdatum(Date einreichungsdatum) {
        this.einreichungsdatum = einreichungsdatum;
    }

    public Date getAusfuehrungsDatum() {
        return ausfuehrungsDatum;
    }

    public void setAusfuehrungsDatum(Date ausfuehrungsDatum) {
        this.ausfuehrungsDatum = ausfuehrungsDatum;
    }

    public Integer getAnzahlDerAuftraege() {
        return anzahlDerAuftraege;
    }

    public void setAnzahlDerAuftraege(Integer anzahlDerAuftraege) {
        this.anzahlDerAuftraege = anzahlDerAuftraege;
    }

    public Double getSummeDerBetraege() {
        return summeDerBetraege;
    }

    public void setSummeDerBetraege(Double summeDerBetraege) {
        this.summeDerBetraege = summeDerBetraege;
    }

    public String getSepaPainMessage() {
        return sepaPainMessage;
    }

    public void setSepaPainMessage(String sepaPainMessage) {
        this.sepaPainMessage = sepaPainMessage;
    }

    public String getSepaDescriptor() {
        return sepaDescriptor;
    }

    public void setSepaDescriptor(String sepaDescriptor) {
        this.sepaDescriptor = sepaDescriptor;
    }
}
