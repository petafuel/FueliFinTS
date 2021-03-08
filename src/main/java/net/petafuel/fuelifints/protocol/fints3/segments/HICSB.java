package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;

public class HICSB extends Segment {
	@Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2)})
    @ApplicantAccount
    private KontoverbindungInternational kontoverbindungInternational;
	@Element(
			description = {@ElementDescription(number = 3)})
	@an
	private String sepaDescriptor;

	@Element(
			description = {@ElementDescription(number = 4)})
	@bin
	private byte[] sepaPainMessage;

	@Element(
				description = {@ElementDescription(number = 5, length = -99, status = ElementDescription.StatusCode.C)})
	private String auftragsIdentifikation;
	@Element(
				description = {@ElementDescription(number = 6, status = ElementDescription.StatusCode.O)})
	private String loeschbar;
	@Element(
				description = {@ElementDescription(number = 7, status = ElementDescription.StatusCode.O)})
	private String aenderbar;

	public HICSB(byte[] message) {
		super(message);
	}

	public Segmentkopf getSegmentkopf() {
		return segmentkopf;
	}

	public void setSegmentkopf(Segmentkopf segmentkopf) {
		this.segmentkopf = segmentkopf;
	}

	public KontoverbindungInternational getKontoverbindungInternational() {
		return kontoverbindungInternational;
	}

	public void setKontoverbindungInternational(KontoverbindungInternational kontoverbindungInternational) {
		this.kontoverbindungInternational = kontoverbindungInternational;
	}

	public String getSepaDescriptor() {
		return sepaDescriptor;
	}

	public void setSepaDescriptor(String sepaDescriptor) {
		this.sepaDescriptor = sepaDescriptor;
	}

	public byte[] getSepaPainMessage() {
		return sepaPainMessage;
	}

	public void setSepaPainMessage(byte[] sepaPainMessage) {
		this.sepaPainMessage = sepaPainMessage;
	}

	public String getAuftragsIdentifikation() {
		return auftragsIdentifikation;
	}

	public void setAuftragsIdentifikation(String auftragsIdentifikation) {
		this.auftragsIdentifikation = auftragsIdentifikation;
	}

	public String getLoeschbar() {
		return loeschbar;
	}

	public void setLoeschbar(String loeschbar) {
		this.loeschbar = loeschbar;
	}

	public String getAenderbar() {
		return aenderbar;
	}

	public void setAenderbar(String aenderbar) {
		this.aenderbar = aenderbar;
	}
}
