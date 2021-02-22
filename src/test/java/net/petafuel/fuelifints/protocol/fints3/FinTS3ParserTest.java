package net.petafuel.fuelifints.protocol.fints3;

import org.junit.Test;

public class FinTS3ParserTest {
    @Test
    public void testParseAndValidateRequest() throws Exception {
        /*
        String testMessage = "HNHBK:1:3+000000000121+300+0+1'HKIDN:2:2+280:20041144+9999999999+0+0'HKVVB:3:2+0+0+0+123 Banking Android+0.3'HNHBS:4:1+1'";
        FinTS3Parser parser = new FinTS3Parser();
        parser.parseAndValidateRequest(0, testMessage.getBytes());
        */
    }

    @Test
    public void testGetBankPublicKeys() {
        String testMessage = "HNHBK:1:3+000000000121+300+0+1'" +
                "HKIDN:2:2+280:20041144+9999999999+0+0'" +
                "HKVVB:3:2+0+0+0+123 Banking Android+0.3'" +
                "HKISA:4:3+2+124+RDH:10+280:10020030:9999999999:V:1:1'" +
                "HKISA:5:3+2+124+RDH:10+280:10020030:9999999999:S:1:1'" +
                "HNHBS:6:1+1'";
    }
}
