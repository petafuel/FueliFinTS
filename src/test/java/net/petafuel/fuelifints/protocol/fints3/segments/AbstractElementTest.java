package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AbstractElementTest {
    @Test
    public void testGetHbciEncoded() throws Exception {
        Segment dummy = new Segment(new byte[0]) {
                @Element(
     description = {@ElementDescription(number = 1)})
            private Segmentkopf segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung("Dummy").setSegmentVersion(1).setSegmentNumber(1).build();

                @Element(
     description = {@ElementDescription(number = 2, length = -32)})
            @an
            private String text = "Hans@Maulwurf.de:hello world+some question?some sad smiley:'(";
        };

        byte[] hbciEncoded = dummy.getHbciEncoded();
        assertNotNull(hbciEncoded);
        assertEquals("Dummy:1:1+Hans?@Maulwurf.de?:hello world?+some question??some sad smiley?:?'(", new String(hbciEncoded));
    }
}
