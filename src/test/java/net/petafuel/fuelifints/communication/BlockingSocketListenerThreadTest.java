package net.petafuel.fuelifints.communication;

import junit.framework.Assert;
import net.petafuel.fuelifints.FinTSServer;
import net.petafuel.fuelifints.FinTSVersionSwitch;
import net.petafuel.fuelifints.IFinTSController;
import net.petafuel.fuelifints.protocol.FinTSPayload;
import net.petafuel.fuelifints.protocol.fints3.FinTS3Controller;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.net.SocketFactory;
import java.io.BufferedOutputStream;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class BlockingSocketListenerThreadTest {

    FinTSCommunicationHandler currentRequest;

    //@todo fix Test
    public void testRun() throws Exception {
        FinTSServer server = mock(FinTSServer.class);
        BlockingSocketListenerThread thread = new BlockingSocketListenerThread(3000, server);
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

        String request = "HNHBK:1:3+000000000120+300+0+1'HKIDN:2:2+280:20041144+9999999999+0+0'HKVVB:3:2+0+0+0+123 Banking Android+0.3'HNHBS:4:1+1";
        SocketFactory socketFactory = SocketFactory.getDefault();
        Socket s = socketFactory.createSocket("localhost", 3000);
        s.setSoTimeout(1000);
        BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());

        bos.write(request.getBytes());
        bos.flush();
        Thread.sleep(1000);
        while (s.getInputStream().available() > 0) {
            int numBytes = s.getInputStream().available();
            byte[] bytes = new byte[numBytes];
            s.getInputStream().read(bytes);
            Assert.assertTrue(new String(bytes).equals("TestAnswer"));
        }
        s.close();
    }
}
