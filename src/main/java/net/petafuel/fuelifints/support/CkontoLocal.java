package net.petafuel.fuelifints.support;

import net.petafuel.fuelifints.dataaccess.dataobjects.ReturnDataObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CkontoLocal {
    public static final String ckontoStatus = "status";
    public static final String ckontoCode = "code";
    public static final String ckontoIban = "ibn";
    public static final String ckontoBic = "bic";
    public static final String UTF_8 = "UTF-8";
    public static final String returnCode = "9930";
    private static final Logger LOG = LogManager.getLogger(CkontoLocal.class);
    private final static String paramsIbanStatus = "[path] sepa -allinfo ibn=[iban]&bic=[bic]&key=[key]";
    private final static String paramsKtoStatus = "[path] -allinfo knr=[kto]&blz=[blz]&key=[key]";
    private final List<String> codes;
    private String key = "";
    private String path = "";
    private boolean active = false;
    public CkontoLocal(String key, String path, String codes) {
        this.key = key;
        this.path = path;
        this.codes = Arrays.asList(codes.split(","));
        if (!key.equals("") && !path.equals("")) {
            File file = new File(path);
            if (file.exists()) {
                if (file.canExecute()) {
                    this.active = true;
                    LOG.info("Status: Aktiv");
                } else {
                    LOG.error("Status: execute Flag missing: {}", path);
                }
            }
        } else {
            LOG.warn("Status: nicht eingerichtet");
            this.active = false;
        }
    }

    /**
     * Gibt zurück ob Ckonto richtig eingerichtet ist
     * @return true wenn alles okay
     */
    public boolean isActive() {
        return active;
    }

    public ReturnDataObject checkIzv(String kontonummer, String bankleitzahl) {
        kontonummer = kontonummer.replace(" ", "");
        bankleitzahl = bankleitzahl.replace(" ", "");
        try {
            HashMap<String, String> ktoparams = getKontoInfo(kontonummer, bankleitzahl);
            if (checkCode(ktoparams)) {
                return new ReturnDataObject(true, "");
            } else {
                return new ReturnDataObject(false, ktoparams.get(ckontoStatus));
            }
        } catch (URISyntaxException e) {
            LOG.error("Exception", e);
            return null;
        }
    }
    public ReturnDataObject checkSepa(String iban, String bic, Boolean deSonderCheck) {
        bic = bic.toUpperCase().replace(" ", "");
        iban = iban.toUpperCase().replace(" ", "");
        if (deSonderCheck && iban.substring(0, 2).equals("DE")) {
            String blz = StringUtils.mid(iban, 4, 8);
            String kto = StringUtils.mid(iban, 12, 10);
            HashMap<String, String> ktoparams = null;
            try {
                ktoparams = getKontoInfo(kto, blz);
                if (checkCode(ktoparams)) {
                    if (!iban.equals(ktoparams.get(ckontoIban))) {
                        return new ReturnDataObject(false, "IBAN-Pruefsumme stimmt nicht: " + iban + " richtig waere " + ktoparams.get(ckontoIban), returnCode);
                    } else if (!bic.equals(ktoparams.get(ckontoBic))) {
                        String cBic = ktoparams.get(ckontoBic);
                        if (bic.length() == 8 && cBic.length() == 11) {
                            String cBic8 = StringUtils.mid(cBic, 0, 8);
                            if (cBic.endsWith("XXX") && cBic8.equals(bic)) {
                                return new ReturnDataObject(true, "");
                            }
                        }
                        return new ReturnDataObject(false, "BIC stimmt nicht: " + bic + " richtig waere " + ktoparams.get(ckontoBic), returnCode);
                    } else {
                        return new ReturnDataObject(true, "");
                    }
                } else return new ReturnDataObject(false, ktoparams.get(ckontoStatus), returnCode);
            } catch (URISyntaxException e) {
                LOG.error("Exception", e);
                return null;
            }
        } else {
            HashMap<String, String> ibanparams = null;
            try {
                ibanparams = getIbanInfo(iban, bic);
                if (checkCode(ibanparams)) {
                    return new ReturnDataObject(true, "", returnCode);
                } else {
                    return new ReturnDataObject(false, ibanparams.get(ckontoStatus), returnCode);
                }
            } catch (URISyntaxException e) {
                LOG.error("Exception", e);
                return null;
            }
        }
    }
    private boolean checkCode(HashMap<String, String> params) {
        return codes.contains(params.get(ckontoCode));
    }
    private HashMap<String, String> getIbanInfo(String iban, String bic) throws URISyntaxException {
        return parseOutput(paramsIbanStatus.replace("[iban]", iban).replace("[bic]", bic));
    }
    private HashMap<String, String> getKontoInfo(String kontonummer, String bankleitzahl) throws URISyntaxException {
        return parseOutput(paramsKtoStatus.replace("[kto]", kontonummer).replace("[blz]", bankleitzahl));
    }
    private HashMap<String, String> parseOutput(String command) throws URISyntaxException {
        if (path != null && !path.equals("")) {
            command = command.replace("[path]", path);
        }
        if (key != null && !key.equals("")) {
            command = command.replace("[key]", key);
        }
        LOG.debug(command);
        Exec exec = new Exec(command.split(" "));
        String url = exec.run();
        LOG.debug(url);
        HashMap<String, String> paramsMap = new HashMap<>();

        // separate key value pairs
        String[] items = url.split("&");

        for (String item: items) {
            // separate key and value
            String[] pair = item.split("=");

            if(pair.length == 1) {
                paramsMap.put(pair[0], ""); // set empty string as default value
            } else if(pair.length == 2) {
                paramsMap.put(pair[0], pair[1]);
            } else {
                LOG.error("Invaild response from cKonto");
            }
        }

        String code = paramsMap.get(ckontoCode);
        if (code != null) {
            paramsMap.put(ckontoStatus, codeStaties.get(code));
        }
        LOG.info(paramsMap);
        return paramsMap;
    }

    final public static HashMap<String, String> codeStaties = new HashMap<String, String>() {
        {
            put("0", "Die IBAN ist ungültig, sie kann von diesem Institut nicht vergeben werden");
            put("1", "Die IBAN ist gültig, sie kann von diesem Institut vergeben werden");
            put("2", "BIC: Fehler bei der Eingabe");
            put("3", "IBAN: Fehler bei der Eingabe oder Kleinbuchstaben verwendet");
            put("4", "IBAN: Format ungültig");
            put("5", "IBAN / BIC: Genereller Eingabefehler des Übergabeparameters - enthält evtl. Leerzeichen");
            put("6", "Fehler im Format des Übergabeparameters");
            put("7", "IBAN / BIC: Die Bankleitzahl wurde in der Datenbank nicht gefunden (existiert nicht)");
            put("8", "IBAN / BIC: Die von der Bank verwendete Prüfmethode ist im Demonstrations-Modus nicht verfügbar");
            put("9", "IBAN / BIC: Die Kontonummer kann nicht geprüft werden, da die Bank entweder keine Prüfziffern verwendet oder es sich um eine spezielle Kontonummer handelt");
            put("10", "IBAN / BIC: Prüfung der Bankverbindung nicht möglich");
            put("20", "IBAN: IBAN ist ungültig");
            put("21", "BIC: BIC ist ungültig");
            put("22", "IBAN / BIC: IBAN und BIC sind ungültig");
        }
    };
}
