package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import junit.framework.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class DigValidatorTest {
    class TestValue {
        @dig
        String bla;
    }

    @Test
    public void testIsValid() throws Exception {
        TestValue tv = new TestValue();
        tv.bla = "0909";

        Validator v = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<TestValue>> constraintViolations = v.validate(tv);

        Assert.assertTrue(constraintViolations.size() == 0);

        tv.bla = ":";
        constraintViolations = v.validate(tv);

        Assert.assertTrue(constraintViolations.size() > 0);
    }
}
