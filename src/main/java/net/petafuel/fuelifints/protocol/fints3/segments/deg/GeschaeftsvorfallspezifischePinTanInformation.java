package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.an;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.jn;

/**
 * Geschäftsvorfallspezifische PIN/TAN-Informationen
 * Eine  DEG  dieses  Typs  enthält  für  genau  einen  Geschäftsvorfall  PIN/TAN-
 * relevante Informationen. Ist für einen Geschäftsvorfall eine zugehörige DEG
 * hinterlegt,  kann  das  Kundenprodukt  diesen  Geschäftsvorfall  über  das
 * PIN/TAN-Verfahren absichern, andernfalls ist dies nicht erlaubt.
 * Hierdurch wird nicht festgelegt, ob und wie oft ein Geschäftsvorfall zu signie-
 * ren ist. Dies wird weiterhin über die BPD und UPD angegeben.
 * Werden mehr Signaturen eingestellt als in BPD und UPD gefordert, so sind
 * diese alle gemäß der Einstellungen im HIPINS-Segment zu bilden.
 * Werden  in  BPD  und  UPD  keine  Signaturen gefordert, können  diese  selbst
 * dann weggelassen werden, wenn für den betreffenden Geschäftsvorfall eine
 * TAN erforderlich ist.
 * Im Feld „Segmentkennung“ ist die Kennung des Auftragssegments des Ge-
 * schäftsvorfalls  anzugeben,  auf  den  sich  die  PIN/TAN-Informationen  bezie-
 * hen.
 */
public class GeschaeftsvorfallspezifischePinTanInformation extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = -6)})
    @an
    private String segmentkennung;

    @Element(
            description = {@ElementDescription(number = 2, length = 1)})
    @jn
    private String tanErforderlich;

    public GeschaeftsvorfallspezifischePinTanInformation(String segmentkennung, boolean tanErforderlich) {
        super(new byte[0]);
        this.segmentkennung = segmentkennung;
        this.tanErforderlich = tanErforderlich ? "J" : "N";
    }

    public String getSegmentkennung() {
        return segmentkennung;
    }

    public void setSegmentkennung(String segmentkennung) {
        this.segmentkennung = segmentkennung;
    }

    public String getTanErforderlich() {
        return tanErforderlich;
    }

    public void setTanErforderlich(String tanErforderlich) {
        this.tanErforderlich = tanErforderlich;
    }
}
