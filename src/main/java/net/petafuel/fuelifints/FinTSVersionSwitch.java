package net.petafuel.fuelifints;

import net.petafuel.fuelifints.communication.FinTSCommunicationHandler;
import net.petafuel.fuelifints.protocol.FinTSPayload;
import net.petafuel.fuelifints.protocol.fints3.FinTS3Controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class FinTSVersionSwitch {

    public static enum FinTSVersion {
        FINTS_VERSION_3_0,
        FINTS_VERSION_4_0,
        HBCI_VERSION_2_2,
        FINTS_ERROR
    }

    public static IFinTSController controller;   //for testing purposes

    public static boolean selectController(int taskId, byte[] request, FinTSCommunicationHandler.CommunicationChannel channel) throws UnsupportedEncodingException {
        FinTSPayload payload = new FinTSPayload(request, taskId);
        switch (checkFinTSVersion(request)) {
            case FINTS_VERSION_3_0:
                if (controller == null)
                    FinTS3Controller.getInstance().newRequest(payload, channel);
                else
                    controller.newRequest(payload, channel);
                break;
            case FINTS_VERSION_4_0:
                //FinTS 4 wird noch nicht unterstützt!
                //break;
            case HBCI_VERSION_2_2:
                FinTS3Controller.getInstance().replyWithError(taskId, generateVersionNotSupportedError());
                return false;
        }
        return true;
    }

    private static FinTSVersion checkFinTSVersion(byte[] request) {
        String version = new String(request, 23, 3, StandardCharsets.ISO_8859_1);
        switch (version) {
            case "300":
                return FinTSVersion.FINTS_VERSION_3_0;
            case "220":
                return FinTSVersion.HBCI_VERSION_2_2;
            default:
                return FinTSVersion.FINTS_ERROR;
        }
    }

    private static String generateVersionNotSupportedError() {
        String defaultBankcode = "12345678";
        String defaultBankname = "FueliFinTS Testbank";
        Properties properties = new Properties();
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("config/fuelifints.properties"));
            properties.load(bis);
            bis.close();
            defaultBankcode = properties.getProperty("bankcode");
            defaultBankname = properties.getProperty("bankname");
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }


        return "HNHBK:1:3+000000000194+220+0+1'" +
                "HIRMG:2:2+9010::HBCI Version wird nicht unterstützt. Bitte aktualisieren Sie Ihr Kundenprodukt'" +
                "HIBPA:3:2:4+1+280:" + defaultBankcode + "+" + defaultBankname + "+0+1+300'" +
                "HNHBS:4:1+1'";
    }
}
