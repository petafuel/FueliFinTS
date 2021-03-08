package net.petafuel.fuelifints.protocol.fints3.segments.acknowledgement;

import org.junit.Assert;
import org.junit.Test;

public class AckStringTest {
    @Test
    public void testGetAckString() throws Exception {
        Assert.assertEquals("0010::Entgegengenommen", AckString.getAckString("0010"));
        Assert.assertEquals("0020::Ausgeführt", AckString.getAckString("0020"));
        Assert.assertEquals("0100::Dialog beendet.", AckString.getAckString("0100"));
        Assert.assertEquals("0900::TAN gültig", AckString.getAckString("0900"));
        Assert.assertEquals("0901::PIN gültig", AckString.getAckString("0901"));

        Assert.assertEquals("1010::Es liegen neue Kontoinformationen vor", AckString.getAckString("1010"));
        Assert.assertEquals("1040::BPD nicht mehr aktuell, aktuelle Version enthalten", AckString.getAckString("1040"));
        Assert.assertEquals("1050::UPD nicht mehr aktuell, aktuelle Version enthalten", AckString.getAckString("1050"));
        Assert.assertEquals("1060::Teilweise liegen Hinweise vor", AckString.getAckString("1060"));

        Assert.assertEquals("3010::Nicht verfügbar", AckString.getAckString("3010"));

        Assert.assertEquals("9010::Verarbeitung nicht möglich", AckString.getAckString("9010"));
        Assert.assertEquals("9020::Antwort zu groß", AckString.getAckString("9020"));
    }
}
