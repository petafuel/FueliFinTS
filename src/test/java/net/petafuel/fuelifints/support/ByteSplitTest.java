package net.petafuel.fuelifints.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

public class ByteSplitTest {

    private static final Logger LOG = LogManager.getLogger(ByteSplitTest.class);

    @Test
    public void testSimpleMessage() throws Exception {
        String inputString = "***REMOVED***";

        byte[] testMessage = inputString.getBytes("ISO-8859-1");

        List<byte[]> segments = ByteSplit.split(testMessage, ByteSplit.MODE_SEGMENT);
        assertNotNull(segments);

        assertEquals(1, segments.size());
        assertTrue(new String(segments.get(0), "ISO-8859-1") + "\n" + Arrays.toString(segments.get(0)) + "\n" + Arrays.toString(testMessage), Arrays.equals(inputString.substring(0, inputString.length() - 1).getBytes(), segments.get(0)));
        assertEquals(new String(segments.get(0), "ISO-8859-1"), testMessage.length - 1, segments.get(0).length);
    }

    @Test
    public void testMultipleSegmentsMessage() throws Exception {
        String inputString = "***REMOVED***";
        byte[] testMessage = inputString.getBytes("ISO-8859-1");

        List<byte[]> segments = ByteSplit.split(testMessage, ByteSplit.MODE_SEGMENT);
        assertNotNull(segments);

        assertEquals(2, segments.size());
    }

    @Test
    public void testEscapeCharMessage() throws Exception {
        String inputString = "***REMOVED***";
        byte[] testMessage = inputString.getBytes("ISO-8859-1");

        List<byte[]> segments = ByteSplit.split(testMessage, ByteSplit.MODE_SEGMENT);
        assertNotNull(segments);

        assertEquals(1, segments.size());
        assertTrue(new String(segments.get(0), "ISO-8859-1") + "\n" + Arrays.toString(segments.get(0)) + "\n" + Arrays.toString(testMessage), Arrays.equals(inputString.substring(0, inputString.length() - 1).getBytes(), segments.get(0)));
        assertEquals(new String(segments.get(0), "ISO-8859-1"), testMessage.length - 1, segments.get(0).length);
    }

    @Test
    public void testBinary() throws Exception {
        //String inputString = "***REMOVED***";
        String inputString = "***REMOVED***";

        byte[] testMessage = inputString.getBytes("ISO-8859-1");

        List<byte[]> segments = ByteSplit.split(testMessage, ByteSplit.MODE_SEGMENT);
        assertNotNull(segments);

        assertEquals(1, segments.size());
        assertTrue(new String(segments.get(0), "ISO-8859-1") + "\n" + Arrays.toString(segments.get(0)) + "\n" + Arrays.toString(testMessage), Arrays.equals(inputString.substring(0, inputString.length() - 1).getBytes("ISO-8859-1"), segments.get(0)));
        assertEquals(new String(segments.get(0), "ISO-8859-1"), testMessage.length - 1, segments.get(0).length);
    }

    @Test
    public void testDummyHbciMessage() throws Exception {
        String inputString = "***REMOVED***";
        byte[] testMessage = inputString.getBytes("ISO-8859-1");

        List<byte[]> segments = ByteSplit.split(testMessage, ByteSplit.MODE_SEGMENT);
        assertNotNull(segments);

        assertEquals(3, segments.size());
    }

    @Test
    public void testHbciMessage() throws Exception {
        String base64 = "***REMOVED***";

        byte[] message = Base64.decode(base64);

        LOG.info(new String(message, "ISO-8859-1"));

        List<byte[]> segments = ByteSplit.split(message, ByteSplit.MODE_SEGMENT);
        for (byte[] segment : segments) {
            LOG.info(new String(segment, "ISO-8859-1"));
        }
        assertEquals(4, segments.size());
    }

    @Test
    public void testHbciMessage2() throws Exception {
        String inputString = "***REMOVED***";
        byte[] testMessage = inputString.getBytes("ISO-8859-1");

        List<byte[]> segments = ByteSplit.split(testMessage, ByteSplit.MODE_SEGMENT);
        assertNotNull(segments);

        assertEquals(4, segments.size());
        for (byte[] segment : segments) {
            //LOG.info(new String(segment,"ISO-8859-1"));
        }
    }

    @Test
    public void testMt940Message() throws Exception {
        String base64 = "***REMOVED***";

        byte[] message = Base64.decode(base64);

        LOG.info(new String(message, "ISO-8859-1"));

        List<byte[]> segments = ByteSplit.split(message, ByteSplit.MODE_SEGMENT);
        for (byte[] segment : segments) {
            LOG.info(new String(segment, "ISO-8859-1"));
        }
        assertEquals(4, segments.size());
    }


    @Test
    public void testModeDEG() throws Exception {
        String inputString = "***REMOVED***";
        byte[] bytes = inputString.getBytes("ISO-8859-1");
        List<byte[]> degs = ByteSplit.split(bytes, ByteSplit.MODE_DEG);
        assertNotNull(degs);
        assertEquals("Expected Size", 2, degs.size());
    }

    @Test
    public void testBinaryDEG() throws Exception {
        String inputString = "***REMOVED***";
        byte[] bytes = inputString.getBytes("ISO-8859-1");
        List<byte[]> degs = ByteSplit.split(bytes, ByteSplit.MODE_DEG);
        assertNotNull(degs);
        assertEquals("Expected Size", 2, degs.size());
    }

    @Test
    public void testModeDEGEmptyDEGS() throws Exception {
        String inputString = "***REMOVED***";
        byte[] bytes = inputString.getBytes("ISO-8859-1");
        List<byte[]> degs = ByteSplit.split(bytes, ByteSplit.MODE_DEG);
        assertNotNull(degs);
        assertEquals("Expected Size", 4, degs.size());
    }

    @Test
    public void testModeDE() throws Exception {
        String inputString = "***REMOVED***";
        byte[] bytes = inputString.getBytes("ISO-8859-1");
        List<byte[]> des = ByteSplit.split(bytes, ByteSplit.MODE_DE);
        assertNotNull(des);
        assertEquals("Expected Size: ", 3, des.size());
    }

    @Test
    public void testModeDEEmptyDES() throws Exception {
        String inputString = "***REMOVED***";
        byte[] bytes = inputString.getBytes("ISO-8859-1");
        List<byte[]> des = ByteSplit.split(bytes, ByteSplit.MODE_DE);
        assertNotNull(des);
        assertEquals("Expected Size: ", 3, des.size());
        for (byte[] de : des) {
            LOG.info(new String(de, "ISO-8859-1"));
        }
    }


    @Test
    public void testBinary2() throws Exception {
        String inputString = "***REMOVED***";
        byte[] bytes = inputString.getBytes("ISO-8859-1");
        List<byte[]> des = ByteSplit.split(bytes, ByteSplit.MODE_SEGMENT);
        assertNotNull(des);
        assertEquals("Expected Size: ", 1, des.size());
        for (byte[] de : des) {
            LOG.info(new String(de, "ISO-8859-1"));
        }
    }
}
