package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.exceptions.ElementParseException;
import org.junit.Test;

public class HKIDNTest {

    @Test
    public void testHKIDN() {

        try {
            HKIDN test = new HKIDN("HKIDN:2:2+280:20041144+9999999999+0+0".getBytes());
            test.parseElement();
            System.out.println(test.toString());
        } catch (ElementParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
