package net.petafuel.fuelifints.support;

import net.petafuel.fuelifints.model.IMessageElement;
import net.petafuel.fuelifints.protocol.fints3.segments.HIKIM;
import net.petafuel.fuelifints.protocol.fints3.segments.HIRMG;
import net.petafuel.fuelifints.protocol.fints3.segments.HIRMS;
import net.petafuel.fuelifints.protocol.fints3.segments.Segment;
import net.petafuel.fuelifints.protocol.fints3.segments.deg.Segmentkopf;

import java.lang.reflect.Field;
import java.util.Comparator;

public class SegmentComparator implements Comparator<IMessageElement> {

    @Override
    public int compare(IMessageElement o1, IMessageElement o2) {
        if (o1 instanceof Segment && o2 instanceof Segment) {
            //HIRMG muss immer an den Anfang und kommt nur einmalig vor, deshalb genügt diese Unterscheidung
            if (o1 instanceof HIRMG) {
                return -1;
            } else if (o2 instanceof HIRMG) {
                return 1;
            } else if (o1 instanceof HIRMS && !(o2 instanceof HIRMS)) {
                return -1;
            } else if (!(o1 instanceof HIRMS) && o2 instanceof HIRMS) {
                return 1;
            } else if(o1 instanceof HIKIM) {
                return 1;
            } else if(o2 instanceof HIKIM) {
                return -1;
            } else {
                Field[] o1Fields = o1.getClass().getDeclaredFields();
                Field[] o2Fields = o2.getClass().getDeclaredFields();
                Field o1SegmentkopfField = null;
                Field o2SegmentkopfField = null;
                for (Field f : o1Fields) {
                    f.setAccessible(true);
                    if (f.getType().equals(Segmentkopf.class)) {
                        o1SegmentkopfField = f;
                    }
                }
                for (Field f : o2Fields) {
                    f.setAccessible(true);
                    if (f.getType().equals(Segmentkopf.class)) {
                        o2SegmentkopfField = f;
                    }
                }
                if (o1SegmentkopfField == null || o2SegmentkopfField == null) {
                    throw new ClassCastException("Es konnte kein Segmentkopf gefunden werden.");
                }
                try {
                    Segmentkopf o1Segmentkopf = (Segmentkopf) o1SegmentkopfField.get(o1);
                    Segmentkopf o2Segmentkopf = (Segmentkopf) o2SegmentkopfField.get(o2);
                    if (o1Segmentkopf.getBezugssegment() != null && o1Segmentkopf.getBezugssegment() != 0 && o2Segmentkopf.getBezugssegment() != null && o2Segmentkopf.getBezugssegment() != 0) {
                        return o1Segmentkopf.getBezugssegment() - o2Segmentkopf.getBezugssegment();
                    }
                    return 1;
                } catch (IllegalAccessException|NullPointerException e) {
                    //acessible true
                    return 0;
                }

            }
        } else {
            throw new ClassCastException("Es können lediglich Segmente miteinander verglichen werden.");
        }
    }
}
