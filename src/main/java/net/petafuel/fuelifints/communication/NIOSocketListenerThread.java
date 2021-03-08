package net.petafuel.fuelifints.communication;

import net.petafuel.fuelifints.FinTSServer;
import net.petafuel.fuelifints.FinTSVersionSwitch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class NIOSocketListenerThread extends Thread {
    private static final Logger LOG = LogManager.getLogger(NIOSocketListenerThread.class);
    private int port;
    private FinTSServer finTSServer;
    private Selector socketSelector;
    ByteBuffer buffer = ByteBuffer.allocate(512);
    CharsetEncoder encoder;
    CharsetDecoder decoder;
    ServerSocketChannel serverSocketChannel;
    private boolean shouldStop = false;

    private ArrayList<NioSocketWorker> runningWorkers = new ArrayList<NioSocketWorker>();

    public NIOSocketListenerThread(int port, FinTSServer server) {
        this.finTSServer = server;
        this.port = port;
        Charset charset = Charset.forName("ISO-8859-1");  //Latin-1
        encoder = charset.newEncoder();
        decoder = charset.newDecoder();
    }

    public void stopListening() {
        if (socketSelector != null) {
            try {
                shouldStop = true;
                serverSocketChannel.close();
                socketSelector.wakeup();
            } catch (IOException e) {
                LOG.error("I/O error while shutting down ioReactor (stopListening)", e);
            }
        }
    }

    public void run() {
        try {
            shouldStop = false;

            socketSelector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(finTSServer.getServerAdress(), this.port));
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

            LOG.info("NIO SocketServer listening on port {}", port);

            while (!shouldStop) {
                //Check if Worker is ready:
                for (NioSocketWorker worker : runningWorkers) {
                    if (worker.isReady) {
                        worker.nioSocketClientChannel.keyFor(socketSelector).interestOps(SelectionKey.OP_WRITE);
                    }
                }

                //waiting for events from the selector
                socketSelector.select();
                Set keys = socketSelector.selectedKeys();

                for (Iterator i = keys.iterator(); i.hasNext(); ) {
                    SelectionKey key = (SelectionKey) i.next();
                    i.remove();

                    if (!key.isValid())
                        continue;

                    //isAcceptable?
                    if (key.isAcceptable()) {
                        //get Client socket Channel:
                        SocketChannel client = serverSocketChannel.accept();
                        client.configureBlocking(false);
                        //read to the selector
                        client.register(socketSelector, SelectionKey.OP_READ);
                        continue;
                    }

                    //isReadable?
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        new NioSocketWorker(client, key);
                        continue;
                    }

                    //isWriteable?
                    if (key.isWritable()) {
                        for (NioSocketWorker worker : runningWorkers) {
                            if (worker.isReady && worker.nioSocketClientChannel.equals(key.channel())) {
                                worker.writeResponse();
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("I/O error: ", e);
        } catch (ClosedSelectorException e) {
            LOG.error("selector closed: ", e);
        }
    }

    private class NioSocketWorker extends FinTSCommunicationHandler {
        private boolean isReady = false;
        private byte[] response;
        private SocketChannel nioSocketClientChannel;


        private NioSocketWorker(SocketChannel client, SelectionKey key) {
            super(CommunicationChannel.SOCKET);

            try {
                //Read Data From ClientSocket:
                buffer.clear();

                // Read byte coming from the client
                int numRead = -1;
                try {
                    numRead = client.read(buffer);
                } catch (Exception e) {
                    // client is no longer active
                    key.cancel();
                    client.close();
                    LOG.error("Exception", e);
                }

                if (numRead == -1) {
                    // client shut down channel cleanly
                    key.channel().close();
                    key.cancel();
                }

                buffer.flip();
                buffer.clear();

                this.nioSocketClientChannel = client;
                finTSServer.addRequest(this);
                if (!runningWorkers.contains(this))
                    runningWorkers.add(this);
                FinTSVersionSwitch.selectController(this.taskId, buffer.array(), CommunicationChannel.SOCKET);
            } catch (CharacterCodingException e) {
                LOG.error("Encoding error", e);
            } catch (IOException e) {
                LOG.error("I/O error", e);
            }
        }

        @Override
        public void sendResponse(byte[] response, boolean isLastMessage) {
            updateTimeStamp();
            LOG.info("Received Response: {}", this.nioSocketClientChannel);
            this.response = response;
            this.isReady = true;
            socketSelector.wakeup();
        }

        private void writeResponse() {
            try {
                nioSocketClientChannel.write(ByteBuffer.wrap(response));
                this.isReady = false;
            } catch (IOException e) {
                LOG.error("I/O error in writeResponse", e);
            }
        }
    }
}
