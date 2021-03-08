package net.petafuel.fuelifints.communication;

import junit.framework.Assert;
import net.petafuel.fuelifints.FinTSServer;
import net.petafuel.fuelifints.FinTSVersionSwitch;
import net.petafuel.fuelifints.IFinTSController;
import net.petafuel.fuelifints.protocol.FinTSPayload;
import net.petafuel.fuelifints.protocol.fints3.FinTS3Controller;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.util.Properties;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NIOSSLRequestListenerThreadTest {
    FinTSCommunicationHandler currentRequest;

    //@todo fix Test
    public void testRun() throws Exception {
        Properties systemProps = System.getProperties();
        systemProps.put("javax.net.ssl.trustStore", "src/test/resources/keystore/truststore");
        systemProps.put("javax.net.ssl.trustStorePassword", "123456");
        System.setProperties(systemProps);

        FinTSServer server = mock(FinTSServer.class);
        NIOSSLRequestListenerThread thread = new NIOSSLRequestListenerThread(8081, server);
        IFinTSController controller = mock(FinTS3Controller.class);
        when(server.addRequest((FinTSCommunicationHandler) anyObject())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                if (args.length > 0 && args[0] instanceof FinTSCommunicationHandler)
                    currentRequest = (FinTSCommunicationHandler) args[0];
                return true;
            }
        });

        when(controller.newRequest((FinTSPayload) anyObject(), eq(FinTSCommunicationHandler.CommunicationChannel.SSL))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                if (currentRequest != null) {
                    currentRequest.sendResponse("TestAnswer".getBytes(), false);
                }
                currentRequest = null;
                return true;
            }
        });
        FinTSVersionSwitch.controller = controller;
        thread.start();

        Thread.sleep(2000);  //wait for server to start

        SSLContext sslContext = new SSLContextBuilder().build();


        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setSSLSocketFactory(sslSocketFactory);
        httpClientBuilder.setSchemePortResolver(new SchemePortResolver() {
            @Override
            public int resolve(HttpHost httpHost) throws UnsupportedSchemeException {
                return 8081;
            }
        });

        CloseableHttpClient httpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost("https://localhost:8081");
        StringEntity entity = new StringEntity("HNHBK:1:3+000000000120+300+0+1'HKIDN:2:2+280:20041144+9999999999+0+0'HKVVB:3:2+0+0+0+123 Banking Android+0.3'HNHBS:4:1+1");

        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);
        Thread.sleep(1000);
        String responseString = EntityUtils.toString(response.getEntity());

        Assert.assertTrue(responseString.equals("TestAnswer"));

    }
}
