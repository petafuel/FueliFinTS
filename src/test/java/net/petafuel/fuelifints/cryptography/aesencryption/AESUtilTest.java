package net.petafuel.fuelifints.cryptography.aesencryption;

import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class AESUtilTest {
    @Test
    public void aesEncrypt() throws Exception {
        byte[] base64test = ("123456".getBytes());

        assertEquals(AESUtil.aesEncrypt(base64test), "vskYPyQODcBF9n9sPFGGrw==");
    }

    @Test
    public void aesDecrypt() throws Exception {
        String base64test = "123456";
        assertEquals(new String(AESUtil.aesDecrypt("vskYPyQODcBF9n9sPFGGrw==")), base64test);
    }



}