package net.petafuel.fuelifints.protocol.fints3.segments;

import org.junit.Test;

public class HKSALTest {
    @Test
    public void testHKSAL6() throws Exception {
        HKSAL hksal = new HKSAL("HKSAL:3:6+1234567::280:10020030+N".getBytes("ISO-8859-1"));
        hksal.parseElement();
    }

    /* Eventuell mal ein HKSAL version 7 suchen
    @Test
    public void testHKSAL7() throws Exception {
        HKSAL hksal = new HKSAL("HKSAL:3:6+1234567::280:10020030+N".getBytes("ISO-8859-1"));
        hksal.parseElement();
        System.out.println(new String(hksal.getHbciEncoded(),"ISO-8859-1"));
    }
    */
}
