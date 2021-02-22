package net.petafuel.fuelifints.protocol.fints3.segments.parameter;

import net.petafuel.fuelifints.FinTSVersionSwitch;
import net.petafuel.fuelifints.dataaccess.DummyAccessFacade;
import net.petafuel.fuelifints.dataaccess.dataobjects.ParameterDataObject;
import org.junit.Test;

import java.util.ArrayList;

public class ParameterSegmentTest {

    @Test
    public void testParameterSegment() {

        DummyAccessFacade daf = new DummyAccessFacade(null);
        ArrayList<ParameterDataObject> dataObjects = daf.getParameterData(FinTSVersionSwitch.FinTSVersion.FINTS_VERSION_3_0);

        for (ParameterDataObject paramData : dataObjects) {
            ParameterSegment segment = new ParameterSegment(paramData, 0);
        }
    }

}
