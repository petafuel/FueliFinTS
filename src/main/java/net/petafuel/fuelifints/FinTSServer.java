package net.petafuel.fuelifints;

import net.petafuel.fuelifints.communication.*;
import net.petafuel.fuelifints.management.CommunicationManagement;
import net.petafuel.fuelifints.protocol.fints3.FinTS3Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class FinTSServer {
    private static final Logger LOG = LogManager.getLogger(FinTSServer.class);

    private final long timeoutMilliseconds;

    private final ArrayList<FinTSCommunicationHandler> runningRequests = new ArrayList<FinTSCommunicationHandler>();
    private MBeanServer mBeanServer;
    private CommunicationManagement comManagement;

    private NIOSSLRequestListenerThread nioSSL;
    private NIOSocketListenerThread nioSocket;
    private BlockingSSLRequestListenerThread blockingSSL;
    private BlockingSocketListenerThread blockingSocket;

    private InetAddress serverAdress;

    public static void main(String[] args) {
        try {
            LOG.info("* * * Starting FueliFinTS server * * *");

            Thread.setDefaultUncaughtExceptionHandler((t, e) -> LOG.error("UncaughtException in {}", t, e));

            FinTSServer server = new FinTSServer();

            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

        } catch (Exception e) {
            LOG.error("Exception in FueliFinTS", e);
        }
    }

    private void stop() {

        try {
            LOG.info("Stopping FueliFinTS server...");

            //<< TODO: shutdown all open resources ... sockets etc >>

            this.stopListeners();

        } catch (Exception ex) {
            LOG.error("Exception stopping!", ex);
        }

    }

    public FinTSServer() {
        //Diverse Serverparameter können über die Java Console geändert werden, dafür wird die CommunicationManagement Klasse genutzt:
        comManagement = new CommunicationManagement();
        timeoutMilliseconds = Long.parseLong(System.getProperty(CommunicationManagement.DIALOG_TIMEOUT_MILLIS, "10000"));

        IFinTSController controller = FinTS3Controller.getInstance();
        controller.setFinTSServer(this);
        registerMBeans();

        //Es erfolgt der Start der Listener für Socket und HTTPS Verbindungen:
        startListeners(comManagement.isNioMode());
    }

    private void startListeners(boolean nioMode) {
        if (comManagement.getServer_ip() != null
                && !comManagement.getServer_ip().isEmpty()
                && !comManagement.getServer_ip().equals("127.0.0.1"))
            try {
                this.serverAdress = InetAddress.getByName(comManagement.getServer_ip());
            } catch (UnknownHostException e) {
                LOG.error("Unknown Host: {}", comManagement.getServer_ip(), e);
            }

        try {
            this.stopListeners();
            if (nioMode) {
                if (comManagement.getSslPort() != 0) {
                    nioSSL = new NIOSSLRequestListenerThread(comManagement.getSslPort(), this);
                    nioSSL.start();
                }
                if (comManagement.getSocketPort() != 0) {
                    nioSocket = new NIOSocketListenerThread(comManagement.getSocketPort(), this);
                    nioSocket.start();
                }
            } else {
                if (comManagement.getSocketPort() != 0) {
                    blockingSocket = new BlockingSocketListenerThread(comManagement.getSocketPort(), this);
                    blockingSocket.start();
                }
                if (comManagement.getSslPort() != 0) {
                    blockingSSL = new BlockingSSLRequestListenerThread(comManagement.getSslPort(), this);
                    blockingSSL.start();
                }
            }
        } catch (Exception e) {
            LOG.error("Error starting ServerListernerThreads: ", e);
        }
    }

    private void registerMBeans() {
        try {
            mBeanServer = ManagementFactory.getPlatformMBeanServer();

            ObjectName cmName = new ObjectName("net.petafuel.fuelifints.management:type=CommunicationManagement");
            mBeanServer.registerMBean(comManagement, cmName);
            mBeanServer.addNotificationListener(cmName, new CommunicationListener(), new CommunicationFilter(), cmName);
        } catch (Exception ex) {
            LOG.error("Error registering MBeans", ex);
        }
    }

    public InetAddress getServerAdress() {
        return serverAdress;
    }

    /**
     * Beantwortet einen Request
     *
     * @param taskId        Die ID, die diesem Request zugeordnet ist
     * @param responseData  Ein Byte Array, das als Antwort geschickt werden soll
     * @param isLastMessage gibt an, ob es sich bei dieser Nachricht um die letzte Nachricht an den Nutzer handelt (ist entwedet bei HKEND (FinTS 3.0) oder eine Fehlernachricht der Fall)
     */
    public boolean respond(int taskId, byte[] responseData, boolean isLastMessage) {

        LOG.debug("isLastMessage? {}", isLastMessage);

        /*
        try {
            LOG.info("received Response " + new String(responseData, "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            //ISO-8859-1
        }
        */
        FinTSCommunicationHandler currentRequest = null;
        synchronized (runningRequests) {
            for (FinTSCommunicationHandler request : runningRequests) {
                if (request.getTaskId() == taskId) {
                    currentRequest = request;
                    currentRequest.updateTimeStamp();
                    break;
                }
            }
        }
        if (currentRequest != null) {
            currentRequest.sendResponse(responseData, isLastMessage);
            if (isLastMessage) {
                currentRequest.setTimestamp(0);
            }
            cleanRunningRequests();
        } else {
            LOG.error("could not find running request");
        }
        return true;
    }

    class CommunicationFilter implements NotificationFilter {
        public boolean isNotificationEnabled(Notification n) {
            return (n.getType().equals("jmx.attribute.change"));
        }
    }

    public boolean addRequest(FinTSCommunicationHandler req) {
        synchronized (runningRequests) {
            if (!runningRequests.contains(req)) {
                runningRequests.add(req);
                return true;
            }
        }
        return false;
    }

    private void cleanRunningRequests() {
        long currentTimeStamp = new Date().getTime();
        synchronized (runningRequests) {
            Iterator<FinTSCommunicationHandler> iterator = runningRequests.iterator();
            while (iterator.hasNext()) {
                FinTSCommunicationHandler handler = iterator.next();
                if ((currentTimeStamp - handler.getTimestamp()) > timeoutMilliseconds) {
                    LOG.debug("Removing ... {}", handler);
                    iterator.remove();
                }
            }
        }
    }

    private void stopListeners() {
        try {
            if (blockingSSL != null && blockingSSL.isAlive())
                blockingSSL.stopListening();

            if (blockingSocket != null && blockingSocket.isAlive())
                blockingSocket.stopListening();

            if (nioSSL != null && nioSSL.isAlive())
                nioSSL.stopListening();

            if (nioSocket != null && nioSocket.isAlive())
                nioSocket.stopListening();

            while (true) {
                //wait for Threads to finish executing
                if ((blockingSocket != null && blockingSocket.isAlive()) ||
                        blockingSSL != null && blockingSSL.isAlive()) {
                    Thread.sleep(200);
                } else if ((nioSSL != null && nioSSL.isAlive()) ||
                        nioSocket != null && nioSocket.isAlive()) {
                    Thread.sleep(200);
                } else break;
            }

        } catch (Exception e) {
            LOG.error("Error starting ServerListernerThreads!", e);
        }
    }

    class CommunicationListener implements NotificationListener {
        @Override
        public void handleNotification(Notification notification, Object o) {
            String type = notification.getType();
            if (type.equals("jmx.attribute.change")) {
                LOG.trace(notification.getMessage());
                startListeners(comManagement.isNioMode());
            }
        }
    }
}
