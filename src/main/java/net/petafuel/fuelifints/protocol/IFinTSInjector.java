package net.petafuel.fuelifints.protocol;

import net.petafuel.fuelifints.model.Dialog;

/**
 * FFTS-27 - Kann raus!
 */
public interface IFinTSInjector extends Runnable {
    public void setDialog(Dialog dialog);
}
