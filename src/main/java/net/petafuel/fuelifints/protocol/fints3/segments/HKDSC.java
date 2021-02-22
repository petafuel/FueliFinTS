package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;

/**
 * Name:  Terminierte SEPA-COR1-Einzellastschrift einreichen
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKDSC
 * Bezugssegment:  -
 * Version:  1
 * Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKDSC extends HKDSE {
    public HKDSC(byte[] message) {
        super(message);
    }
}
