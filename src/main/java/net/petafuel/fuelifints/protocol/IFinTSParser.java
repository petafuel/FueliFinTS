package net.petafuel.fuelifints.protocol;

import net.petafuel.fuelifints.HBCIParseException;
import net.petafuel.fuelifints.exceptions.DeprecatedVersionHKTAN;
import net.petafuel.fuelifints.exceptions.HBCIValidationException;
import net.petafuel.fuelifints.exceptions.UnregisteredClientProduct;
import net.petafuel.fuelifints.model.Dialog;

public interface IFinTSParser extends Runnable {

    public void setPayload(FinTSPayload payload);

    public void setDialog(Dialog dialog);

    public void parseAndValidateRequest(int taskId, byte[] request) throws HBCIParseException, SegmentNotSupportedException, HBCIValidationException, DeprecatedVersionHKTAN, UnregisteredClientProduct;

    public String getDialogId();
}
