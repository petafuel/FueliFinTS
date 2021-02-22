package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;

import java.util.List;

/**
 * Parameter für Parameter-Segmente.
 */
public class Parameter extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1)})
    private List<String> parameter;

    public Parameter(byte[] bytes) {
        super(bytes);
    }

    public List<String> getParameter() {
        return parameter;
    }

    public void setParameter(List<String> parameter) {
        this.parameter = parameter;
    }
}
