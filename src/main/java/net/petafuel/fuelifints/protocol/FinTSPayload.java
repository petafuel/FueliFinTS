package net.petafuel.fuelifints.protocol;

/**
 * Klasse um die Informationen aus einer Clientnachricht zu speichern und an den entsprechenden FinTSController zur
 * weiteren Auswertung weiter zu reichen
 */
public class FinTSPayload {

    private byte[] payload;
    private int taskId;

    public FinTSPayload(byte[] payload, int taskId) {
        this.payload = payload;
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
