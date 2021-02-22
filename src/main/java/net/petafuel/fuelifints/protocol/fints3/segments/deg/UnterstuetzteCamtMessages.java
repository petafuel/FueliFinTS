package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;

import java.util.LinkedList;
import java.util.List;

/**
 * Unterstützte camt-messages
 * Jedes DE enthält eine URN einer camt Schema-Definition.
 * Wird verwendet, um die zur Verfügung stehenden camt Schema-Definitionen bekannt zu geben
 * und gibt bei Abfragen an, welche camt Schemata durch das Kundenprodukt unterstützt werden.
 * Diese müssen aus den vom Kredit- institut unterstützten in der BPD übermittelten camt
 * Schema-Definitionen ausgewählt werden.
 */
public class UnterstuetzteCamtMessages extends DatenElementGruppe {

    @Element(description = {@ElementDescription(number = 1, length = -99)})
    private List<String> camtDescriptor;

    public UnterstuetzteCamtMessages(byte[] bytes) {
        super(bytes);
    }

    public List<String> getCamtDescriptor() {
        return camtDescriptor;
    }

    public void setCamtDescriptor(List<String> camtDescriptor) {
        this.camtDescriptor = camtDescriptor;
    }

    public void addCamtDescriptor(String descriptorString) {
        if(camtDescriptor == null) {
            camtDescriptor = new LinkedList<>();
        }
        camtDescriptor.add(descriptorString);
    }
}
