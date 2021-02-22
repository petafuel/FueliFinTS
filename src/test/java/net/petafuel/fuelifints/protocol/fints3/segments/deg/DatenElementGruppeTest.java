package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DatenElementGruppeTest {

    @Test
    public void testGetHbciEncoded() throws UnsupportedEncodingException {
        Segmentkopf segmentkopf = new Segmentkopf(new byte[0]);
        segmentkopf.segmentKennung = "HNHBK";
        segmentkopf.segmentNummer = 1;
        segmentkopf.segmentVersion = 3;
        byte[] hbciEncoded = segmentkopf.getHbciEncoded();
        assertNotNull(hbciEncoded);
        assertArrayEquals("HNHBK:1:3".getBytes(), hbciEncoded);
    }
}
