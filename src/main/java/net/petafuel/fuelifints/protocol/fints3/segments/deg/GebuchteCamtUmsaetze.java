package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;

import java.util.LinkedList;
import java.util.List;

public class GebuchteCamtUmsaetze extends DatenElementGruppe {

    @Element(description = @ElementDescription(number = 1))
    private List<byte[]> camtUmsaetzeGebucht;

    public GebuchteCamtUmsaetze(byte[] bytes) {
        super(bytes);
    }

    public List<byte[]> getCamtUmsaetzeGebucht() {
        return camtUmsaetzeGebucht;
    }

    public void setCamtUmsaetzeGebucht(List<byte[]> camtUmsaetzeGebucht) {
        this.camtUmsaetzeGebucht = camtUmsaetzeGebucht;
    }

    public void addCamtUmsatz(byte[] camtUmsatz) {
        if(camtUmsaetzeGebucht == null) {
            camtUmsaetzeGebucht = new LinkedList<>();
        }
        camtUmsaetzeGebucht.add(camtUmsatz);
    }
}
