package net.petafuel.fuelifints.communication;

import net.petafuel.fuelifints.FinTSServer;
import net.petafuel.fuelifints.FinTSVersionSwitch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BlockingSocketListenerThread extends Thread {

    private static final Logger LOG = LogManager.getLogger(BlockingSocketListenerThread.class);
    private FinTSServer finTSServer;
    private boolean shouldStop = false;
    private ServerSocket socket;

    private int port;

    public BlockingSocketListenerThread(int port, FinTSServer server) {
        this.finTSServer = server;
        this.port = port;

    }

    public void stopListening() {
        try {
            shouldStop = true;
            socket.close();
        } catch (IOException e) {
            LOG.error("I/O error while stopListening", e);
        }
    }

    public void run() {
        try {
            shouldStop = false;
            socket = new ServerSocket(port, 0, finTSServer.getServerAdress());
            LOG.info("SocketServer listening on port {}", socket.getLocalPort());
            while (!shouldStop) {
                Socket clientSocket = socket.accept();
                new SocketWorkerThread(clientSocket).start();
            }
        } catch (IOException e) {
            LOG.error("I/O error in run()", e);
        }
    }

    class SocketWorkerThread extends Thread {

        private final Object monitor = new Object();
        private Socket clientSocket;
        private boolean isLastMessage = false;

        public SocketWorkerThread(Socket clientSocket) {
            super();
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            LOG.info("New socket connection thread");
            SocketRequestHandler handler = new SocketRequestHandler(this);
            finTSServer.addRequest(handler);
            while (!shouldStop && !isLastMessage) {
                handler.handle(this.clientSocket);
                try {
                    if (!isLastMessage) {
                        synchronized (this) {
                            this.wait();
                        }
                    }
                } catch (InterruptedException ex) {
                    LOG.warn("Interrupted Exception", ex);
                }
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                LOG.error("Exception closing clientSocket", e);
            }
        }
    }

    class SocketRequestHandler extends FinTSCommunicationHandler {
        private final Object monitor;
        private Socket clientSocket;
        private BufferedInputStream bis;
        private BufferedOutputStream bos;

        public SocketRequestHandler(Object monitor) {
            super(CommunicationChannel.SOCKET);
            this.monitor = monitor;
        }

        private void handle(Socket clientSocket) {
            LOG.trace("begin handling socket...");
            try {
                this.clientSocket = clientSocket;
                if (bis == null) {
                    bis = new BufferedInputStream(this.clientSocket.getInputStream());
                }
                int c;
                ByteArrayOutputStream inputPuffer = new ByteArrayOutputStream();
                int i = 0;

                int messageSize = 0;

                while ((c = bis.read()) >= 0) {
                    //receivedMessage += (char) c;
                    inputPuffer.write(c);
                    i++;
                    if (messageSize != 0 && i == messageSize)
                        break;

                    // Diese Abbruchbedingung ist HBCI 2 / 3 spezifisch und wird für FinTS 4 nicht mehr funktionieren:
                    if (inputPuffer.size() == 22) { //in den ersten 22 Zeichen einer HBCI Nachricht steht die Nachrichtenlänge
                        try {
                            String responseSize = new String(inputPuffer.toByteArray(), 10, 12, StandardCharsets.ISO_8859_1);
                            messageSize = Integer.parseInt(responseSize);
                        } catch (NumberFormatException nfe) {
                            LOG.error("Exception parsing responseSize as integer", nfe);
                            break;
                        }
                    }
                }
                if (inputPuffer.size() > 0) {
                    FinTSVersionSwitch.selectController(this.taskId, inputPuffer.toByteArray(), CommunicationChannel.SOCKET);
                } else {
                    LOG.info("could not read data");
                }
            } catch (IOException e) {
                LOG.error("I/O error in handle(clientSocket)", e);
            }
        }

        @Override
        public void sendResponse(byte[] response, boolean isLastMessage) {
            updateTimeStamp();
            try {
                if (bos == null) {
                    bos = new BufferedOutputStream(clientSocket.getOutputStream());
                }
                bos.write(response);
                bos.flush();
            } catch (IOException e) {
                LOG.error("I/O error in sendResponse", e);
            }
            synchronized (monitor) {
                ((SocketWorkerThread) monitor).isLastMessage = isLastMessage;
                monitor.notifyAll();
            }
        }
    }
}
