package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.exceptions.ElementParseException;
import org.junit.Test;

public class HKVVBTest {

    @Test
    public void testHKVVB() {

        try {
            HKVVB test = new HKVVB("HKVVB:3:2+0+0+0+123 Banking Android+0.3".getBytes());
            test.parseElement();
            System.out.println(test.toString());
        } catch (ElementParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
