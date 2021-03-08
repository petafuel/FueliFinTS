package net.petafuel.fuelifints.cryptography;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class PinTanDecryptorTest {
    @Test
    public void testDecrypt() throws Exception {
        byte[] encrypted = "Hello World!".getBytes();
        PinTanCryptography pinTanDecryptor = new PinTanCryptography();
        byte[] decrypted = pinTanDecryptor.decrypt(encrypted,new byte[0],"DEBUG");
        assertNotNull(decrypted);
        assertEquals(encrypted,decrypted);
    }
}
