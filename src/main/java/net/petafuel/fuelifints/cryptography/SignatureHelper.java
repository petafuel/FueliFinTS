package net.petafuel.fuelifints.cryptography;

import net.petafuel.fuelifints.dataaccess.dataobjects.ReturnDataObject;
import net.petafuel.fuelifints.model.Dialog;
import net.petafuel.fuelifints.protocol.fints3.segments.HNSHA;
import net.petafuel.fuelifints.protocol.fints3.segments.HNSHK;

public interface SignatureHelper {
    public ReturnDataObject validateSignature(byte[] bytes, HNSHK hnshk, HNSHA hnsha, Dialog dialog);

    public byte[] sign(byte[] toSign, HNSHK hnshk);
}
