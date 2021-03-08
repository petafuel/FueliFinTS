package net.petafuel.fuelifints.protocol;

import net.petafuel.fuelifints.HBCIParseException;
import net.petafuel.fuelifints.exceptions.HBCIValidationException;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.Message;

public interface IFinTSParser extends Runnable {

    public void setPayload(FinTSPayload payload);

    public void setDialog(Dialog dialog);

    public void parseAndValidateRequest(int taskId, byte[] request) throws HBCIParseException, SegmentNotSupportedException, HBCIValidationException;

    public String getDialogId();
}
