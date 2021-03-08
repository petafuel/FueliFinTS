package net.petafuel.fuelifints.support;

public class StringSplit {

    private static char escapeChar = '?';

    /**
     * Funktioniert im Prinzip wie String.split, aber
     * trennt einen String bei einem Trennzeichen,
     * wenn vor dem Trennzeichen nicht der EscapeCharacter steht
     *
     * @param splitString String, der geteilt werden soll
     * @param trennzeichen char, bei dem geteilt werden soll
     * @return Stringarray mit den Substrings
     */
    public static String[] split(String splitString, char trennzeichen) {
        StringList stringList = new StringList();
        if (splitString!=null) {
            char[] splitArray = splitString.toCharArray();
            int start = 0;
            int i;
            for (i = 0; i<splitArray.length; i++) {
                if (splitArray[i]==escapeChar) i++; //nächsten Charakter bei der Trennung überspringen
                else if (splitArray[i]==trennzeichen) { //Trennzeichen gefunden, Substring zur Liste hinzufügen
                    if (start == i) stringList.add("");
                    else stringList.add(splitString.substring(start,i));

                    start = i+1;
                }
            }
            if (start!=i) stringList.add(splitString.substring(start,i));
        }
        return stringList.getArray();
    }

}
