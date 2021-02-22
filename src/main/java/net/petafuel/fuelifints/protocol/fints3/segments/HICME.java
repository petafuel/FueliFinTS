package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

public class HICME extends Segment {
	@Element(description = {@ElementDescription(number = 1)})
	private Segmentkopf segmentkopf;
	@Element(description = {@ElementDescription(number = 2,status = ElementDescription.StatusCode.O)})
	private String auftragsidentifikation;

	public HICME(byte[] message) {
		super(message);
	}

	public void setAuftragsidentifikation(String auftragsidentifikation) {
		this.auftragsidentifikation = auftragsidentifikation;
	}

	public Segmentkopf getSegmentkopf() {
		return segmentkopf;
	}

	public String getAuftragsidentifikation() {
		return auftragsidentifikation;
	}

	public void setSegmentkopf(Segmentkopf segmentkopf) {
		this.segmentkopf = segmentkopf;
	}
}
