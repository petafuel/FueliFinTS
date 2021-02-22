package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.exceptions.ElementParseException;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Kik;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Sicherheitsprofil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessageElementParserTest {

    @Test
    public void testFillElements() throws Exception {
        HNVSK hnvsk = new HNVSK("HNVSK:998:3+PIN:1+998+1+1::2+1:20020610:102044+2:2:13:@8@12345678:5:1+280:10020030:12345:V:0:0+0".getBytes("ISO-8859-1"));
        hnvsk.parseElement();
        Sicherheitsprofil sicherheitsprofil = hnvsk.getSicherheitsprofil();
        assertNotNull(sicherheitsprofil);
        String sicherheitsverfahren = sicherheitsprofil.getSicherheitsverfahren();
        Integer sicherheitsverfahrenversion = sicherheitsprofil.getSicherheitsverfahrensversion();
        assertNotNull(sicherheitsverfahren);
        assertNotNull(sicherheitsverfahrenversion);
        assertEquals("PIN",sicherheitsverfahren);
        assertEquals(new Integer(1),sicherheitsverfahrenversion);
    }

    @Test
    public void testFillElements2() throws ElementParseException {
        HKISA hkisa = new HKISA("HKISA:5:3+2+124+RDH:10+280:12345678:999:V:999:999".getBytes());
        hkisa.parseElement();
        System.out.println(hkisa);
    }

    @Test
    public void testKikParsing() throws ElementParseException {
        Kik kik = new Kik("280:12345678".getBytes());
        kik.parseElement();
        System.out.println(kik);
    }
}
