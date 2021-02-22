package net.petafuel.fuelifints.support;

import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.segments.HIRMS;
import net.petafuel.fuelifints.protocol.fints3.segments.HISYN;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class SegmentComparatorTest {
    @Test
    public void testCompare() throws Exception {
        HIRMS hirms = new HIRMS("HIRMS:4:2:5+0010::Auftrag entgegengenommen".getBytes());
        HIRMS hirms2 = new HIRMS("HIRMS:4:2:3+0010::Auftrag entgegengenommen".getBytes());
        HISYN hisyn = new HISYN("HISYN:10:3:8+2".getBytes());
        hirms.parseElement();
        hirms2.parseElement();
        hisyn.parseElement();
        List<IMessageElement> unsortedList = new LinkedList<IMessageElement>();
        unsortedList.add(hisyn);
        unsortedList.add(hirms);
        unsortedList.add(hirms2);
        Collections.sort(unsortedList,new SegmentComparator());
        assertEquals(3,unsortedList.size());
        assertEquals(hirms2,unsortedList.get(0));
    }
}
