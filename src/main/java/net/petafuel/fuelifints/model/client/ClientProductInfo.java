package net.petafuel.fuelifints.model.client;

public class ClientProductInfo {
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
}
