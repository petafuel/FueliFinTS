package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.HBCIParseException;
import net.petafuel.fuelifints.exceptions.ElementParseException;
import net.petafuel.fuelifints.exceptions.HBCISyntaxException;
import net.petafuel.fuelifints.exceptions.HBCIValidationException;
import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.SegmentNotSupportedException;
import net.petafuel.fuelifints.protocol.fints3.segments.acknowledgement.AckString;
import net.petafuel.fuelifints.support.ByteSplit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SegmentManager {
    private static final Logger LOG = LogManager.getLogger(SegmentManager.class);
    /**
     * Parst den Auftrag und liefert die entsprechenden Segmente.
     * Es wird zunächst der Auftrag in die einzelnen Segmente gesplitted, anschließend
     * wird für jedes Segment eine Instanz erstellt und die Werte der enstprechenden
     * Datenelemte gesetzt.
     *
     * @param requestBytes entschlüsselter Auftrag als byte[].
     * @return die entsprechenden Segmente die dieser Auftrag beinhaltet.
     * @throws SegmentNotSupportedException
     * @throws HBCIParseException
     */
    public static ArrayList<IMessageElement> getSegments(byte[] requestBytes) throws SegmentNotSupportedException, HBCIParseException {
        List<byte[]> segments = null;
        try {
            segments = ByteSplit.split(requestBytes, ByteSplit.MODE_SEGMENT);
        } catch (HBCISyntaxException e) {
            throw new HBCIParseException(e);
        }
        ArrayList<IMessageElement> returnElements = new ArrayList<IMessageElement>();
        for (byte[] segmentBytes : segments) {
            Segment segment = getSegment(segmentBytes);
            try {
                segment.parseElement();
                segment.validate();
                if (segment != null) {
                    returnElements.add(segment);
                }
            } catch (HBCIValidationException e) {
                throw new HBCIParseException(e);
            } catch (ElementParseException e) {
                throw new HBCIParseException(e);
            }
        }
        if (returnElements != null) {
            return returnElements;
        }
        throw new HBCIParseException(AckString.getAckString("9010"));
    }

    private static String getSegmentName(byte[] segment) {
        int index = 0;
        for (byte b : segment) {
            if (b == ':') {
                break;
            }
            index++;
        }
        try {
            return new String(segment, 0, index, "ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            //ignored ISO-8859-1 (Latin-1) is supported
        }
        return "";
    }

    private static Segment getSegment(byte[] segment) throws SegmentNotSupportedException {
        String segmentName = getSegmentName(segment);
        //try to find Segment Class:
        try {
            Class segmentClass = Class.forName("net.petafuel.fuelifints.protocol.fints3.segments." + segmentName.toUpperCase());

            Class[] constructorParams = new Class[]{byte[].class};
            Constructor constructor = segmentClass.getConstructor(constructorParams);
            Object[] messageObject = new Object[]{segment};
            return (Segment) constructor.newInstance(messageObject);
        } catch (ClassNotFoundException e) {
            throw new SegmentNotSupportedException(segmentName);
        } catch (InvocationTargetException e) {
            LOG.error("InvocationTargetException", e);
        } catch (NoSuchMethodException e) {
            LOG.error("NoSuchMethodException", e);
        } catch (InstantiationException e) {
            LOG.error("InstantiationException", e);
        } catch (IllegalAccessException e) {
            LOG.error("IllegalAccessException", e);
        }
        return null;
    }

    /**
     * Factory method, instantiate new Segment
     *
     * @param name
     * @param message
     * @return
     */
    private static Segment getSegment(String name, String message) throws SegmentNotSupportedException {
        try {
            //try to find Segment Class:
            Class segment = Class.forName("net.petafuel.fuelifints.protocol.fints3.segments." + name.toUpperCase());

            //instantiate and return Segment:
            Class[] constructorParams = new Class[]{String.class};
            java.lang.reflect.Constructor constructor = segment.getConstructor(constructorParams);
            Object[] messageObject = new Object[]{message};
            return (Segment) constructor.newInstance(messageObject);

        } catch (ClassNotFoundException e) {
            throw new SegmentNotSupportedException(name);
        } catch (NoSuchMethodException e) {
            LOG.info("Falscher Parameter, Konstruktor(String) zu Segment {} nicht gefunden", name);
        } catch (InvocationTargetException e) {
            throw new SegmentNotSupportedException(name);
        } catch (InstantiationException e) {
            throw new SegmentNotSupportedException(name);
        } catch (IllegalAccessException e) {
            throw new SegmentNotSupportedException(name);
        }

        return null;
    }
}
