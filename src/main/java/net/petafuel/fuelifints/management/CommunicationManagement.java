package net.petafuel.fuelifints.management;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CommunicationManagement extends NotificationBroadcasterSupport implements CommunicationManagementMBean {
    private static final Logger LOG = LogManager.getLogger(CommunicationManagement.class);
    public static final String DIALOG_TIMEOUT_MILLIS = "dialog.timeout.millis";
    private boolean nioMode = false;
    private int socketPort = 3000;
    private int sslPort = 443;
    private String server_ip = "";

    private long sequenceNumber = 1;

    public CommunicationManagement() {
        //Versuche, default Einstellungen aus einer property-Datei zu laden:
        Properties properties = new Properties();
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("config/fuelifints.properties"));
            properties.load(bis);
            bis.close();
            System.getProperties().setProperty("hbci.security.protocols", properties.getProperty("hbci.security.protocols"));
            System.getProperties().setProperty(DIALOG_TIMEOUT_MILLIS, properties.getProperty(DIALOG_TIMEOUT_MILLIS));
            socketPort = Integer.parseInt(properties.getProperty("rdh_port"));
            sslPort = Integer.parseInt(properties.getProperty("ssl_port"));
            server_ip = properties.getProperty("server_ip");
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isNioMode() {
        return nioMode;
    }

    @Override
    public void setNioMode(boolean nioMode) {
        boolean oldmode = this.nioMode;
        this.nioMode = nioMode;

        Notification n = new AttributeChangeNotification(this, sequenceNumber++,
                System.currentTimeMillis(), "Niomode changed", "nioMode", "boolean",
                oldmode, this.nioMode);
        sendNotification(n);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };

        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info =
                new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[]{info};
    }

    @Override
    public int getSocketPort() {
        return socketPort;
    }

    @Override
    public void setSocketPort(int socketPort) {
        int oldPort = this.socketPort;
        this.socketPort = socketPort;

        Notification n = new AttributeChangeNotification(this, sequenceNumber++,
                System.currentTimeMillis(), "Socket Port changed", "socketPort", "int",
                oldPort, this.socketPort);
        sendNotification(n);

    }

    @Override
    public int getSslPort() {
        return sslPort;
    }

    @Override
    public void setSslPort(int sslPort) {
        int oldPort = this.sslPort;
        this.sslPort = sslPort;

        Notification n = new AttributeChangeNotification(this, sequenceNumber++,
                System.currentTimeMillis(), "SSL Port changed", "sslPort", "int",
                oldPort, this.sslPort);
        sendNotification(n);
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }
}
