package net.petafuel.fuelifints.cryptography;

public interface Cryptography {
    public byte[] decrypt(byte[] encrypted, byte[] key, String bankId);
    public byte[] encryptMessage(byte[] message, byte[] key, String bankId);
    public byte[] encryptEncryptionKey(byte[] encryptionKey, String bankId, String userId);
    public byte[] generateKey();
}
