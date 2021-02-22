package net.petafuel.fuelifints.dataaccess.dataobjects.gateway;

import org.bouncycastle.util.encoders.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.left;

public class sms
{
	public static enum status
	{
		fail,
		okay,
		queued
	}
	private Charset charsetUTF8 = Charset.forName("UTF-8");
	private String configGatewayUrl = "http://yoursmsgateway.url/yousmsgateway.php";
	private String configSystemId = "";
	private String configSubSystemId = null;
	private String configGatewayId = null;
	private String configNotBefore = null;

	public sms(String configSystemId, String configGatewayUrl)
	{
		this.configSystemId = configSystemId;
		this.configGatewayUrl = configGatewayUrl;
	}

	/**
	 * Sendete eine SMS ab
	 * @param handyNummer Handynummer
	 * @param message Nachricht
	 * @param absender Absender  (max 11 Zeichen Text oder Handynummer)
	 * @return gibt zurück, ob die SMS erfolgreich versendet wurde
	 * @throws Exception
	 */
	public status doSendSMS(String handyNummer, String message, String absender) throws Exception
	{
		if (absender != null && absender.isEmpty()) absender = null;
		if (configSystemId.isEmpty())
		{
			throw new Exception("Keine System-ID übergeben");
		}
		return doSendNew(handyNummer, message, absender);
	}

	/**
	 * SMS-Funktion zum versenden Über das SMS-Gateway
	 *
	 * @param handynummer Handynummer
	 * @param message Nachricht
	 * @param absender Absender
	 * @return boolean
	 * @throws Exception
	 */
	private status doSendNew(String handynummer, String message, String absender) throws Exception
	{


		if (absender != null)
		{
			absender = isNumeric(absender) ? formatHandynummer(absender, false) : left(absender,11);
		}
		URL url = new URL(configGatewayUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		connection.setRequestProperty("systemid", configSystemId);
		connection.setRequestProperty("target", formatHandynummer(handynummer, true));
		connection.setRequestProperty("text", new String(Base64.encode(message.getBytes(charsetUTF8))));
		if (absender != null)
		{
			connection.setRequestProperty("sender", new String(Base64.encode(absender.getBytes(charsetUTF8))));
		}
		if (configSubSystemId != null)
		{
			connection.setRequestProperty("subsystemCustomer", configSubSystemId);
		}
		if (configGatewayId != null)
		{
			connection.setRequestProperty("gateway", configGatewayId);
		}
		if (configNotBefore != null)
		{
			connection.setRequestProperty("notbefore", configNotBefore);
		}
		connection.setUseCaches(false);
		connection.connect();
		if (connection.getResponseCode() == 200)
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null)
			{
				sb.append(line).append("\n");
			}
			if(sb.toString().contains("OK"))
			{
				return status.okay;
			}
			else if(sb.toString().contains("QUEUED"))
			{
				return status.queued;
			}
		}
		return status.fail;
	}

	/**
	 * Formatiert die Handynummer richtig
	 *
	 * @param handynummer   Handynummer
	 * @param international soll die Nummer am ende International sein?
	 * @return Handynummer, formatiert
	 */
	private String formatHandynummer(String handynummer, boolean international)
	{
		handynummer = handynummer.replace("+", "00");
		if (!handynummer.startsWith("00"))
		{
			handynummer = "0049" + handynummer.substring(1);
		}
		if (!international)
		{
			handynummer = handynummer.replace("0049", "0");
		}
		return handynummer;
	}

	/**
	 * Setzen der SubsystemID (für Abrechnung innerhalb eines Systems)
	 *
	 * @param configSubSystemId Subsystem-ID oder auch CustomerId
	 */
	public void setConfigSubSystemId(String configSubSystemId)
	{
		this.configSubSystemId = configSubSystemId;
	}

	/**
	 * Bevorzugte Gateway-ID (eigentlich nur zum Testen)
	 *
	 * @param configGatewayId Gateway-ID
	 */
	public void setConfigGatewayId(String configGatewayId)
	{
		this.configGatewayId = configGatewayId;
	}

	/**
	 * Nicht senden vor ...
	 *
	 * @param configNotBefore MindestDatum
	 */
	public void setConfigNotBefore(String configNotBefore)
	{
		this.configNotBefore = configNotBefore;
	}
}
