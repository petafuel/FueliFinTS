package net.petafuel.fuelifints.dataaccess.dataobjects;

public class ReturnDataObject {
    private boolean success = false;
    private String message;
    private String returnCode;
	private Object additionalData = null;

    public ReturnDataObject(boolean success, String message) {
        this(success, message, null);
    }

    public ReturnDataObject(boolean success, String message, String code) {
        this.success = success;
        this.message = message;
        this.returnCode = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

	public Object getAdditionalData() {
		return additionalData;
	}

	public ReturnDataObject setAdditionalData(Object additionalData) {
		this.additionalData = additionalData;
		return this;
	}
}
