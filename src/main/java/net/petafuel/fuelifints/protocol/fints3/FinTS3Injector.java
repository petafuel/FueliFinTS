package net.petafuel.fuelifints.protocol.fints3;

import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.protocol.IFinTSInjector;

/**
 * FFTS-27: Kann raus!
 */
@Deprecated
public class FinTS3Injector implements IFinTSInjector {

    private Dialog dialog;

    @Override
    public void run() {
        FinTS3Controller.getInstance().finishedInjecting(dialog.getTaskId());
    }

    @Override
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
