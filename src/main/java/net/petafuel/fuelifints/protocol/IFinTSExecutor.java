package net.petafuel.fuelifints.protocol;

import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.model.Message;

public interface IFinTSExecutor extends Runnable {

    public Message execute();

    public void setDialog(Dialog dialog);
}
