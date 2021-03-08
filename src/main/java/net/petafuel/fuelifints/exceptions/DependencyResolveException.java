package net.petafuel.fuelifints.exceptions;

public class DependencyResolveException extends Exception {

    private String errorCode = "9000";

    public DependencyResolveException() {
        super();
    }

    public DependencyResolveException(String message) {
        super(message);
    }

    public DependencyResolveException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
