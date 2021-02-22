package net.petafuel.fuelifints.communication;

import net.petafuel.fuelifints.FinTSServer;
import net.petafuel.fuelifints.FinTSVersionSwitch;
import org.apache.http.*;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.nio.DefaultHttpServerIODispatch;
import org.apache.http.impl.nio.DefaultNHttpServerConnection;
import org.apache.http.impl.nio.SSLNHttpServerConnectionFactory;
import org.apache.http.impl.nio.reactor.DefaultListeningIOReactor;
import org.apache.http.nio.NHttpConnectionFactory;
import org.apache.http.nio.NHttpServerConnection;
import org.apache.http.nio.entity.NByteArrayEntity;
import org.apache.http.nio.protocol.*;
import org.apache.http.nio.reactor.IOEventDispatch;
import org.apache.http.nio.reactor.ListeningIOReactor;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.*;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;

public class NIOSSLRequestListenerThread extends Thread {

    private static final Logger LOG = LogManager.getLogger(NIOSSLRequestListenerThread.class);

    private int port;
    private FinTSServer finTSServer;
    private ListeningIOReactor ioReactor;

    public NIOSSLRequestListenerThread(int port, FinTSServer server) {
        this.port = port;
        this.finTSServer = server;
    }

    public void stopListening() {
        if (ioReactor != null) {
            try {
                long gracePeriod = 3000;
                ioReactor.shutdown(gracePeriod);
            } catch (IOException e) {
                LOG.error("I/O error while shutting down ioReactor (stopListening)", e);
            }
        }
    }

    /**
     * @see <a href="http://hc.apache.org/httpcomponents-core-ga/httpcore-nio/examples/org/apache/http/examples/nio/NHttpServer.java">Apache HttpCore NIO Example</a>
     */
    @Override
    public void run() {
        try {
            /*** HTTP Core SSL Server ***/

            // HTTP parameters for the server
            HttpParams params = new SyncBasicHttpParams();
            params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)
                    .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
                    .setParameter(CoreProtocolPNames.ORIGIN_SERVER, "HttpComponents/1.1");

            // Create HTTP protocol processing chain
            HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpResponseInterceptor[]{
                    // Use standard server-side protocol interceptors
                    new ResponseDate(),
                    new ResponseServer(),
                    new ResponseContent(),
                    new ResponseConnControl()
            });

            // Create request handler registry
            HttpAsyncRequestHandlerRegistry reqistry = new HttpAsyncRequestHandlerRegistry();
            // Register the default handler for all URIs
            reqistry.register("*", new NioSSLHandler());

            // Create server-side HTTP protocol handler
            HttpAsyncService protocolHandler = new HttpAsyncService(
                    httpproc, new DefaultConnectionReuseStrategy(), reqistry, params) {

                @Override
                public void connected(final NHttpServerConnection conn) {
                    LOG.info("{}: connection open", conn);
                    super.connected(conn);
                }

                @Override
                public void closed(final NHttpServerConnection conn) {
                    LOG.info("{}: connection closed", conn);
                    super.closed(conn);
                }

            };

            // Create HTTP connection factory
            NHttpConnectionFactory<DefaultNHttpServerConnection> connFactory;
            // Initialize SSL context
            final KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("src/test/resources/keystore/keystore"), "123456".toCharArray());
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmfactory.init(ks, "123456".toCharArray());
            KeyManager[] keymanagers = kmfactory.getKeyManagers();
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(keymanagers, null, null);
            connFactory = new SSLNHttpServerConnectionFactory(sslcontext, null, params);

            // Create server-side I/O event dispatch
            IOEventDispatch ioEventDispatch = new DefaultHttpServerIODispatch(protocolHandler, connFactory);
            // Create server-side I/O reactor
            ioReactor = new DefaultListeningIOReactor();
            try {
                LOG.info("NIO SSLServer listening on port {}", port);
                // Listen of the given port
                ioReactor.listen(new InetSocketAddress(finTSServer.getServerAdress(), port));
                // Ready to go!
                ioReactor.execute(ioEventDispatch);
            } catch (InterruptedIOException ex) {
                LOG.error("Interrupted", ex);
            } catch (IOException e) {
                LOG.error("I/O error", e);
            }
            LOG.info("Shutdown");
        } catch (Exception e) {
            LOG.error("Exception", e);
        }
    }

    private class NioSSLHandler extends FinTSCommunicationHandler implements HttpAsyncRequestHandler<HttpRequest> {

        HttpResponse response;
        HttpAsyncExchange exchange;

        public NioSSLHandler() {
            super(CommunicationChannel.SSL);
        }

        @Override
        public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest request, HttpContext httpContext) throws HttpException, IOException {
            return new BasicAsyncRequestConsumer();
        }

        @Override
        public void handle(HttpRequest request, HttpAsyncExchange httpAsyncExchange, HttpContext httpContext) throws HttpException, IOException {
            finTSServer.addRequest(this);

            this.response = httpAsyncExchange.getResponse();
            this.exchange = httpAsyncExchange;

            String method = request.getRequestLine().getMethod().toUpperCase();
            if (!method.equals("POST")) {
                throw new MethodNotSupportedException(method + " method not supported");
            }

            if (request instanceof HttpEntityEnclosingRequest) {
                HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
                byte[] entityContent = EntityUtils.toByteArray(entity);
                FinTSVersionSwitch.selectController(this.taskId, entityContent, CommunicationChannel.SSL);
            }
        }

        @Override
        public void sendResponse(byte[] responseData, boolean isLastMessage) {
            updateTimeStamp();
            response.setStatusCode(HttpStatus.SC_OK);
            NByteArrayEntity entity = new NByteArrayEntity(responseData);
            response.setEntity(entity);
            exchange.submitResponse(new BasicAsyncResponseProducer(response));
        }
    }
}
