package net.petafuel.fuelifints.protocol.fints3;

import net.petafuel.fuelifints.FinTSServer;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.IFinTSReplyThread;
import net.petafuel.fuelifints.protocol.fints3.segments.HNHBK;
import net.petafuel.fuelifints.protocol.fints3.segments.HNHBS;
import net.petafuel.fuelifints.protocol.fints3.segments.Segment;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Bezugsnachricht;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class FinTS3ReplyThread implements IFinTSReplyThread {

    private static final Logger LOG = LogManager.getLogger(FinTS3ReplyThread.class);

    private Dialog dialog;
    private FinTSServer finTSServer;

    @Override
    public void run() {
        LOG.trace("Respond element count: {}", dialog.getCurrentMessage().getMessageElements().size());

        HNHBK hnhbk = new HNHBK(new byte[0]);
        HNHBS hnhbs = new HNHBS(new byte[0]);
        Segmentkopf segmentkopf;// = new Segmentkopf("HNHBK:1:3".getBytes());
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(HNHBK.class).setSegmentNumber(1).setSegmentVersion(3).build();
        hnhbk.setSegmentkopf(segmentkopf);

        if (dialog.getMessageNumber() == 1) {
            String dialogId = generateDialogId();
            dialog.setDialogId(dialogId);
        }
        hnhbk.setDialog_id(dialog.getDialogId());
        hnhbk.setHbci_version(300);
        hnhbk.setNachrichtennummer(dialog.getMessageNumber());
        hnhbk.setNachrichtengroesse("012345678901");
        hnhbk.setBezugsnachricht(new Bezugsnachricht((dialog.getDialogId() + ":" + dialog.getMessageNumber()).getBytes()));

        //segmentkopf = new Segmentkopf(("HNHBS:"+(dialog.getCurrentMessage().getSegmentCount()+1)+":1").getBytes());
        segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(HNHBS.class).setSegmentNumber(dialog.getCurrentMessage().getSegmentCount() + 1).setSegmentVersion(1).build();
        hnhbs.setSegmentkopf(segmentkopf);
        hnhbs.setNachrichtennummer(dialog.getMessageNumber());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            byteArrayOutputStream.write(hnhbk.getHbciEncoded());
            byteArrayOutputStream.write('\'');
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
        for (IMessageElement iMessageElement : dialog.getCurrentMessage().getMessageElements()) {
            if (iMessageElement != null) {
                try {
                    byteArrayOutputStream.write(((Segment) iMessageElement).getHbciEncoded());
                    byteArrayOutputStream.write('\'');
                    byteArrayOutputStream.flush();
                } catch (IOException e) {
                    LOG.error("IOException", e);
                }
            } else {
                LOG.error("iMessageElement is null");
            }
        }
        try {
            byteArrayOutputStream.write(hnhbs.getHbciEncoded());
            byteArrayOutputStream.write('\'');
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
        byte[] respondBytes = byteArrayOutputStream.toByteArray();
        int length = respondBytes.length;
        byte[] lengthBytes = String.format("%012d", length).getBytes();
        System.arraycopy(lengthBytes, 0, respondBytes, 10, 12);
        LOG.trace("response length: {}", length);
        //try {
        LOG.debug("Response: {}", new String(respondBytes));
        //} catch (UnsupportedEncodingException e) {
        //}
        finTSServer.respond(dialog.getTaskId(), respondBytes, dialog.isLastMessage());
    }

    private String generateDialogId() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            SecureRandom random = new SecureRandom();
            byte[] bytes = messageDigest.digest(new BigInteger(130, random).toString(32).getBytes());
            LOG.trace("Generated DialogId: {} length: {}", new String(Base64.encode(bytes)), (new String(Base64.encode(bytes))).length());
            return (new String(Base64.encode(bytes))).replace('+', '-');
        } catch (NoSuchAlgorithmException e) {
        }
        return "fallbackMessage#";
    }

    @Override
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void setFinTSServer(FinTSServer finTSServer) {
        this.finTSServer = finTSServer;
    }
}
