package net.petafuel.fuelifints.protocol;

import net.petafuel.fuelifints.model.Dialog;

public interface IFinTSDecryptor extends Runnable {

    public void setPayload(FinTSPayload payload);

    public void setDialog(Dialog dialog);

}
