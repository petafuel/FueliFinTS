package net.petafuel.fuelifints.protocol.fints3.segments.acknowledgement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


/**
 * Gibt zu einem Acknowledgement Code die passende Meldung zurück.
 *
 * Dabei werden ACK Codes und Meldungen aus einer XML Datei ausgelesen
 *
 */
public class AckString {

    private static final Logger LOG = LogManager.getLogger(AckString.class);

    public static String getAckString(String ackCode) {

        return getMessageFromXml(ackCode);
    }

    public static String getMessageText(String ackCode) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            // File file = new File("src/test/resources/xml/acknowledgementcodes.xml");



            Document doc = factory.newDocumentBuilder().parse(AckString.class.getResourceAsStream("/xml/acknowledgementcodes.xml"));

            NodeList codes = doc.getElementsByTagName("ack_code");
            for (int i = 0; i < codes.getLength(); i++) {
                NamedNodeMap parameter = codes.item(i).getAttributes();

                if (parameter.item(1).getNodeValue().equals(ackCode)) {
                    return parameter.item(2).getNodeValue();
                }

            }
        } catch (ParserConfigurationException e) {
            LOG.error("ParserConfiguration Fehler", e);
        } catch (SAXException e) {
            LOG.error("SAX Fehler", e);
        } catch (IOException e) {
            LOG.error("IO-Fehler", e);
        }
        return null;
    }

    private static String getMessageFromXml(String code) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            File file = new File("src/test/resources/xml/acknowledgementcodes.xml");

            Document doc = factory.newDocumentBuilder().parse(file);

            NodeList codes = doc.getElementsByTagName("ack_code");
            for (int i = 0; i < codes.getLength(); i++) {
                NamedNodeMap parameter = codes.item(i).getAttributes();

                if (parameter.item(1).getNodeValue().equals(code)) {
                    return code + "::" + parameter.item(2).getNodeValue();
                }

            }
        } catch (ParserConfigurationException e) {
            LOG.error("ParserConfiguration Fehler", e);
        } catch (SAXException e) {
            LOG.error("SAX Fehler", e);
        } catch (IOException e) {
            LOG.error("IO-Fehler", e);
        }
        return null;
    }

}
