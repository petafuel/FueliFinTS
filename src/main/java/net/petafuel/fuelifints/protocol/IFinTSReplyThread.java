package net.petafuel.fuelifints.protocol;

import net.petafuel.fuelifints.FinTSServer;
import net.petafuel.fuelifints.model.Dialog;

public interface IFinTSReplyThread extends Runnable {

    public void setDialog(Dialog dialog);

    public void setFinTSServer(FinTSServer finTSServer);
}
