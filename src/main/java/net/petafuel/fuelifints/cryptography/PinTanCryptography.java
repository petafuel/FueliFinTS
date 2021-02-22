package net.petafuel.fuelifints.cryptography;

public class PinTanCryptography implements Cryptography {

    /**
     * Beim PIN/TAN Verfahren sind die Daten in HNVSD nicht verschlüsselt.
     * Gilt für HBCI 2.2+ , FinTS 3 - FinTS 4 muss geprüft werden.
     * @param encrypted
     * @param key
     * @return
     */
    @Override
    public byte[] decrypt(byte[] encrypted, byte[] key, String bankId) {
        return encrypted;
    }

    @Override
    public byte[] encryptMessage(byte[] message, byte[] key, String bankId) {
        return message;
    }

    @Override
    public byte[] encryptEncryptionKey(byte[] encryptionKey, String bankId, String userId) {
        return encryptionKey;
    }

    @Override
    public byte[] generateKey() {
        return new byte[]{0,0,0,0,0,0,0,0};
    }
}
