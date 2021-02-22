package net.petafuel.fuelifints.dataaccess.dataobjects;

import net.petafuel.fuelifints.FinTSVersionSwitch;

import java.util.*;

public class PermissionDataObject
{
	public static final String verfahren_iTan = "912";
	public static final String verfahren_mTan = "913";
	public static final String verfahren_iTanSms = "914";
	private static List<GlobalParameter> bankParameters = null;
	/**
	 * Liste aller Geschäftsvorfälle (auch nicht aktive)
	 */
	private static HashMap<FINTS30, String> geschaeftsvorfaelle = new HashMap<FINTS30, String>()
	{{
			put(FINTS30.SaldoAbfrage, "HKSAL");
			put(FINTS30.UmsatzAbfrage, "HKKAZ");
			put(FINTS30.PinTanVerfahren, "HKTAN");
			put(FINTS30.PinAendern, "HKPAE");
			put(FINTS30.SepaKontoVerbindungen, "HKSPA");
			put(FINTS30.SepaUeberweisungEinzelEinreichen, "HKCCS");
			put(FINTS30.SepaUeberweisungSammelEinreichen, "HKCCM");
			put(FINTS30.PinTanZweiSchrittParameter, "HKTAB");
			put(FINTS30.SepaLastschriftEinzelEinreichenCore1, "HKDSC");
			put(FINTS30.SepaLastschriftEinzelEinreichenB2B,"HKBSE");
			put(FINTS30.SepaLastschriftEinzelEinreichen, "HKDSE");
			put(FINTS30.SepaLastschriftEinzelAendern, "HKDSL");
			put(FINTS30.SepaLastschriftEinzelLoeschen, "HKDSA");
			put(FINTS30.SepaLastschriftEinzelLoeschenB2B,"HKBSL");
			put(FINTS30.SepaLastschriftEinzelBestand, "HKDBS");
			put(FINTS30.SepaLastschriftEinzelBestandB2B,"HKBBS");
			put(FINTS30.SepaLastschriftSammelEinreichenCore1,"HKDMC");
			put(FINTS30.SepaLastschriftSammelEinreichenB2B, "HKBME");
			put(FINTS30.SepaLastschriftSammelEinreichen, "HKDME");
			put(FINTS30.SepaLastschriftSammelBestand, "HKDMB");
			put(FINTS30.SepaLastschriftSammelBestandB2B, "HKBMB");
			put(FINTS30.SepaLastschriftSammelLoeschen, "HKDML");
			put(FINTS30.SepaLastschriftSammelLoeschenB2B, "HKBML");
			put(FINTS30.KundenBankNachricht,"HKKDM");
			put(FINTS30.Vormerkumsaetze,"HKVMK");
            put(FINTS30.SepaUeberweisungEinzelTerminEinreichen,"HKCSE");
			put(FINTS30.SepaUeberweisungEinzelTerminBestand,"HKCSB");
			put(FINTS30.SepaUeberweisungEinzelTerminLoeschen,"HKCSL");
            put(FINTS30.SepaUeberweisungSammelTerminEinreichen,"HKCME");
			put(FINTS30.SepaUeberweisungSammelTerminBestand,"HKCMB");
			put(FINTS30.SepaUeberweisungSammelTerminLoeschen, "HKMSL");
			put(FINTS30.Identifikation, "HKIDN");
		}};

	private static HashMap<FINTS30, Boolean> pinRequired = null;
	private String permissions = "";

	/**
	 * Liste der aktiven Geschäftsvorfälle
	 * @return FINTS30-Liste
	 */
	public static List<FINTS30> getAllowedFINTS30()
	{
		return Arrays.asList(
				FINTS30.SaldoAbfrage,
				FINTS30.UmsatzAbfrage,
				FINTS30.PinTanVerfahren,
				FINTS30.PinTanZweiSchrittParameter,
				FINTS30.SepaKontoVerbindungen,
				FINTS30.SepaUeberweisungEinzelEinreichen,
				FINTS30.SepaUeberweisungEinzelTerminEinreichen,
				FINTS30.SepaUeberweisungEinzelTerminBestand,
				FINTS30.SepaUeberweisungEinzelTerminLoeschen,
				FINTS30.SepaUeberweisungSammelEinreichen,
				FINTS30.SepaUeberweisungSammelTerminEinreichen,
				FINTS30.SepaUeberweisungSammelTerminBestand,
				FINTS30.SepaUeberweisungSammelTerminLoeschen,
				FINTS30.SepaLastschriftEinzelEinreichen,
				FINTS30.SepaLastschriftEinzelEinreichenCore1,
				FINTS30.SepaLastschriftEinzelEinreichenB2B,
				FINTS30.SepaLastschriftEinzelLoeschen,
				FINTS30.SepaLastschriftEinzelLoeschenB2B,
				FINTS30.SepaLastschriftEinzelAendern,
				FINTS30.SepaLastschriftEinzelBestand,
				FINTS30.SepaLastschriftEinzelBestandB2B,
				FINTS30.SepaLastschriftSammelEinreichen,
				FINTS30.SepaLastschriftSammelEinreichenCore1,
				FINTS30.SepaLastschriftSammelEinreichenB2B,
				FINTS30.SepaLastschriftSammelLoeschen,
				FINTS30.SepaLastschriftSammelLoeschenB2B,
				FINTS30.SepaLastschriftSammelBestand,
				FINTS30.SepaLastschriftSammelBestandB2B,
				FINTS30.PinAendern,
				FINTS30.KundenBankNachricht,
				FINTS30.Vormerkumsaetze
		);
	}

	/**
	 * Liste mit HBCI-ParameterObjekten
	 * @return Liste
	 */
	public static List<GlobalParameter> getBankParameters()
	{
		if (bankParameters == null)
		{
			TanProcessParameterObject iTAN = getiTanParameter();
			TanProcessParameterObject mTAN = getmTanParameter();
			TanProcessParameterObject iTANsms = getiTanSmsParameter();
			bankParameters = new ArrayList<>();
			bankParameters.add(new GlobalParameter("HICCMS", 1, 10, "1", "1", "1000:J:J", ""));    //	Sammel Einreichung
			bankParameters.add(new GlobalParameter("HICCSS", 1, 1, "1", "2", "", ""));           //	Einzel Einreichung
			//2		SEPA-Einzel-Lastschriften
			bankParameters.add(new GlobalParameter("HIDSES", 1, 1, "1", "1", "1:14:1:14", ""));  //	Einreichen
			bankParameters.add(new GlobalParameter("HIDSCS", 1, 1, "1", "1", "1:14:1:14", ""));  //	COR1 Einreichen
			bankParameters.add(new GlobalParameter("HIDBSS", 1, 1, "1", "1", "J:N", ""));        //	Bestand
			bankParameters.add(new GlobalParameter("HIDBSS", 1, 1, "1", "1", "J:N", ""));        //	Änderung
			bankParameters.add(new GlobalParameter("HIDSAS", 1, 1, "1", "1", "1:14:1:14", ""));  //	Löschen
			bankParameters.add(new GlobalParameter("HIDSLS", 1, 1, "1", "1", "J", ""));
			//7		SEPA-Sammel-Lastschriften
			bankParameters.add(new GlobalParameter("HIDMES", 1, 1, "1", "1", "1:14:1:14:1000:N:J", ""));//	Einreichung
			bankParameters.add(new GlobalParameter("HIDMCS", 1, 1, "1", "1", "100:N:N:1:14:1:14", ""));//	COR1 Einreichen
			bankParameters.add(new GlobalParameter("HIDMBS", 1, 1, "1", "1", "N:N", ""));        //	Bestand
			bankParameters.add(new GlobalParameter("HIDMLS", 1, 1, "1", "1", "", ""));            //	Löschung
			//11	Umsätze abrufen
			bankParameters.add(new GlobalParameter("HIKAZS", 6, 1, "1", "1", "90:J:J", ""));
			bankParameters.add(new GlobalParameter("HIKAZS", 7, 1, "1", "1", "90:J:J", ""));
			bankParameters.add(new GlobalParameter("HIKAZS", 5, 1, "1", "", "90:J:N", ""));
			//14	Saldo abrufen
			bankParameters.add(new GlobalParameter("HISALS", 5, 1, "1", "", "", ""));
			bankParameters.add(new GlobalParameter("HISALS", 6, 1, "1", "1", "", ""));
			bankParameters.add(new GlobalParameter("HISALS", 7, 1, "1", "1", "", ""));
			//17	SEPA-Parameter
			bankParameters.add(new GlobalParameter("HISPAS", 1, 1, "1", "1", "J:N:N:sepade.pain.001.001.02.xsd:sepade.pain.001.002.02.xsd:sepade.pain.001.001.03.xsd:sepade.pain.001.002.03.xsd:sepade.pain.008.001.02.xsd:sepade.pain.008.002.02.xsd:sepade.pain.008.003.02.xsd", ""));
			bankParameters.add(new GlobalParameter("HISPAS", 2, 1, "1", "1", "J:N:N:N:sepade.pain.001.001.02.xsd:sepade.pain.001.002.02.xsd:sepade.pain.001.001.03.xsd:sepade.pain.001.002.03.xsd:sepade.pain.008.001.02.xsd:sepade.pain.008.002.02.xsd:sepade.pain.008.003.02.xsd", ""));
			//19	Überweisung IZV
			bankParameters.add(new GlobalParameter("HIUEBS", 5, 1, "1", "2", "14:51:53:54:67:69", ""));
			bankParameters.add(new GlobalParameter("HITANS", 6, 1, "1", "1", "J:N:1:" + mTAN.getElementVersion6String(), ""));
			bankParameters.add(new GlobalParameter("HIPAES", 1, 1, "1", "1", "", ""));//PIN ändern
			bankParameters.add(new GlobalParameter("HIKOMS", 4, 1, "1", "1", "", ""));//Kommunikationszugänge
			bankParameters.add(new GlobalParameter("HIKDMS", 5, 1, "1", "1", "2048", ""));
			bankParameters.add(new GlobalParameter("HIVMKS", 1, 1, "1", "1", "N:N", ""));
            bankParameters.add(new GlobalParameter("HICSES", 1, 1, "1", "2", "1:14", ""));
            bankParameters.add(new GlobalParameter("HICMES", 1, 10, "1", "2", "1:14:0:N:J", ""));
			bankParameters.add(new GlobalParameter("HICSBS", 1, 1, "1", "1", "N:N", ""));
			bankParameters.add(new GlobalParameter("HICMBS", 1, 1, "1", "1", "N:N", ""));
			bankParameters.add(new GlobalParameter("HICSLS", 1, 1, "1", "2", "N" ,""));
			bankParameters.add(new GlobalParameter("HICMLS", 1, 1, "1", "2", "", ""));
			bankParameters.add(new GlobalParameter("HIBBSS", 1, 1, "1", "1", "J:N", ""));	//Bestand Firmeneinzellastschrift
			bankParameters.add(new GlobalParameter("HIBMES", 1, 1, "1", "1", "1:14:1:14:1000:N:J", ""));	//Einreichung Firmensammellastschrift
			bankParameters.add(new GlobalParameter("HIBMLS", 1, 1, "1", "1", "", ""));		//Firmensammellastschrift löschen
			bankParameters.add(new GlobalParameter("HIBSES", 1, 1, "1", "2", "1:14:1:14", "")); 	//Einreichung Firmeneinzellastschrift
			bankParameters.add(new GlobalParameter("HIBSLS", 1, 1, "1", "1", "J", ""));		//Firmeneinzellastschrift löschen
		}
		return bankParameters;
	}

    public static String getHBCIversion(FinTSVersionSwitch.FinTSVersion fintsVersion)
	{
		String hbciversion;
		switch (fintsVersion)
		{
			case HBCI_VERSION_2_2:
				hbciversion = "220";
				break;
			default:
			case FINTS_VERSION_3_0:
				hbciversion = "300";
				break;
			case FINTS_VERSION_4_0:
				hbciversion = "400";
				break;
		}
		return hbciversion;
	}

	/**
	 * HKXXX zu FINTS30 Geschäftsvorfall
	 * @param type FINTS30
	 * @return String "HKXXX"
	 */
	public static String getGeschaeftsvorfall(FINTS30 type)
	{
		if (geschaeftsvorfaelle.containsKey(type))
		{
			return geschaeftsvorfaelle.get(type);
		}
		return null;
	}

	/**
	 * FINTS30 Geschäftsvorfall zum String
	 * @param value "HKXXX"
	 * @return FINTS30 Geschäftsvorfall
	 */
	public static FINTS30 getGeschaeftsvorfall(String value)
	{
		for (Map.Entry<FINTS30, String> entry : geschaeftsvorfaelle.entrySet())
		{
			if (value.equals(entry.getValue()))
			{
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Liste "aller" FINTS30-Geschäftsvorfälle und ob für diese eine PIN-Eingabe benötigt wird
	 * @return Liste mit true/false-Values
	 */
	public static HashMap<FINTS30, Boolean> getPinRequired()
	{
		if (pinRequired == null)
		{
			pinRequired = new HashMap<>();
			pinRequired.put(FINTS30.SaldoAbfrage, false);
			pinRequired.put(FINTS30.UmsatzAbfrage, false);
			pinRequired.put(FINTS30.PinTanVerfahren, false);
			pinRequired.put(FINTS30.PinAendern, true);
			pinRequired.put(FINTS30.SepaKontoVerbindungen, false);
			pinRequired.put(FINTS30.SepaUeberweisungEinzelEinreichen, true);
            pinRequired.put(FINTS30.SepaUeberweisungEinzelTerminEinreichen,true);
			pinRequired.put(FINTS30.SepaUeberweisungEinzelTerminBestand,false);
			pinRequired.put(FINTS30.SepaUeberweisungEinzelTerminLoeschen,true);
			pinRequired.put(FINTS30.SepaUeberweisungSammelEinreichen, true);
			pinRequired.put(FINTS30.SepaUeberweisungSammelTerminEinreichen,true);
			pinRequired.put(FINTS30.SepaUeberweisungSammelTerminBestand,false);
			pinRequired.put(FINTS30.SepaUeberweisungSammelTerminLoeschen,true);
			pinRequired.put(FINTS30.SepaLastschriftEinzelAendern, true);
			pinRequired.put(FINTS30.SepaLastschriftEinzelEinreichen, true);
			pinRequired.put(FINTS30.SepaLastschriftEinzelEinreichenCore1, true);
			pinRequired.put(FINTS30.SepaLastschriftEinzelLoeschen, true);
			pinRequired.put(FINTS30.SepaLastschriftEinzelBestand, false);
			pinRequired.put(FINTS30.SepaLastschriftSammelEinreichen, true);
			pinRequired.put(FINTS30.SepaLastschriftSammelEinreichenCore1,true);
			pinRequired.put(FINTS30.SepaLastschriftSammelLoeschen, true);
			pinRequired.put(FINTS30.SepaLastschriftSammelBestand, false);
			pinRequired.put(FINTS30.KundenBankNachricht,false);
			pinRequired.put(FINTS30.Vormerkumsaetze,false);
			pinRequired.put(FINTS30.Identifikation, true);
		}
		return pinRequired;
	}

	/**
	 * Generiert PIN-Parameter-Objekt für iTAN
	 * @return Object
	 */
	private static TanProcessParameterObject getiTanParameter()
	{
		TanProcessParameterObject iTanParameter = new TanProcessParameterObject();
		iTanParameter.setNameDesZweiSchrittVerfahrens("iTAN-Liste");
		iTanParameter.setBelegungsText("Erhaltene TAN");
		iTanParameter.setSicherheitsfunktion_kodiert(verfahren_iTan);
		iTanParameter.setTechnischeIdentifikationTanVerfahren("iTAN");
		return iTanParameter;
	}

	/**
	 * Generiert PIN-Parameter-Objekt für mTAN
	 * @return Object
	 */
	private static TanProcessParameterObject getmTanParameter()
	{
		TanProcessParameterObject mTanParameter = new TanProcessParameterObject();
		mTanParameter.setNameDesZweiSchrittVerfahrens("Mobile TAN");
		mTanParameter.setBelegungsText("Erhaltene TAN");
		mTanParameter.setSicherheitsfunktion_kodiert(verfahren_mTan);
		mTanParameter.setTechnischeIdentifikationTanVerfahren("mTAN");
		return mTanParameter;
	}

	/**
	 * Generiert PIN-Parameter-Objekt für iTAN per SMS
	 * @return Object
	 */
	private static TanProcessParameterObject getiTanSmsParameter()
	{
		TanProcessParameterObject iTanSmsParameter = new TanProcessParameterObject();
		iTanSmsParameter.setNameDesZweiSchrittVerfahrens("iTAN-Liste-Mobil");
		iTanSmsParameter.setBelegungsText("Erhaltene TAN");
		iTanSmsParameter.setSicherheitsfunktion_kodiert(verfahren_iTanSms);
		iTanSmsParameter.setTechnischeIdentifikationTanVerfahren("iTANm");
		return iTanSmsParameter;
	}

	/**
	 * Für die Generierung des Strings der Geschäftsvofälle pro konto
	 * @param type FINTS30, es sollten nur erlaubte hier auftrauchen
	 */
	public void addPermission(FINTS30 type)
	{
		String gvf = getGeschaeftsvorfall(type);
		if (!permissions.equals(""))
		{
			permissions += "+";
		}
		permissions += gvf + ":1";
	}

	/**
	 * Gibt den Geschäftsvorfallstring zurück
	 * @return String "HKXXX:1+HKXXX:1+..."
	 */
	public String toString()
	{
		return permissions;
	}

	/**
	 * Liste aller Geschäftsvorfälle
	 */
	public enum FINTS30
	{
		SaldoAbfrage,
		UmsatzAbfrage,
		PinTanVerfahren,
		SepaKontoVerbindungen,
		SepaUeberweisungEinzelEinreichen,
		SepaUeberweisungEinzelTerminEinreichen,
		SepaUeberweisungEinzelTerminBestand,
		SepaUeberweisungEinzelTerminLoeschen,
		SepaUeberweisungSammelEinreichen,
		SepaUeberweisungSammelTerminEinreichen,
		SepaUeberweisungSammelTerminBestand,
		SepaUeberweisungSammelTerminLoeschen,
		SepaLastschriftEinzelEinreichen,
		SepaLastschriftEinzelLoeschen,
		SepaLastschriftEinzelAendern,
		SepaLastschriftEinzelEinreichenCore1,
		SepaLastschriftEinzelBestand,
		SepaLastschriftSammelEinreichenCore1,
		SepaLastschriftSammelEinreichen,
		SepaLastschriftSammelLoeschen,
		SepaLastschriftSammelBestand,
		PinTanZweiSchrittParameter,
		PinAendern,
		KundenBankNachricht,
		Vormerkumsaetze,
        SepaLastschriftEinzelLoeschenB2B,
        SepaLastschriftEinzelEinreichenB2B,
        SepaLastschriftEinzelBestandB2B,
        SepaLastschriftSammelEinreichenB2B,
        SepaLastschriftSammelBestandB2B,
        SepaLastschriftSammelLoeschenB2B,
        Identifikation
	}

	/**
	 * Generelle Bankparameter
	 */
	public static class GlobalParameter
	{
		private final String segmentName;
		private final Integer segmentVersion;
		private final Integer maximaleAnzahlAuftraege;
		private final String anzahlSingaturenMindestens;
		private final String sicherheitsKlasse;
		private final String fints_3_parameter;
		private final String fints_4_parameter;

		GlobalParameter(String segmentName,
						Integer segmentVersion, Integer maximaleAnzahlAuftraege, String anzahlSignaturenMindestens,
						String sicherheitsKlasse, String fints_3_parameter, String fints_4_parameter)
		{
			this.segmentName = segmentName;
			this.segmentVersion = segmentVersion;
			this.maximaleAnzahlAuftraege = maximaleAnzahlAuftraege;
			this.anzahlSingaturenMindestens = anzahlSignaturenMindestens;
			this.sicherheitsKlasse = sicherheitsKlasse;
			this.fints_3_parameter = fints_3_parameter;
			this.fints_4_parameter = fints_4_parameter;

		}

		public String getFints_4_parameter()
		{
			return fints_4_parameter;
		}

		public String getFints_3_parameter()
		{
			return fints_3_parameter;
		}

		public String getSicherheitsKlasse()
		{
			return sicherheitsKlasse;
		}

		public String getAnzahlSingaturenMindestens()
		{
			return anzahlSingaturenMindestens;
		}

		public Integer getMaximaleAnzahlAuftraege()
		{
			return maximaleAnzahlAuftraege;
		}

		public Integer getSegmentVersion()
		{
			return segmentVersion;
		}

		public String getSegmentName()
		{
			return segmentName;
		}
	}
}
