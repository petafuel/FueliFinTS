package net.petafuel.fuelifints.protocol.fints3.validator.validators;

import junit.framework.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class NumValidatorTest {
    class TestValue {
        @num
        Integer bla;
    }

    @Test
    public void testIsValid() throws Exception {
        TestValue tv = new TestValue();
        tv.bla = 909;

        Validator v = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<TestValue>> constraintViolations = v.validate(tv);

        //Assert.assertTrue(constraintViolations.size() > 0);  //0 zu Beginn ist nicht erlaubt!

        //tv.bla = 1919;
        //constraintViolations = v.validate(tv);

        Assert.assertTrue(constraintViolations.size() == 0);
    }
}
