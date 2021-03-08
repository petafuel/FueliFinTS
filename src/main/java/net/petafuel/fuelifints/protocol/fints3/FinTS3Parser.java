package net.petafuel.fuelifints.protocol.fints3;

import net.petafuel.fuelifints.HBCIParseException;
import net.petafuel.fuelifints.cryptography.SignatureHelper;
import net.petafuel.fuelifints.cryptography.SignatureHelperFactory;
import net.petafuel.fuelifints.dataaccess.dataobjects.ReturnDataObject;
import net.petafuel.fuelifints.exceptions.ElementParseException;
import net.petafuel.fuelifints.exceptions.HBCIValidationException;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.FinTSPayload;
import net.petafuel.fuelifints.protocol.IFinTSParser;
import net.petafuel.fuelifints.protocol.SegmentNotSupportedException;
import net.petafuel.fuelifints.protocol.fints3.model.SecurityMethod;
import net.petafuel.fuelifints.protocol.fints3.segments.HIRMS;
import net.petafuel.fuelifints.protocol.fints3.segments.HNSHA;
import net.petafuel.fuelifints.protocol.fints3.segments.HNSHK;
import net.petafuel.fuelifints.protocol.fints3.segments.SegmentManager;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Rueckmeldung;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * - parsed die Auftragelemente (IMessageElement), die sich im Request befinden
 * <p/>
 * <p/>
 * Entschlüsselung und Verifizierung der Signatur erfolt nicht hier, sondern im IFinTSDecryptor
 * Es kann davon ausgegangen werden, dass die Nachricht fehlerfrei entschlüsselt und die Signatur verifiziert wurde
 */

public class FinTS3Parser implements IFinTSParser, Runnable {

    private static final Logger LOG = LogManager.getLogger(FinTS3Parser.class);
    private Dialog dialog;
    private byte[] request;
    private ArrayList<IMessageElement> elements;
    private int taskId;
    private String dialogId = "";

    public int getTaskId() {
        return this.taskId;
    }

    @Override
    public void setPayload(FinTSPayload payload) {
        this.request = payload.getPayload();
        this.taskId = payload.getTaskId();
    }

    @Override
    public void parseAndValidateRequest(int taskId, byte[] request) throws HBCIParseException, SegmentNotSupportedException, HBCIValidationException {

        //split request in segment objects:
        this.elements = SegmentManager.getSegments(request);
        HNSHK hnshk = null;
        HNSHA hnsha = null;
        List<byte[]> signedSegments = new LinkedList<byte[]>();
        for (IMessageElement messageElement : elements) {
            if (messageElement instanceof HNSHK) {
                hnshk = (HNSHK) messageElement;
                dialog.setBankId(hnshk.getSchluesselname().getKreditsinstitutkennung().getKreditinstitutscode());
            }
            if (messageElement instanceof HNSHA) {
                hnsha = (HNSHA) messageElement;
                break;
            }
            //die Segmente zwischen HNSHK und HNVSK werden nur zur Signaturprüfung herangezogen
            //deshalb ist es an dieser Stelle möglich nachdem HNSHK gefunden wurde, alle Segmente
            //die anschließend folgen bis hin zu HNSHA in die Signaturprüfung mit aufzunehmen
            if (hnshk != null) {
                signedSegments.add(messageElement.getBytes());
            }
        }

        if (hnshk != null && hnsha == null) {
            throw new HBCIValidationException("Signaturabschluss fehlt.");
        }

        //bei anonymend dialog wird nicht signiert, deshalb wird hier geprüft
        //ob die Signatur verifiziert werden muss
        if (hnshk != null) {
            ReturnDataObject validSignature = new ReturnDataObject(false, "");
            try {
                validSignature = validateSignature(hnshk, hnsha, signedSegments);
            } catch (UnsupportedEncodingException e) {
                //ignored ISO-8859-1 is supported
            } catch (ElementParseException e) {
                LOG.error("ElementParseException", e);
            }

            if (!validSignature.isSuccess()) {
                switch (SecurityMethod.valueOf(hnshk.getSicherheitsprofil().getSicherheitsverfahren() + "_" + hnshk.getSicherheitsprofil().getSicherheitsverfahrensversion())) {
                    case PIN_1:
                    case PIN_2:

                        HIRMS hirms = new HIRMS(new byte[0]);
                        hirms.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(hnsha.getSegmentkopf().getSegmentNummer()).build());
                        HBCIValidationException exception;
                        if (validSignature.getMessage().equals("")) {
                            Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("9910");
                            hirms.addRueckmeldung(rueckmeldung);

                            exception = new HBCIValidationException("PIN/TAN ungültig.");
                        } else {
                            Rueckmeldung rueckmeldung = new Rueckmeldung(("9910::" + validSignature.getMessage()).getBytes());
                            hirms.addRueckmeldung(rueckmeldung);
                            exception = new HBCIValidationException(validSignature.getMessage());
                        }
                        exception.setRueckmeldung(hirms);
                        throw exception;
                    case RDH_9:
                    case RDH_10:
                        throw new HBCIValidationException("Verarbeitung nicht möglich.", false);
                    default:
                        throw new HBCIValidationException("Kein unterstützes Sicherheitsverfahren verwendet. Signatur ungültig.");
                }
            }
            if (validSignature.getReturnCode() != null) {
                HIRMS hirms = new HIRMS(new byte[0]);
                hirms.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setBezugssegment(hnsha.getSegmentkopf().getSegmentNummer()).build());
                Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung(validSignature.getReturnCode());
                hirms.addRueckmeldung(rueckmeldung);
                dialog.getCurrentMessage().addReplyElement(hirms);
            }
        }
        LOG.info("Signaturprüfung erfolgreich.");

        dialog.getCurrentMessage().getMessageElements().addAll(elements);

        if (hnshk != null) {
            dialog.getCurrentMessage().setSecurityMethod(SecurityMethod.valueOf(hnshk.getSicherheitsprofil().getSicherheitsverfahren() + "_" + hnshk.getSicherheitsprofil().getSicherheitsverfahrensversion()));
            dialog.getCurrentMessage().setUserId(hnshk.getSchluesselname().getBenutzerkennung());
            dialog.getCurrentMessage().setBankId(hnshk.getSchluesselname().getKreditsinstitutkennung().getKreditinstitutscode());
        }
    }

    /**
     * Prüft die Signatur-Segmente einer Nachricht.
     *
     * @param hnshk    Signaturkopf
     * @param hnsha    Signaturabschluss
     * @param segments signierte Segmente
     * @return true falls Signatur übereinstimmt, andernfalls false.
     */
    private ReturnDataObject validateSignature(HNSHK hnshk, HNSHA hnsha, List<byte[]> segments) throws UnsupportedEncodingException, ElementParseException, HBCIValidationException {
        SignatureHelper signatureHelper = SignatureHelperFactory.getSignatureHelper(SecurityMethod.valueOf(hnshk.getSicherheitsprofil().getSicherheitsverfahren() + "_" + hnshk.getSicherheitsprofil().getSicherheitsverfahrensversion()));
        int sigLength = 0;
        for (byte[] segment : segments) {
            sigLength += segment.length;
        }
        byte[] signedData = new byte[sigLength + segments.size()];
        int offset = 0;
        for (byte[] segment : segments) {
            System.arraycopy(segment, 0, signedData, offset, segment.length);
            offset += segment.length;
            signedData[offset++] = 0x27;
        }
        LOG.debug("Data to check Singature: {}", new String(signedData, "ISO-8859-1"));
        return signatureHelper.validateSignature(signedData, hnshk, hnsha, dialog);
    }

    public String getDialogId() {
        return dialogId;
    }

    @Override
    public void run() {
        try {
            if (request != null) {
                parseAndValidateRequest(taskId, request);

            }
        } catch (HBCIParseException | SegmentNotSupportedException e) {
            HIRMS error = new HIRMS(new byte[0]);
            error.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).build());
            Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("9000");
            error.addRueckmeldung(rueckmeldung);
            dialog.getErrorInfo().setErrorOccured(true);
            dialog.getErrorInfo().setErrorSegment(error);
        } catch (HBCIValidationException hbciValidationException) {

            if (hbciValidationException.isShowDetails()) {
                HIRMS error;
                if (hbciValidationException.getRueckmeldung() == null) {
                    error = new HIRMS(new byte[0]);
                    error.setSegmentkopf(Segmentkopf.Builder.newInstance().setSegmentKennung(HIRMS.class).setSegmentVersion(2).setSegmentNumber(0).build());
                    Rueckmeldung rueckmeldung = Rueckmeldung.getRueckmeldung("9999");
                    error.addRueckmeldung(rueckmeldung);
                    rueckmeldung = new Rueckmeldung(new byte[0]);
                    rueckmeldung.setRueckmeldungstext(hbciValidationException.getMessage());
                    rueckmeldung.setRueckmeldungscode("9999");
                    error.addRueckmeldung(rueckmeldung);
                } else {
                    error = hbciValidationException.getRueckmeldung();
                }
                dialog.getErrorInfo().setErrorSegment(error);
            }
            dialog.getErrorInfo().setErrorOccured(true);

        }
        FinTS3Controller.getInstance().finishedParsing(taskId, dialog);
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
