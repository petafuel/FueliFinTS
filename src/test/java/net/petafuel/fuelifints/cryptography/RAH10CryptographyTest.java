package net.petafuel.fuelifints.cryptography;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.security.Security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RAH10CryptographyTest {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    public void testDecrypt() throws Exception {
        /*
        RAH10Cryptography rah10Cryptography = new RAH10Cryptography();
        byte[] data = "Hello World!".getBytes("ISO-8859-1");


        KeyManager keyManager = KeyManager.getInstance("DEBUG");
        PublicKey bankPublicKey = keyManager.getPublicKey(SecurityMethod.RAH_10, "V");

        byte[] symmetricKey = rah10Cryptography.generateKey();

        byte[] encrypted = rah10Cryptography.encryptMessage(data, symmetricKey, "DEBUG");


        Cipher rsaCipher = Cipher.getInstance("RSA/NONE/NOPADDING");
        rsaCipher.init(Cipher.ENCRYPT_MODE, bankPublicKey);

        byte[] encryptedKey = rsaCipher.doFinal(symmetricKey);

        byte[] decrypted = rah10Cryptography.decrypt(encrypted, encryptedKey, "DEBUG");

        assertNotNull(decrypted);
        assertArrayEquals(data, decrypted);
        */
    }

    @Test
    public void testEncryptMessage() throws Exception {
        /*
        RAH10Cryptography rah10Cryptography = new RAH10Cryptography();
        byte[] generatedKey = rah10Cryptography.generateKey();
        assertNotNull(generatedKey);
        assertTrue(generatedKey.length > 0);
        byte[] message = "Hello World!".getBytes("ISO-8859-1");
        byte[] encrypted = rah10Cryptography.encryptMessage(message, generatedKey, "DEBUG");
        assertNotNull(encrypted);
        assertTrue(encrypted.length > 0);
        */
    }

    @Test
    public void testGenerateKey() throws Exception {
        RAH10Cryptography rah10Cryptography = new RAH10Cryptography();
        byte[] generatedKey = rah10Cryptography.generateKey();
        assertNotNull(generatedKey);
        assertTrue(generatedKey.length > 0);
    }
}
