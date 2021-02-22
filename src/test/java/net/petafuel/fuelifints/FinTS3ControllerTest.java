package net.petafuel.fuelifints;

import junit.framework.Assert;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.Message;
import net.petafuel.fuelifints.protocol.FinTSPayload;
import net.petafuel.fuelifints.protocol.IFinTSDecryptor;
import net.petafuel.fuelifints.protocol.IFinTSExecutor;
import net.petafuel.fuelifints.protocol.IFinTSInjector;
import net.petafuel.fuelifints.protocol.IFinTSParser;
import net.petafuel.fuelifints.protocol.IFinTSReplyThread;
import net.petafuel.fuelifints.protocol.fints3.FinTS3Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyObject;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FinTS3ControllerTest {

    private static final Logger LOG = LogManager.getLogger(FinTS3ControllerTest.class);

    final static Object o = new Object();


    @Before
    public void before() {
        //Remove existing FinTS3Controller instance:
        FinTS3Controller.removeInstance();

        //Setup mocked classes
        final Message mockedMessage = mock(Message.class);
        when(mockedMessage.getMessageId()).thenReturn("0");     //First message in a dialog has to have message id 0
        when(mockedMessage.getTaskId()).thenReturn(1234);

        FinTSServer mockedServer = mock(FinTSServer.class);
        when(mockedServer.respond(eq(1234), any(byte[].class), any(boolean.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                LOG.info("mockedServer.respond called!");
                if (invocationOnMock.getArguments() != null && invocationOnMock.getArguments().length == 2)
                    LOG.info(new String((byte[]) invocationOnMock.getArguments()[1]));
                synchronized (o) {
                    o.notifyAll();
                }
                return true;
            }
        });

        ExecutorManager mockedManager = mock(ExecutorManager.class);

        //Step 1: Decrypt Message
        when(mockedManager.addToDecryptionQueue((Dialog) anyObject(), (FinTSPayload) anyObject(), (IFinTSDecryptor) anyObject())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                LOG.info("mockedManager.addToDecryptionQueue called!");
                Object[] args = invocationOnMock.getArguments();
                Assert.assertTrue(args.length > 0);
                Assert.assertTrue(args[1] instanceof FinTSPayload);
                Assert.assertEquals(1234, ((FinTSPayload) args[1]).getTaskId());
                FinTS3Controller.getInstance().finishedDecryption((FinTSPayload) args[1], (Dialog) args[0]);
                return true;
            }
        });

        //Step 2: Parse & verifySyntax
        when(mockedManager.addToParseQueue((Dialog) anyObject(), (FinTSPayload) anyObject(), (IFinTSParser) anyObject())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                LOG.info("mockedManager.addToParseQueue called!");
                Object[] args = invocationOnMock.getArguments();
                Assert.assertTrue(args.length > 0);
                Assert.assertTrue(args[1] instanceof FinTSPayload);
                Assert.assertEquals(1234, ((FinTSPayload) args[1]).getTaskId());
                FinTS3Controller.getInstance().finishedParsing(((FinTSPayload) args[1]).getTaskId(), (Dialog) args[0]);
                return true;
            }
        });

        //Step 3: CheckInjection & injection
        when(mockedManager.addToInjectionQueue((Dialog) anyObject(), (IFinTSInjector) anyObject())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                LOG.info("mockedManager.addToInjectionQueue called!");
                Object[] args = invocationOnMock.getArguments();
                Assert.assertTrue(args.length > 0);
                Assert.assertTrue(args[0] instanceof Dialog);
                FinTS3Controller.getInstance().finishedInjecting(((Dialog) args[0]).getTaskId());
                return true;
            }
        });

        //Step 4: Execute
        when(mockedManager.addToExecuteQueue((Dialog) anyObject(), (IFinTSExecutor) anyObject())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                LOG.info("mockedManager.addToExecuteQueue called!");
                Object[] args = invocationOnMock.getArguments();
                assertTrue(args.length > 0);
                Assert.assertTrue(args[0] instanceof Dialog);
                FinTS3Controller.getInstance().finishedExecuting(((Dialog) args[0]).getCurrentMessage());
                return true;
            }
        });

        //Step 5: Reply
        when(mockedManager.addToReplyQueue((Dialog) anyObject(), any(FinTSServer.class), any(IFinTSReplyThread.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                LOG.info("mockedManager.addToReplyQueue called!");
                Object[] args = invocationOnMock.getArguments();
                Assert.assertTrue(args.length > 0);
                Assert.assertTrue(args[0] instanceof Dialog);
                return true;
            }
        });
        IFinTSController controller = FinTS3Controller.getInstance();
        controller.setExecutor(mockedManager);
        controller.setFinTSServer(mockedServer);
    }

    @Test
    @Ignore
    public void testEndToEnd() throws Exception {
        /*
        new Thread() {
            public void run() {
                byte[] request = "HNHBK:1:3+000000000120+300+0+1'HKIDN:2:2+280:20041144+9999999999+0+0'HKVVB:3:2+0+0+0+123 Banking Android+0.3'HNHBS:4:1+1".getBytes();
                FinTSPayload payload = new FinTSPayload(request, 1234);
                FinTS3Controller.getInstance().newRequest(payload, FinTSCommunicationHandler.CommunicationChannel.SSL);
            }
        }.start();  //I don't know why newRequest has to be started in a new thread, but otherwise notification for o comes before lock...*/
        /*
        synchronized (o) {
            o.wait(); //wait for tests to complete (i.e. wait for mockedServer to receive response)
        } */
    }
}
