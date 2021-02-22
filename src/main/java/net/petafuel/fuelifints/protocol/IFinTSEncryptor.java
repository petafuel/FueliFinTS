package net.petafuel.fuelifints.protocol;

import net.petafuel.fuelifints.model.Dialog;

public interface IFinTSEncryptor extends Runnable{

    public void setDialog(Dialog dialog);
}
