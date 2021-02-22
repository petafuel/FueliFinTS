package net.petafuel.fuelifints.communication;

import org.junit.Test;

public class BlockingSSLRequestListenerThreadTest {
    FinTSCommunicationHandler currentRequest;

    @Test
    public void testRun() throws Exception {
        /*
        Properties systemProps = System.getProperties();
        systemProps.put("javax.net.ssl.trustStore", "src/test/resources/keystore/truststore");
        systemProps.put("javax.net.ssl.trustStorePassword", "123456");
        System.setProperties(systemProps);

        FinTSServer server = mock(FinTSServer.class);
        BlockingSSLRequestListenerThread thread = new BlockingSSLRequestListenerThread(8080, server);
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
                    currentRequest.sendResponse("TestAnswer".getBytes());
                }
                currentRequest = null;
                return true;
            }
        });
        FinTSVersionSwitch.controller = controller;
        thread.start();

        Thread.sleep(2000);  //wait for server to start


        SSLSocketFactory sslsocketFactory = new SSLSocketFactory(null, null);
        sslsocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        Scheme https = new Scheme("https", sslsocketFactory, 8080);
        DefaultHttpClient httpClient = new DefaultHttpClient();

        httpClient.getConnectionManager().getSchemeRegistry().register(https);

        HttpPost httpPost = new HttpPost("https://localhost:8080");
        StringEntity entity = new StringEntity("HNHBK:1:3+000000000120+300+0+1'HKIDN:2:2+280:20041144+9999999999+0+0'HKVVB:3:2+0+0+0+123 Banking Android+0.3'HNHBS:4:1+1");

        httpPost.setEntity(entity);

        HttpResponse response = httpClient.execute(httpPost);
        Thread.sleep(1000);
        String responseString = EntityUtils.toString(response.getEntity());

        Assert.assertTrue(responseString.equals("TestAnswer"));
        */
    }
}
