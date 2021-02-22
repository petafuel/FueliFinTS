package net.petafuel.fuelifints.protocol.fints3.segments;

import net.petafuel.fuelifints.protocol.fints3.annotations.Requires;

/**
 * Name: Terminierte SEPA-COR1-Sammellastschrift einreichen
 * Typ:  Segment
 * Segmentart:  Geschäftsvorfall
 * Kennung:  HKDMC
 * Bezugssegment:  -
 * Version:  1
 * Sender:  Kunde
 */
@Requires({Requires.Requirement.EXECUTION_ALLOWED,
        Requires.Requirement.KUNDENSYSTEM_ID,
        Requires.Requirement.USER_IDENTIFIED,
        Requires.Requirement.TAN})
public class HKDMC extends HKDME {
    public HKDMC(byte[] message) {
        super(message);
    }
}
