package net.petafuel.fuelifints.protocol.fints3.segments;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class SegmentTest {
    @Test
    public void testGetHbciEncoded() throws Exception {
        byte[] expected = "HNHBK:1:3+000000000120+300+0+1".getBytes("ISO-8859-1");
        Segment hnhbk = new HNHBK(expected);
        hnhbk.parseElement();
        byte[] hbciEncoded = hnhbk.getHbciEncoded();
        assertNotNull(hbciEncoded);
        assertArrayEquals(expected,hbciEncoded);

    }
}
