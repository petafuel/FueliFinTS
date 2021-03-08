package net.petafuel.fuelifints.management;

public interface CommunicationManagementMBean {
    boolean isNioMode();
    void setNioMode(boolean nioMode);

    int getSocketPort();
    void setSocketPort(int socketPort);

    int getSslPort();
    void setSslPort(int sslPort);
}
