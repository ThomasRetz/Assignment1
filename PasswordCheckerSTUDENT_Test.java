
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

/**
 * STUDENT tests for the methods of PasswordChecker
 * @author Thomas Retz
 *
 *
 */
public class PasswordCheckerSTUDENT_Test {

    ArrayList<String> passwordsArray;
    java.lang.String password = "Hello";


    @Before
    public void setUp() throws Exception {
        java.lang.String[] p = {"aaBBCC", "SoggyBowl10"};
        passwordsArray = new ArrayList<String>();
        passwordsArray.addAll(Arrays.asList(p));
    }

    @After
    public void tearDown() throws Exception {
        passwordsArray = null;
    }

    /**
     * Test if the password is less than 6 characters long.
     * This test should throw a LengthException for second case.
     */
    @Test
    public void testIsValidPasswordTooShort()
    {
        Throwable exception = Assertions.assertThrows(LengthException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                PasswordCheckerUtility.isValidLength(password);
            }
        });
        assertEquals("The password must be at least 6 characters long", exception.getMessage());
    }

    /**
     * Test if the password has at least one uppercase alpha character
     * This test should throw a NoUpperAlphaException for second case
     */
    @Test
    public void testIsValidPasswordNoUpperAlpha()
    {
        try {
            assertTrue(PasswordCheckerUtility.hasUpperAlpha("Sandwich"));
            assertTrue(PasswordCheckerUtility.hasUpperAlpha("sandwich"));
        } catch (NoUpperAlphaException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if the password has at least one lowercase alpha character
     * This test should throw a NoLowerAlphaException for second case
     */
    @Test
    public void testIsValidPasswordNoLowerAlpha()
    {
        try {
            assertTrue(PasswordCheckerUtility.hasLowerAlpha("Beautiful"));
            assertTrue(PasswordCheckerUtility.hasLowerAlpha("BEAUTIFUL"));
        } catch (NoLowerAlphaException e) {
            e.printStackTrace();
        }
    }
    /**
     * Test if the password has more than 2 of the same character in sequence
     * This test should throw a InvalidSequenceException for second case
     */
    @Test
    public void testIsWeakPassword()
    {
        try {
            assertTrue(PasswordCheckerUtility.isWeakPassword("Mas1!Tea"));
            assertFalse(PasswordCheckerUtility.isWeakPassword("MAssss21@#ban"));
        } catch (WeakPasswordException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if the password has more than 2 of the same character in sequence
     * This test should throw a InvalidSequenceException for second case
     */
    @Test
    public void testIsValidPasswordInvalidSequence()
    {
        try {
            assertTrue(PasswordCheckerUtility.hasSameCharInSequence("Sup2!2Po"));
            assertTrue(PasswordCheckerUtility.hasSameCharInSequence("Suuup2!2"));
        } catch (InvalidSequenceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test if the password has at least one digit
     * One test should throw a NoDigitException
     */
    @Test
    public void testIsValidPasswordNoDigit()
    {
        try {
            assertTrue(PasswordCheckerUtility.hasDigit("Beautiful1"));
            assertTrue(PasswordCheckerUtility.hasDigit("BEAUtiful"));
        } catch (NoDigitException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test correct passwords
     * This test should not throw an exception
     */
    @Test
    public void testIsValidPasswordSuccessful() throws NoLowerAlphaException, NoSpecialCharacterException, NoDigitException, LengthException, WhiteSpaceException, NoUpperAlphaException, InvalidSequenceException {
        assertTrue(PasswordCheckerUtility.isValidPassword("BEAUtiful2!"));
    }

    /**
     * Test the invalidPasswords method
     * Check the results of the ArrayList of Strings returned by the validPasswords method
     */
    @Test
    public void testInvalidPasswords() {
        ArrayList<String> results;
        results = PasswordCheckerUtility.getInvalidPasswords(passwordsArray);
        assertEquals(results.size(), 2);
        assertEquals(results.get(0), "aaBBCC -> The password must contain at least one digit");
        assertEquals(results.get(1), "SoggyBowl10 -> The password must contain at least one special character");
    }

}