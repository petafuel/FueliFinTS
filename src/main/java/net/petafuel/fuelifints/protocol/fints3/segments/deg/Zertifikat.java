package net.petafuel.fuelifints.protocol.fints3.segments.deg;

import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.bin;
import net.petafuel.fuelifints.protocol.fints3.validator.validators.code;

import java.util.Arrays;

/**
 * Zertifikat
 * Zertifikat eines öffentlichen Schlüssels.
 * Da Zertifikate Informationen beinhalten, die auch in den HBCI-Formaten ent-
 * halten sind (z.B. Zertifikatsreferenz respektive Schlüsselnamen), können Da-
 * ten  redundant  vorkommen.  Diese  müssen  dann  auf  Konsistenz  überprüft
 * werden. Bei Unstimmigkeiten hat das Zertifikat Vorrang.
 */
public class Zertifikat extends DatenElementGruppe {

    @Element(
            description = {@ElementDescription(number = 1, length = 1)})
    @code(restrictions = {"1", "2", "3"})
    private String zertifikatstyp;

    @Element(
            description = {@ElementDescription(number = 2, length = -4096)})
    @bin
    private byte[] zertifikatsinhalt;

    public Zertifikat(byte[] degString) {
        super(degString);
    }

    @Override
    public String toString() {
        return "Zertifikat{" +
                "zertifikatstyp='" + zertifikatstyp + '\'' +
                ", zertifikatsinhalt=" + Arrays.toString(zertifikatsinhalt) +
                '}';
    }
}
