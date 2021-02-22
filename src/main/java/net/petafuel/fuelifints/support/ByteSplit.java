package net.petafuel.fuelifints.support;

import net.petafuel.fuelifints.exceptions.HBCISyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Trennt byte[] anhand von vorgegebenen Trennzeichen auf.
 */
public class ByteSplit {

    private static final Logger LOG = LogManager.getLogger(ByteSplit.class);

    //escape character (?) as hex value
    private static final byte ESCAPE = 0x3F;
    //binary character (@) as hex value
    private static final byte BINARY = 0x40;
    //segment end character (') as hex value
    private static final byte SEGMENT_END = 0x27;
    //DEG end character (+) as hex value
    private static final byte DEG_END = 0x2B;
    //DEG end character (:) as hex value
    private static final byte DE_END = 0x3A;
    //Default storage for a segment,  will grow automatically
    public static final int SEGMENT_INIT_SIZE = 32;


    public static final int MODE_SEGMENT = 0;
    public static final int MODE_DEG = 1;
    public static final int MODE_DE = 2;


    public static List<byte[]> split(byte[] input, int mode) throws HBCISyntaxException {
        try {
            if (input.length <= 0) {
                return null;
            }
            List<byte[]> segments = new ArrayList<byte[]>();

            int index = 0;

            byte currentByte = input[index++];

            //this will be doubled
            byte[] currentSegment = new byte[SEGMENT_INIT_SIZE];
            int segmentIndex = 0;

            while (index <= input.length) {
                //every character here will be stored in the current segment
                currentSegment = addToSegment(currentSegment, segmentIndex++, currentByte);

                switch (currentByte) {
                    case ESCAPE:
                        /**
                         * When the escape char is parsed first time this and the next character can be stored in the segment.
                         */
                        currentSegment = addToSegment(currentSegment, segmentIndex++, input[index]);
                        index++;
                        break;
                    case BINARY:
                        /**
                         *  Find the next binary character and store as much bytes as there are needed.
                         */
                        int startIndex = index;
                        int length = 0;
                        int endIndex = -1;
                        for (int i = startIndex; i < input.length; i++) {
                            currentSegment = addToSegment(currentSegment, segmentIndex++, input[i]);
                            if (input[i] != BINARY) {
                                //48 is the numeric value of 0
                                length = length * 10 + (input[i] - 48);
                            } else {
                                endIndex = i;
                                break;
                            }
                        }
                        //LOG.info("Length: "+length);

                        if (endIndex == -1) {
                            LOG.error("expected @ in binary block but was not found.");
                            throw new RuntimeException("Input String incorrect");
                        }

                        currentSegment = addToSegment(currentSegment, segmentIndex, input, endIndex + 1, length);
                        segmentIndex += length;
                        index = endIndex + 1 + length;
                        if (index < input.length)
                            currentByte = input[index];
                        index++;

                        continue;
                    case SEGMENT_END:
                        byte[] cuttedSegment = new byte[segmentIndex - 1];
                        System.arraycopy(currentSegment, 0, cuttedSegment, 0, cuttedSegment.length);

                        segments.add(cuttedSegment);

                        currentSegment = new byte[SEGMENT_INIT_SIZE];
                        segmentIndex = 0;
                        break;
                    case DEG_END:
                        if (mode == MODE_DEG) {
                            byte[] cuttedDEG = new byte[segmentIndex - 1];
                            System.arraycopy(currentSegment, 0, cuttedDEG, 0, cuttedDEG.length);

                            segments.add(cuttedDEG);

                            currentSegment = new byte[SEGMENT_INIT_SIZE];
                            segmentIndex = 0;
                        }
                        break;
                    case DE_END:
                        if (mode == MODE_DE) {
                            byte[] cuttedDE = new byte[segmentIndex - 1];
                            System.arraycopy(currentSegment, 0, cuttedDE, 0, cuttedDE.length);

                            segments.add(cuttedDE);

                            currentSegment = new byte[SEGMENT_INIT_SIZE];
                            segmentIndex = 0;
                        }
                        break;
                }

                if (index < input.length) {
                    currentByte = input[index];
                }
                index++;
            }
            if (mode == MODE_DEG || mode == MODE_DE) {
                byte[] cuttedDEG = new byte[segmentIndex];
                System.arraycopy(currentSegment, 0, cuttedDEG, 0, cuttedDEG.length);

                segments.add(cuttedDEG);

                //currentSegment = new byte[SEGMENT_INIT_SIZE];
                //segmentIndex = 0;
            }
            return segments;
        } catch (Exception ex) {
            throw new HBCISyntaxException(ex);
        }
    }


    /**
     * Fast Array doubling.
     *
     * @param input the input array.
     * @return byte array with double size.
     */
    private static byte[] doubleSizeAndCopy(byte[] input) {
        byte[] result = new byte[input.length << 2];
        System.arraycopy(input, 0, result, 0, input.length);
        return result;
    }

    /**
     * Add a new byte to the current segment.
     *
     * @param segment   the segment.
     * @param index     index in the byte array.
     * @param character the character to be added as byte.
     * @return the segment as byte array.
     */
    private static byte[] addToSegment(byte[] segment, int index, byte character) {
        if (index >= segment.length) {
            segment = doubleSizeAndCopy(segment);
        }
        segment[index] = character;
        return segment;
    }

    /**
     * Add a new bytes to the current segment.
     *
     * @param segment    the current segment.
     * @param offset     the offset in segment byte array.
     * @param characters the characters to be added as byte array.
     * @param start      start offset in characters array.
     * @param length     the number of bytes which should be copied.
     * @return the segments byte array.
     */
    private static byte[] addToSegment(byte[] segment, int offset, byte[] characters, int start, int length) {
        while (offset + length >= segment.length) {
            segment = doubleSizeAndCopy(segment);
        }
        System.arraycopy(characters, start, segment, offset, length);
        return segment;
    }
}
