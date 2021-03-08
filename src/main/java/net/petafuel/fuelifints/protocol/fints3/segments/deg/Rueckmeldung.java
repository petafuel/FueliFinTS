package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.segments.acknowledgement.AckString;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.dig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Rückmeldung
 * Rückmeldung des Kreditsinstituts.
 */
public class Rueckmeldung extends DatenElementGruppe {

    private static final Logger LOG = LogManager.getLogger(Rueckmeldung.class);

    @Element(
            description = {@ElementDescription(number = 1, length = 4)})
    @dig
    private String rueckmeldungscode;

    @Element(
            description = {@ElementDescription(number = 2, status = ElementDescription.StatusCode.C, length = -7)})
    @an
    private String bezugsdatenelement;

    @Element(
            description = {@ElementDescription(number = 3, length = -80)})
    @an
    private String rueckmeldungstext;

    @Element(
            description = {@ElementDescription(number = 4, status = ElementDescription.StatusCode.O, length = -35)})
    private List<String> rueckmeldungsparamter;

    public static Rueckmeldung getRueckmeldung(String code) {
        Rueckmeldung rueckmeldung = new Rueckmeldung(new byte[0]);
        rueckmeldung.setRueckmeldungscode(code);
        String rueckmeldungsText = AckString.getMessageText(code);
        LOG.debug("RückmeldungsText from AckString: {}", rueckmeldungsText);
        rueckmeldung.setRueckmeldungstext(rueckmeldungsText);
        return rueckmeldung;
    }

    public Rueckmeldung(byte[] bytes) {
        super(bytes);
    }

    public String getRueckmeldungscode() {
        return rueckmeldungscode;
    }

    public void setRueckmeldungscode(String rueckmeldungscode) {
        this.rueckmeldungscode = rueckmeldungscode;
    }

    public String getBezugsdatenelement() {
        return bezugsdatenelement;
    }

    public void setBezugsdatenelement(String bezugsdatenelement) {
        this.bezugsdatenelement = bezugsdatenelement;
    }

    public String getRueckmeldungstext() {
        return rueckmeldungstext;
    }

    public void setRueckmeldungstext(String rueckmeldungstext) {
        this.rueckmeldungstext = rueckmeldungstext;
    }

    public List<String> getRueckmeldungsparamter() {
        return rueckmeldungsparamter;
    }

    public void setRueckmeldungsparamter(List<String> rueckmeldungsparamter) {
        this.rueckmeldungsparamter = rueckmeldungsparamter;
    }

    @Override
    public String toString() {
        return "Rueckmeldung{" +
                "rueckmeldungscode='" + rueckmeldungscode + '\'' +
                ", bezugsdatenelement='" + bezugsdatenelement + '\'' +
                ", rueckmeldungstext='" + rueckmeldungstext + '\'' +
                ", rueckmeldungsparamter='" + rueckmeldungsparamter + '\'' +
                '}';
    }
}
