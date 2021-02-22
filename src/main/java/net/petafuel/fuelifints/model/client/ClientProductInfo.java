package net.petafuel.fuelifints.model.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.FileReader;

public class ClientProductInfo {

    private static final Logger LOG = LogManager.getLogger(ClientProductInfo.class);

    private String userSystemId;
    private Integer currentClientBPDVersion;
    private Integer currentClientUPDVersion;
    private Integer dialogLanguage;
    private String clientProductName;
    private String clientProductVersion;
    private boolean hksynRequired;

    public void setUserSystemId(String userSystemId) {
        this.userSystemId = userSystemId;
    }

    public String getUserSystemId() {
        return userSystemId;
    }

    public void setCurrentClientBPDVersion(Integer currentClientBPDVersion) {
        this.currentClientBPDVersion = currentClientBPDVersion;
    }

    public Integer getCurrentClientBPDVersion() {
        return currentClientBPDVersion;
    }

    public void setCurrentClientUPDVersion(Integer currentClientUPDVersion) {
        this.currentClientUPDVersion = currentClientUPDVersion;
    }

    public Integer getCurrentClientUPDVersion() {
        return currentClientUPDVersion;
    }

    public void setDialogLanguage(Integer dialogLanguage) {
        this.dialogLanguage = dialogLanguage;
    }

    public Integer getDialogLanguage() {
        return dialogLanguage;
    }

    public void setClientProductName(String clientProductName) {
        this.clientProductName = clientProductName;
    }

    public String getClientProductName() {
        return clientProductName;
    }

    public void setClientProductVersion(String clientProductVersion) {
        this.clientProductVersion = clientProductVersion;
    }

    public String getClientProductVersion() {
        return clientProductVersion;
    }

    public void setHksynRequired() {
        hksynRequired = true;
    }

    public void hksynRequested() {
        hksynRequired = false;
    }

    public boolean hksynRequired() {
        return hksynRequired;
    }

    public boolean validateClientProductInfo() {

        if (this.clientProductName == null || this.clientProductName.equals("")) {
            return false;
        }

        try (BufferedReader csvReader = new BufferedReader(new FileReader(System.getProperty("productinfo.csv.filepath")))) {
            String row;
            int startRow =  Integer.parseInt(System.getProperty("productinfo.csv.startRow", "10"));
            int pointer = 0;
            while ((row = csvReader.readLine()) != null) {

                // Anfang der CSV Datei überspringen
                if (pointer < startRow) {
                    pointer++;
                    continue;
                }

                String[] data = row.split(";");

                // Produktbezeichnung mit dem Eintrag in der Liste vergleichen
                if (this.clientProductName.equals(data[2])) {
                    return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException invalidIndex){
            LOG.error("Fehler beim Parsen der CSV-Datei - unerwartetes Format");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return false;
    }
  
}
