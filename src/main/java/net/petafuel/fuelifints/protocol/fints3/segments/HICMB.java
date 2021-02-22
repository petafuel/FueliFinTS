package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.annotations.ApplicantAccount;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Betrag;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.KontoverbindungInternational;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dat;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

public class HICMB extends Segment{

	@Element(
				description = {@ElementDescription(number = 1)})
	private Segmentkopf segmentkopf;
	@Element(
				description = {@ElementDescription(number = 2)})
	private String auftragsidentifikation;
	@Element(
				description = {@ElementDescription(number = 3)})
	@ApplicantAccount
	private KontoverbindungInternational kontoverbindungInternational;
	@Element(
				description = {@ElementDescription(number = 4)})
	@dat
	private String einreichungsdatum;
	@Element(
				description = {@ElementDescription(number = 5)})
	@dat
	private String ausfuehrungsdatum;
	@Element(
				description = {@ElementDescription(number = 6)})
	@num
	private int anzahlAuftraege;
	@Element(
				description = {@ElementDescription(number = 7)})
	private Betrag summeDerBetrage;

	public HICMB(byte[] message) {
		super(message);
	}

	public void setSegmentkopf(Segmentkopf segmentkopf) {
		this.segmentkopf = segmentkopf;
	}

	public void setKontoverbindungInternational(KontoverbindungInternational kontoverbindungInternational) {
		this.kontoverbindungInternational = kontoverbindungInternational;
	}

	public void setEinreichungsdatum(String einreichungsdatum) {
		this.einreichungsdatum = einreichungsdatum;
	}

	public void setAusfuehrungsdatum(String ausfuehrungsdatum) {
		this.ausfuehrungsdatum = ausfuehrungsdatum;
	}

	public void setAnzahlAuftraege(int anzahlAuftraege) {
		this.anzahlAuftraege = anzahlAuftraege;
	}

	public void setSummeDerBetrage(Betrag summeDerBetrage) {
		this.summeDerBetrage = summeDerBetrage;
	}

	public void setAuftragsidentifikation(String auftragsidentifikation) {
		this.auftragsidentifikation = auftragsidentifikation;
	}
}
