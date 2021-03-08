package net.petafuel.fuelifints.support;

import net.petafuel.fuelifints.protocol.fints3.segments.AbstractElement;
import net.petafuel.fuelifints.protocol.fints3.segments.Element;
import net.petafuel.fuelifints.protocol.fints3.segments.ElementDescription;

import java.lang.reflect.Field;
import java.util.Comparator;

public class FieldComparator implements Comparator<Field> {

    int segmentVersion = 0;

    public FieldComparator(int segmentVersion) {
        this.segmentVersion = segmentVersion;
    }

    @Override
    public int compare(Field o1, Field o2) {
        if (o1.getAnnotation(Element.class) == null || o2.getAnnotation(Element.class) == null) {
            throw new ClassCastException("couldn't find necessary annotation");
        }
        return Integer.compare(AbstractElement.getMatchingElementDescription(o1, segmentVersion).number(), AbstractElement.getMatchingElementDescription(o2, segmentVersion).number());
    }
}
