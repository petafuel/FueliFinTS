package net.petafuel.fuelifints.cryptography;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class TwoKeyTripleDESCryptography {

    /**
     * Mit dieser Methode legt jede erbende Unterklasse fest,
     * mit welchen Verfahren der geheime Schluessel geniert werden soll
     *
     * @param password Passwort mit dem der geheime Schluessel erzeugt wird
     * @return Geheimer Schluessel
     * @throws java.security.GeneralSecurityException
     *
     */
    public SecretKey createSecretKey(byte[] password) throws GeneralSecurityException {
        return new SecretKeySpec(generate2Key(password), "DESede");
    }

    private byte[] generate2Key(byte[] password) throws GeneralSecurityException {
        byte[] startKey = password;
        if (startKey.length != 16) {
            throw new GeneralSecurityException("Ungültige Länge für das Passwort. Passwort muss 16 Zeichen haben!");
        }

        byte[] finalKey = new byte[24];
        for (int i = 0; i<finalKey.length; i++) {
            finalKey[i] = getOddParity(startKey[i%startKey.length]);
        }

        return finalKey.clone();
    }

    /**
     * Generiert eine ungerade ParitÃ¤t fÃ¼+r das Ã¼bergebene byte
     * Dabei wird das LSB verworfen und durch ein ParitÃ¤tsbit ersetzt
     *
     * @param b byte, dem eine ungerade paritÃ¤t gegeben werden soll
     * @return byte mit ungerader paritÃ¤t
     */
    private byte getOddParity(byte b) {
        b = (byte) ((b >>> 1) << 1);
        return (byte) (b | (checkOddParity(b) ? 0:1));
    }

    /**
     * PrÃ¼ft, ob die ParitÃ¤t des Ã¼bergebenen bytes ungerade ist
     * (== ist die Anzahl der gesetzten bits gerade)
     * (== ergibt ein xor unter jedem bit 1)
     *
     * @param b byte, das zu prÃ¼fen ist
     * @return true bei ungerader ParitÃ¤t, ansonsten false
     */
    private boolean checkOddParity(byte b) {
        return (((b ^
                (b>>>1) ^
                (b>>>2) ^
                (b>>>3) ^
                (b>>>4) ^
                (b>>>5) ^
                (b>>>6) ^
                (b>>>7)) & 1)==1);
    }

    public byte[] generatePassword() {
        //siehe B3.1.1, FinTS 3.0 Security HBCI
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[16];
        random.nextBytes(keyBytes);
        //starmoney key fix
        if (keyBytes[0] == 0) {
            keyBytes[0] = 1;
        }
        return keyBytes;
    }

    public Cipher createCipher(byte[] password, int mode) throws GeneralSecurityException {
        if (password == null) {
            throw new IllegalArgumentException("Parameter password must not be null.");
        }
        SecretKey secretKey = createSecretKey(password);

        //Initialisierungsvektor:
        //Xâ€™00 00 00 00 00 00 00 00â€™
        //siehe B2.2.2, FinTS 3.0 Security HBCI
        byte[] iv = new byte[] {
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
        };
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("DESede/CBC/ISO10126Padding");
        cipher.init(mode, secretKey, ivSpec);
        return cipher;
    }

}

