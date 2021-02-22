package net.petafuel.fuelifints.support;

public class LogHelper {

    public static String removeSignatureInformation(String toLog) {

        try {
            if (toLog.contains("HNSHA")) {
                String hnsha = toLog.substring(toLog.indexOf("HNSHA"));
                hnsha = hnsha.substring(0, hnsha.indexOf("'"));
                String pinTan = hnsha.substring(hnsha.lastIndexOf("+") + 1);
                toLog = toLog.replace(pinTan, "*****");
            }
            return toLog;
        }
        catch (Exception ignored) {

        }
        return toLog;
    }
}