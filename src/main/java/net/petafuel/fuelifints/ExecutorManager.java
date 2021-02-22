package net.petafuel.fuelifints;

import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.protocol.FinTSPayload;
import net.petafuel.fuelifints.protocol.IFinTSDecryptor;
import net.petafuel.fuelifints.protocol.IFinTSEncryptor;
import net.petafuel.fuelifints.protocol.IFinTSExecutor;
import net.petafuel.fuelifints.protocol.IFinTSInjector;
import net.petafuel.fuelifints.protocol.IFinTSParser;
import net.petafuel.fuelifints.protocol.IFinTSReplyThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorManager {
    private static final Logger LOG = LogManager.getLogger(ExecutorManager.class);

    private ThreadPoolExecutor decryptQueue = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));
    private ThreadPoolExecutor parseQueue = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));
    private ThreadPoolExecutor injectionQueue = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));
    private ThreadPoolExecutor executeQueue = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));
    private ThreadPoolExecutor encryptionQueue = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));
    private ThreadPoolExecutor replyQueue = new ThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(15));

    public boolean addToDecryptionQueue(Dialog dialog, FinTSPayload payload, IFinTSDecryptor decryptor) {
        LOG.trace("addToDecryptionQueue called by {}", new Throwable().getStackTrace()[1].getMethodName());
        try {
            decryptor.setPayload(payload);
            decryptor.setDialog(dialog);
            decryptQueue.execute(decryptor);
            return true;
        } catch (Exception e) {
            LOG.error("addToDecryptionQueue", e);
            return false;
        }
    }

    /**
     * @param payload message from client + taskId
     * @param parser  FinTS3 or FinTS4 Parser
     * @return true, if no problems with adding request to parseQueue occured
     */
    public boolean addToParseQueue(Dialog dialog, FinTSPayload payload, IFinTSParser parser) {
        LOG.trace("addToParseQueue called by {}", new Throwable().getStackTrace()[1].getMethodName());
        try {
            parser.setPayload(payload);
            parser.setDialog(dialog);
            parseQueue.execute(parser);
            return true;
        } catch (Exception e) {
            LOG.error("addToParseQueue", e);
            return false;
        }
    }

    public boolean addToInjectionQueue(Dialog dialog, IFinTSInjector injector) {
        try {
            injector.setDialog(dialog);
            injectionQueue.execute(injector);
            return true;
        } catch (Exception e) {
            LOG.error("addToInjectionQueue", e);
            return false;
        }
    }

    public boolean addToExecuteQueue(Dialog dialog, IFinTSExecutor executor) {
        LOG.trace("addToExecuteQueue");
        try {
            executor.setDialog(dialog);
            executeQueue.execute(executor);
            return true;
        } catch (Exception e) {
            LOG.error("addToExecuteQueue", e);
            return false;
        }
    }

    public boolean addToReplyQueue(final Dialog dialog, final FinTSServer finTSServer, IFinTSReplyThread finTSReplyThread) {
        LOG.trace("addToReplyQueue");
        finTSReplyThread.setDialog(dialog);
        finTSReplyThread.setFinTSServer(finTSServer);
        try {
            replyQueue.execute(finTSReplyThread);
            return true;
        } catch (Exception e) {
            LOG.error("addToReplyQueue", e);
            return false;
        }
    }

    public void addToEncryptionQueue(Dialog dialog, IFinTSEncryptor finTSEncryptor) {
        LOG.trace("addToEncryptionQueue");
        finTSEncryptor.setDialog(dialog);
        encryptionQueue.execute(finTSEncryptor);
    }
}
