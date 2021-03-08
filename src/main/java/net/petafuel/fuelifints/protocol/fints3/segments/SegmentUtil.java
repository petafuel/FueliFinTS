package net.petafuel.fuelifints.protocol.fints3.segments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * Hilfsklasse um den Umgang mit binären DE umzugehen.
 */
public class SegmentUtil {

    static final Logger LOG = LogManager.getLogger(SegmentUtil.class);

    /**
     * Liefert den Segment-Namen zu einem gegebenen byte[].
     *
     * @param segment die bytes die das Segment repräsentieren.
     * @return den Namen des Segments als String.
     */
    public static String getSegmentName(byte[] segment) {
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

    /**
     * Liefert die echten Binär-Daten eines binären Datenelements.
     * Beispiel: @3@123 liefert 123 zurück.
     *
     * @param binaryFormat das binäre DE als byte[].
     * @return die reinen Binär-Daten.
     */
    public static byte[] getBytes(byte[] binaryFormat) {
        byte[] result;
        int index = 1;
        for (; index < binaryFormat.length; index++) {
            if (binaryFormat[index] == 0x40) {
                index++;
                break;
            }
        }
        result = new byte[binaryFormat.length - index];
        System.arraycopy(binaryFormat, index, result, 0, result.length);
        return result;
    }

    public static byte[] wrapBinary(byte[] bytes) {
        if (bytes == null) {
            LOG.debug("input bytes was null in wrap binary");
            return null;
        }
        byte[] result = null;
        try {
            result = new byte[bytes.length + 2 + Integer.toString(bytes.length).length()];
            result[0] = '@';
            int i = 1;
            for (byte b : Integer.toString(bytes.length).getBytes("ISO-8859-1")) {
                result[i++] = b;
            }
            result[i] = '@';
            System.arraycopy(bytes, 0, result, i + 1, bytes.length);
        } catch (UnsupportedEncodingException es) {
            //ISO is supported
        }
        LOG.debug("wrap binary {}", new String(result));
        return result;
    }

    public static String ibanToAccountNr(String iban) {
        /*
         * DEpp bbbb bbbb kkkk kkkk kk
         *
         * Dabei bedeutet:
         *
         * DE           Länderkennzeichen für Deutschland
         * pp           zweistellige Prüfsumme mit Prüfziffern
         * bbbbbbbb     die 8-stellige deutsche Bankleitzahl
         * kkkkkkkkkk   die 10-stellige Kontonummer
         */
        if (iban != null) {
            if (iban.length() == 22 && iban.startsWith("DE")) {
                //gilt nur für deutsche ibans
                String account = iban.substring(12);
                while (account.startsWith("0"))
                    account = account.substring(1); //führende Nullen entfernen
                return account;
            }
        }
        return "";

    }

    public static String accountToIban(String accountNr, String bankCode) {
        /*
         * DEpp bbbb bbbb kkkk kkkk kk
         *
         * Dabei bedeutet:
         *
         * DE           Länderkennzeichen für Deutschland
         * pp           zweistellige Prüfsumme mit Prüfziffern
         * bbbbbbbb     die 8-stellige deutsche Bankleitzahl
         * kkkkkkkkkk   die 10-stellige Kontonummer
         */


        if (accountNr.length() < 10)
            accountNr = "0000000000".substring(accountNr.length()) + accountNr; //Kontonummer mit Nullen auf 10 Stellen auffüllen

        String bban = bankCode + accountNr; //Bankleitzahl + 10 stellige Kontonummer = BBAN
        String countryIdentifier = "131400";  // (4+9) + (5+9) + 00
        String checksum = (bban + countryIdentifier);

        //Einfach eine 22 stellige Zahl mod 97 nehmen ist leider nicht genau genug
        long checkDigit = 0;
        for (int i = 0; i < checksum.length(); i++) {
            int charValue = Character.getNumericValue(checksum.charAt(i));

            checkDigit = (charValue > 9 ? checkDigit * 100 : checkDigit * 10) + charValue;
            if (checkDigit > 999999999) {   //Berechnungen mit zu langen Zahlen führen zu Fehlern
                checkDigit = (checkDigit % 97);
            }
        }
        checkDigit = (checkDigit % 97); //Prüfumme Modulo 97
        checkDigit = 98 - checkDigit;
        return String.format("%s%02d%s", "DE", checkDigit, bban);
    }
}