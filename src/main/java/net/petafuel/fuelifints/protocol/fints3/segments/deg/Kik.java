package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;

/**
 * Description:
 * <p/>
 * <p/>     280:BLZ
 * <p/>
 */
public class Kik extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1,
                    length = -3)})
    String laenderkennzeichen;

    @Element(
            description = {@ElementDescription(number = 2,
                    status = ElementDescription.StatusCode.O,
                    length = -30)})
    @an
    private String kreditinstitutscode;

    public Kik(byte[] degString) {
        super(degString);
    }

    @Override
    public String toString() {
        return "Kik{" +
                "laenderkennzeichen='" + laenderkennzeichen + '\'' +
                ", kreditinstitutscode='" + kreditinstitutscode + '\'' +
                '}';
    }

    public String getKreditinstitutscode() {
        return kreditinstitutscode;
    }

    public String getLaenderkennzeichen() {
        return laenderkennzeichen;
    }

    public void setLaenderkennzeichen(String laenderkennzeichen) {
        this.laenderkennzeichen = laenderkennzeichen;
    }

    public void setKreditinstitutscode(String kreditinstitutscode) {
        this.kreditinstitutscode = kreditinstitutscode;
    }
}
