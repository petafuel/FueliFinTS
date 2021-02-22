package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

public class HICSE extends Segment {
	@Element(description = {@ElementDescription(number = 1)})
	private Segmentkopf segmentkopf;
	@Element(description = {@ElementDescription(number = 2,status = ElementDescription.StatusCode.O)})
	private String auftragsidentifikation;
	public HICSE(byte[] message) {
		super(message);
	}

	public Segmentkopf getSegmentkopf() {
		return segmentkopf;
	}

	public void setSegmentkopf(Segmentkopf segmentkopf) {
		this.segmentkopf = segmentkopf;
	}

	public String getAuftragsidentifikation() {
		return auftragsidentifikation;
	}

	public void setAuftragsidentifikation(String auftragsidentifikation) {
		this.auftragsidentifikation = auftragsidentifikation;
	}
}
