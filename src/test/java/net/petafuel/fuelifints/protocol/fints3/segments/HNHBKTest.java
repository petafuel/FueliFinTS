package net.petafuel.fuelifints.protocol.fints3.segments;

import junit.framework.Assert;
import net.petafuel.fuelifints.exceptions.ElementParseException;
import org.junit.Test;

public class HNHBKTest {

    @Test
    public void testHNHBK() {

        try {
            HNHBK test = new HNHBK("HNHBK:1:3+000000000120+300+0+1".getBytes());
            test.parseElement();
            System.out.println(test.toString());
        } catch (ElementParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
