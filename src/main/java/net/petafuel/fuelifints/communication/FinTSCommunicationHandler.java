package net.petafuel.fuelifints.communication;

import java.util.Date;

public abstract class FinTSCommunicationHandler {


    public enum CommunicationChannel {
        SSL,
        SOCKET
    }

    protected int taskId;
    private long timestamp;
    private CommunicationChannel communicationChannel;

    public FinTSCommunicationHandler(CommunicationChannel channel) {
        this.communicationChannel = channel;
        this.updateTimeStamp();
        this.taskId = (int) (Math.random() * Integer.MAX_VALUE);
    }

    public int getTaskId() {
        return taskId;
    }

    public abstract void sendResponse(byte[] response, boolean isLastMessage);

    public void updateTimeStamp() {
        this.timestamp = new Date().getTime();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
