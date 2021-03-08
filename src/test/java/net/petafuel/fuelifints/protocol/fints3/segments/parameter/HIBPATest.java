package net.petafuel.fuelifints.protocol.fints3.segments.parameter;

import net.petafuel.fuelifints.FinTSVersionSwitch;
import net.petafuel.fuelifints.dataaccess.DummyAccessFacade;
import net.petafuel.fuelifints.dataaccess.dataobjects.CommonBankParameterDataObject;
import org.junit.Test;

public class HIBPATest {

    @Test
    public void testHIBPA() {
        DummyAccessFacade daf = new DummyAccessFacade(null);

        CommonBankParameterDataObject cbpdo = daf.getCommonBankParameters(FinTSVersionSwitch.FinTSVersion.FINTS_VERSION_3_0);
        HIBPA hibpa = new HIBPA(cbpdo,3);
    }


}
