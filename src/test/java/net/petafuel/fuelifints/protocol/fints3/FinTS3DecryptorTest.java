package net.petafuel.fuelifints.protocol.fints3;

import net.petafuel.fuelifints.dataaccess.DummyAccessFacade;
import net.petafuel.fuelifints.exceptions.HBCIValidationException;
import net.petafuel.fuelifints.protocol.FinTSPayload;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;

public class FinTS3DecryptorTest {



    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Before
    public void before() {
        //Remove existing FinTS3Controller instance:
        FinTS3Controller.removeInstance();
        DummyAccessFacade dummyAccessFacade = new DummyAccessFacade(null);
        dummyAccessFacade.clearUserKeys();
    }

    @After
    public void after() {
        //DummyAccessFacade dummyAccessFacade = new DummyAccessFacade();
        //dummyAccessFacade.clearDB();
    }

    @Test(expected = HBCIValidationException.class)
    public void testReferenceMessageSet() throws Exception {
        String base64 = "***REMOVED***";

        byte[] message = Base64.decode(base64);
        FinTSPayload payload = new FinTSPayload(message, 0);
        FinTS3Decryptor finTS3Decryptor = new FinTS3Decryptor();
        finTS3Decryptor.setPayload(payload);
        try {
            finTS3Decryptor.run();
        } catch (RuntimeException ex) {
            if (ex.getCause().getClass() == HBCIValidationException.class) {
                throw (Exception) ex.getCause();
            }
        }
    }

    @Test(expected = HBCIValidationException.class)
    public void testInvalidMessageSize() throws Throwable {
        String invalidMessageSizeString = "***REMOVED***";
        byte[] message = invalidMessageSizeString.getBytes("ISO-8859-1");
        FinTSPayload finTSPayload = new FinTSPayload(message, 0);
        FinTS3Decryptor finTS3Decryptor = new FinTS3Decryptor();
        finTS3Decryptor.setPayload(finTSPayload);
        try {
            finTS3Decryptor.run();
        } catch (RuntimeException ex) {
            if (ex.getCause().getClass() == HBCIValidationException.class) {
                throw ex.getCause();
            }
        }
    }

    @Test(expected = HBCIValidationException.class)
    public void testInvalidMessageNumber() throws Throwable {
        /**
         * Nachrichtennummer in HNHBK und HNHBS müssen identisch sein.
         */
        String invalidMessageSizeString = "***REMOVED***";
        byte[] message = invalidMessageSizeString.getBytes("ISO-8859-1");
        FinTSPayload finTSPayload = new FinTSPayload(message, 0);
        FinTS3Decryptor finTS3Decryptor = new FinTS3Decryptor();
        finTS3Decryptor.setPayload(finTSPayload);
        try {
            finTS3Decryptor.run();
        } catch (RuntimeException ex) {
            if (ex.getCause().getClass() == HBCIValidationException.class) {
                throw ex.getCause();
            }
        }
    }

    @Test
    public void testAnonymeAnfrage() throws UnsupportedEncodingException {
        String anonym = "***REMOVED***";
        byte[] message = anonym.getBytes("ISO-8859-1");
        FinTSPayload finTSPayload = new FinTSPayload(message, 0);
        FinTS3Decryptor finTS3Decryptor = new FinTS3Decryptor();
        finTS3Decryptor.setPayload(finTSPayload);
        finTS3Decryptor.run();
    }

    @Test
    @Ignore
    public void testPinTanDialogInit() throws UnsupportedEncodingException {
        /*
        String pinTan = "***REMOVED***";
        byte[] message = pinTan.getBytes("ISO-8859-1");
        FinTSPayload payload = new FinTSPayload(message, 0);
        FinTS3Decryptor finTS3Decryptor = new FinTS3Decryptor();
        finTS3Decryptor.setPayload(payload);
        finTS3Decryptor.run();  */
    }

    @Test
    @Ignore
    public void testRDH10DialogInit() throws GeneralSecurityException, UnsupportedEncodingException, InterruptedException {
        /*
        KeyManager keyManager = KeyManager.getInstance("DEBUG");
        PublicKey cipherPubKey = keyManager.getPublicKey(SecurityMethod.RDH_10, "V");
        byte[] toSign = ("***REMOVED***").getBytes("ISO-8859-1");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        int bits = 4096;
        keyPairGenerator.initialize(bits, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey userSigningPrivateKey = keyPair.getPrivate();
        PublicKey userSigningPublicKey = keyPair.getPublic();

        keyManager.addUserPublicKey("***REMOVED***", SecurityMethod.RDH_10, "S", 0, 0, userSigningPublicKey);

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(userSigningPrivateKey);
        signature.update(toSign);

        //byte[] signatureByes = new byte[]{1,2,3,4};
        byte[] signatureByes = signature.sign();
        LOG.debug("SignatureBytes:\n " + new String(signatureByes, "ISO-8859-1"));

        Signature verify = Signature.getInstance("SHA256withRSA");
        verify.initVerify(userSigningPublicKey);
        verify.update(toSign);
        assertTrue(verify.verify(signatureByes));

        byte[] hnshaBegin = ("HNSHA:5:2+654321+@" + signatureByes.length + "@").getBytes("ISO-8859-1");
        byte[] hnshaEnd = "'".getBytes("ISO-8859-1");
        int hnshaLength = hnshaBegin.length + signatureByes.length + hnshaEnd.length;
        byte[] hnsha = new byte[hnshaLength];

        int offset = 0;
        System.arraycopy(hnshaBegin, 0, hnsha, offset, hnshaBegin.length);
        offset += hnshaBegin.length;
        System.arraycopy(signatureByes, 0, hnsha, offset, signatureByes.length);
        offset += signatureByes.length;
        System.arraycopy(hnshaEnd, 0, hnsha, offset, hnshaEnd.length);

        byte[] data = new byte[toSign.length + hnsha.length];
        offset = 0;
        System.arraycopy(toSign, 0, data, offset, toSign.length);
        offset += toSign.length;
        System.arraycopy(hnsha, 0, data, offset, hnsha.length);

        LOG.debug("SegmentsToEncrypt: " + new String(data, "ISO-8859-1"));

        TwoKeyTripleDESCryptography twoKeyTripleDESCryptography = new TwoKeyTripleDESCryptography();
        byte[] tripleDesKey = twoKeyTripleDESCryptography.generatePassword();
        Cipher twoKeyTripleDesCipher = twoKeyTripleDESCryptography.createCipher(tripleDesKey, Cipher.ENCRYPT_MODE);
        byte[] encryptedMessage = twoKeyTripleDesCipher.doFinal(data);

        byte[] decryptedMessage = twoKeyTripleDESCryptography.createCipher(tripleDesKey, Cipher.DECRYPT_MODE).doFinal(encryptedMessage);
        assertTrue(Arrays.equals(data, decryptedMessage));

        Cipher rsaCipher = Cipher.getInstance("RSA/NONE/NOPADDING");
        rsaCipher.init(Cipher.ENCRYPT_MODE, cipherPubKey);

        byte[] encryptedKey = rsaCipher.doFinal(tripleDesKey);
        LOG.info("EncryptedKeyBytes length: " + encryptedKey.length);
        int length = 0;
        byte[] hnvskBegin = ("***REMOVED***" + encryptedKey.length + "@").getBytes("ISO-8859-1");
        byte[] hnvskEnd = ("***REMOVED***").getBytes("ISO-8859-1");
        length += hnvskBegin.length + hnvskEnd.length + encryptedKey.length;
        byte[] hnvskBytes = new byte[length];
        offset = 0;
        System.arraycopy(hnvskBegin, 0, hnvskBytes, offset, hnvskBegin.length);
        offset += hnvskBegin.length;
        System.arraycopy(encryptedKey, 0, hnvskBytes, offset, encryptedKey.length);
        offset += encryptedKey.length;
        System.arraycopy(hnvskEnd, 0, hnvskBytes, offset, hnvskEnd.length);


        //String hnvsdString = "***REMOVED***"+encryptedMessage.length+"@"+new String(encryptedMessage,"ISO-8859-1")+"'";
        byte[] hnvsdBegin = ("***REMOVED***" + encryptedMessage.length + "@").getBytes("ISO-8859-1");
        byte[] hnvsdEnd = ("'").getBytes("ISO-8859-1");
        length = hnvsdBegin.length + hnvsdEnd.length + encryptedMessage.length;
        byte[] hnvsdBytes = new byte[length];
        LOG.info("HNVSD length: " + length + " encryptedMessageLength: " + encryptedMessage.length + " hnvsdbeginLength: " + hnvsdBegin.length);
        offset = 0;
        System.arraycopy(hnvsdBegin, 0, hnvsdBytes, offset, hnvsdBegin.length);
        offset += hnvsdBegin.length;
        System.arraycopy(encryptedMessage, 0, hnvsdBytes, offset, encryptedMessage.length);
        offset += encryptedMessage.length;
        System.arraycopy(hnvsdEnd, 0, hnvsdBytes, offset, hnvsdEnd.length);

        byte[] hnhbk = "***REMOVED***".getBytes("ISO-8859-1");
        byte[] hnhbs = "***REMOVED***".getBytes("ISO-8859-1");

        byte[] rdh10 = new byte[hnhbk.length + hnvskBytes.length + hnvsdBytes.length + hnhbs.length];

        LOG.debug("Messagesize: " + String.format("%012d", rdh10.length));
        offset = 0;
        System.arraycopy(hnhbk, 0, rdh10, offset, hnhbk.length);
        offset = 10;
        System.arraycopy(String.format("%012d", rdh10.length).getBytes("ISO-8859-1"), 0, rdh10, offset, 12);
        offset = 0 + hnhbk.length;
        System.arraycopy(hnvskBytes, 0, rdh10, offset, hnvskBytes.length);
        offset += hnvskBytes.length;
        System.arraycopy(hnvsdBytes, 0, rdh10, offset, hnvsdBytes.length);
        offset += hnvsdBytes.length;
        System.arraycopy(hnhbs, 0, rdh10, offset, hnhbs.length);


        LOG.debug("RDH-Message: " + new String(rdh10, "ISO-8859-1"));

        RDH10Cryptography rdh10Decryptor = new RDH10Cryptography();
        rdh10Decryptor.decrypt(encryptedMessage, encryptedKey, "DEBUG");

        FinTSPayload finTSPayload = new FinTSPayload(rdh10, 0);
        FinTS3Decryptor finTS3Decryptor = new FinTS3Decryptor();
        finTS3Decryptor.setPayload(finTSPayload);

        finTS3Decryptor.run();


        Thread.sleep(2000);

        */
    }
}
