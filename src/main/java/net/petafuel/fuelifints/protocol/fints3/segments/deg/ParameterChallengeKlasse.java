package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;

import java.util.List;

/**
 * Parameter Challenge-Klasse
 * Auftragsspezifische  Daten,  die  entsprechend  der  Challenge-Klasse  für  die
 * Verarbeitung im Institut benötigt werden.
 */
public class ParameterChallengeKlasse extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, status = ElementDescription.StatusCode.O, length = -999)})
    private List<String> challengeKlasseParameter;

    public ParameterChallengeKlasse(byte[] bytes) {
        super(bytes);
    }

    public List<String> getChallengeKlasseParameter() {
        return challengeKlasseParameter;
    }

    public void setChallengeKlasseParameter(List<String> challengeKlasseParameter) {
        this.challengeKlasseParameter = challengeKlasseParameter;
    }
}
