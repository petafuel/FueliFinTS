package net.petafuel.fuelifints.dataaccess.dataobjects;

public class SaldoDataObject {
    private double gebuchterSaldo = 0d;
    private Double vorgemerkterSaldo = null;
    private Double verfuegbarerBetrag = null;
    private Double kreditlinie = null;                //	verfügbarer Betrag - gebuchterSaldo
    private String waehrung = "EUR";                // EUR ist standard für deutsche Konten
	private Double verfuegbarTag=null;
	private boolean isOverdrawAllowed = false;

	public double getGebuchterSaldo() {
        return gebuchterSaldo;
    }

    public void setGebuchterSaldo(double gebuchterSaldo) {
        this.gebuchterSaldo = gebuchterSaldo;
    }

    public Double getVorgemerkterSaldo() {
        return vorgemerkterSaldo;
    }

    public void setVorgemerkterSaldo(Double vorgemerkterSaldo) {
        this.vorgemerkterSaldo = vorgemerkterSaldo;
    }

    public Double getVerfuegbarerBetrag() {
        return verfuegbarerBetrag;
    }

    public void setVerfuegbarerBetrag(Double verfuegbarerBetrag) {
        this.verfuegbarerBetrag = verfuegbarerBetrag;
    }

    public Double getKreditlinie() {
        return kreditlinie;
    }

    public void setKreditlinie(Double kreditlinie) {
        this.kreditlinie = kreditlinie;
    }

    public String getWaehrung() {
        return waehrung;
    }

    public void setWaehrung(String waehrung) {
        this.waehrung = waehrung;
    }
	public void setVerfuegbarTag(Double verfuegbarTag)
	{
		this.verfuegbarTag = verfuegbarTag;
	}

	public Double getVerfuegbarTag()
	{
		return verfuegbarTag;
	}

    public boolean isOverdrawAllowed() {
        return isOverdrawAllowed;
    }

    public void setOverdrawAllowed(boolean overdrawAllowed) {
        isOverdrawAllowed = overdrawAllowed;
    }
}
