package net.petafuel.fuelifints.protocol.fints3.segments.parameter;

import net.petafuel.fuelifints.dataaccess.dataobjects.ParameterDataObject;
import net.petafuel.fuelifints.exceptions.ElementParseException;
import net.petafuel.fuelifints.exceptions.HBCISyntaxException;
import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.segments.Segment;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Parameter;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;
import net.petafuel.fuelifints.support.ByteSplit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstrakte Klasse für Bankparameter Segmente.
 * <p/>
 * Diese Parametersegmente sind konstant, d.h. sie sind für alle Kunden einer Bank gleich.
 * Gleichzeitig können diese Segmente anonym angefragt werden, der Kunde muss sich nicht authentifizieren.
 * <p/>
 * (FinTS 3.0) Beispiele:
 * <p/>
 * HIBPA
 * HIKOM
 * HISHV
 * HISALS
 * HIKAZS
 * (...)
 */
public class ParameterSegment extends Segment {
    private static final Logger LOG = LogManager.getLogger(ParameterSegment.class);

    @Element(
            description = {@ElementDescription(number = 1)})
    private Segmentkopf segmentkopf;

    @Element(
            description = {@ElementDescription(number = 2, length = -3)})
    @num
    protected Integer maximaleAnzahlAuftraege;

    @Element(
            description = {@ElementDescription(number = 3, length = 1)})
    @num
    protected Integer anzahlSignaturenMindestens;


    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O, length = 0)})
    @code(restrictions = {"0", "1", "2", "3", "4"})
    protected String sicherheitsKlasse;

    @Element(
            description = {@ElementDescription(number = 5, status = ElementDescription.StatusCode.O)})
    protected Parameter parameter;

    public ParameterSegment(byte[] message) {
        super(message);
    }

    public ParameterSegment(ParameterDataObject paramData, int bezugsSegment) {
        super(new byte[0]);
        if (paramData != null) {
            /*
            this.segmentkopf = new Segmentkopf(
                    (paramData.getSegmentName() + ":0:" +
                            paramData.getSegmentVersion() + ":" +
                            bezugsSegment).getBytes()
            );
            */

            this.segmentkopf = Segmentkopf.Builder.newInstance().setSegmentKennung(paramData.getSegmentName()).setSegmentVersion(paramData.getSegmentVersion()).setBezugssegment(bezugsSegment).build();

            this.maximaleAnzahlAuftraege = paramData.getMaximaleAnzahlAuftraege();
            this.anzahlSignaturenMindestens = paramData.getAnzahlSignaturenMindestens();
            this.sicherheitsKlasse = paramData.getSicherheitsKlasse();
            LOG.debug("parameterData {}", paramData.getParameter());
            List<byte[]> splitted = null;
            try {
                splitted = ByteSplit.split(paramData.getParameter().getBytes(), ByteSplit.MODE_DE);
            } catch (HBCISyntaxException e) {
                // ignored here
            }
            List<String> params = new LinkedList<>();
            if (splitted != null) {
                for (byte[] temp : splitted) {
                    try {
                        params.add(new String(temp, "ISO-8859-1"));
                    } catch (UnsupportedEncodingException e) {
                        //ISO-8859-1 is supported
                    }
                }
                parameter = new Parameter(new byte[0]);
                parameter.setParameter(params);
            }
        }
    }

    @Override
    public void parseElement() throws ElementParseException {
        //nichts zu tun, Parametersegmente werden nur vom Kreditinstitut geschickt und müssen nicht geparsed werden
        throw new ElementParseException("Parametersegmente werden nur vom Kreditinstitut geschickt und können nicht geparsed werden");
    }


    @Override
    public byte[] getHbciEncoded() {
        byte[] paramBytes = super.getHbciEncoded();

        /*
         * In alten HBCI 220 Segmenten wurde keine Sicherheitsklasse berücksichtigt.
         * Weil insbesondere StarMoney aber trotz FinTS 300 Support noch 220 Segmente nutzt (HKSAL & HKKAZ),
         * müssen die entsprechenden Parameter noch HBCI 220 kompatibel sein.
         *
         * Wenn also die Sicherheitsklasse in einem Parametersegment nicht gesetzt wurde:
         * - muss es sich um ein HBCI 220 Segment handeln
         * - muss sie komplett ignoriert werden, ein Datenelement dafür ist nicht vorgesehen
         *
         * Das doppelte ++ als Datenelementtrennzeichen wird durch ein einfaches + ersetzt:
         *
         * Aus
         * ANZAHLSIGS+SICHERKLASSE(null)+PARAMETER
         * wird
         * ANZAHLSIGS+PARAMETER
         */
        if (sicherheitsKlasse == null || sicherheitsKlasse.equals(""))
            paramBytes = new String(paramBytes).replace("++", "+").getBytes();
        return paramBytes;
    }
}
