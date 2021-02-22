package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.num;

/**
 * Unterstützte Sicherheitsverfahren
 * Information  über  die  kreditinstitutsseitig  unterstützten  Sicherheitsverfahren.
 * Anhand  der  Kombination  der  beiden  Elemente  „Sicherheitsverfahren“  und
 * „Version“ wird das Sicherheitsprofil (z.B. RAH-7) bestimmt.
 */
public class UnterstuetzesSicherheitsverfahren extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1)})
    @an
    private String verfahren;

    @Element(
            description = {@ElementDescription(number = 2)})
    @num
    private Integer verfahrensVersion;

    public UnterstuetzesSicherheitsverfahren(byte[] bytes) {
        super(bytes);
    }

    public String getVerfahren() {
        return verfahren;
    }

    public void setVerfahren(String verfahren) {
        this.verfahren = verfahren;
    }

    public Integer getVerfahrensVersion() {
        return verfahrensVersion;
    }

    public void setVerfahrensVersion(Integer verfahrensVersion) {
        this.verfahrensVersion = verfahrensVersion;
    }
}
