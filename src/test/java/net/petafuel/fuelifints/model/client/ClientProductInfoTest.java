package net.petafuel.fuelifints.model.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

public class ClientProductInfoTest {

    private ClientProductInfo clientProductInfo;

    @Before
    public void setup() {
        this.clientProductInfo = new ClientProductInfo();

        Properties properties = new Properties();
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream("src/test/resources/test.properties"));
            properties.load(stream);
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        @SuppressWarnings("unchecked")
        Enumeration<String> enums = (Enumeration<String>) properties.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = properties.getProperty(key);
            System.setProperty(key, value);
        }
    }

    @Test
    public void goodConfig() {
        Assert.assertEquals("true", System.getProperty("productinfo.csv.check"));
        Assert.assertNotEquals("", System.getProperty("productinfo.csv.filepath"));
        Assert.assertNotEquals(null, System.getProperty("productinfo.csv.filepath"));
    }

    @Test
    public void validateClientProductInfo() {

        // Produktbezeichung fehlt
        Assert.assertFalse(clientProductInfo.validateClientProductInfo());

        // Produktbezeichung falsch
        clientProductInfo.setClientProductName("falsche Produktbezeichung");
        Assert.assertFalse(clientProductInfo.validateClientProductInfo());

        // Produktbezeichung richtig
        clientProductInfo.setClientProductName("Q519D9BWIB7GINUI");
        Assert.assertTrue(clientProductInfo.validateClientProductInfo());
    }
}