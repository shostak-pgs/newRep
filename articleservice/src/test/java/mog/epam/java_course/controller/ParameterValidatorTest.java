package mog.epam.java_course.controller;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ParameterValidatorTest {

    @Test
    public void testValidateGet() {
        String[] args = new String[3];
        args[0] = "GET";
        args[1] = "5";
        args[2] = "URL";
        boolean actual = ParameterValidator.validate(args);
        assertTrue(actual);
    }

    @Test
    public void testValidatePOST() {
        String[] args = new String[3];
        args[0] = "POST";
        args[1] = "10";
        args[2] = "HTTP";
        boolean actual = ParameterValidator.validate(args);
        assertTrue(actual);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateWrongMethod() {
        String[] args = new String[3];
        args[0] = "PUT";
        args[1] = "10";
        args[2] = "URL";
        boolean actual = ParameterValidator.validate(args);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNegativeId() {
        String[] args = new String[3];
        args[0] = "POST";
        args[1] = "-1";
        args[2] = "HTTP";
        boolean actual = ParameterValidator.validate(args);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNegativeClientType() {
        String[] args = new String[3];
        args[0] = "POST";
        args[1] = "10";
        args[2] = "HTTPs";
        boolean actual = ParameterValidator.validate(args);
    }

    @Test(expected = ValidatorException.class)
    public void testValidateNullCase() {
        String[] args = new String[3];
        args[0] = "POST";
        args[1] = null;
        args[2] = "HTTPs";
        boolean actual = ParameterValidator.validate(args);
    }
}
