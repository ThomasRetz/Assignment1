import org.junit.platform.engine.support.discovery.SelectorResolver;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.regex.*;

public class PasswordCheckerUtility {

    String password = "";

    //determines if two passwords are equal and throws an exception if they are
    public static void comparePasswords(String password, String passwordConfirm) throws UnmatchedException {
        System.out.println("Comparing " + password + " and " + passwordConfirm + " in comparePasswords: ");


            if (!password.equals(passwordConfirm)) {
                throw new UnmatchedException();
            } else {
                System.out.println("Exception was not thrown, passwords are equal");
            }

    }

    //Compares the 2 passwords and returns true or false
    public static boolean comparePasswordsWithReturn(String password, String passwordConfirm) {

        System.out.println("Comparing " + password + " and " + passwordConfirm + " in comparePasswordsWithReturn: ");

        return password.equals(passwordConfirm);
    }

    //determines how many invalid passwords are in the passwords array/list.
    public static ArrayList<String> getInvalidPasswords(ArrayList<String> passwords){

        //Create an illegalPasswords array list and add the illegal passwords to it with the correct message.
        ArrayList<String> illegalPasswords = new ArrayList<String>();
        String message = null;

        for(int i = 0; i < passwords.size(); i++){
            try{
                isValidPassword(passwords.get(i));
            } catch (LengthException lengthException) {
                message = (passwords.get(i) + " -> " + lengthException.getMessage());
                illegalPasswords.add(message);
            } catch (NoUpperAlphaException upperAlphaException) {
                message = (passwords.get(i) + " -> " + upperAlphaException.getMessage());
                illegalPasswords.add(message);
            } catch (NoLowerAlphaException lowerAlphaException) {
                message = (passwords.get(i) + " -> " + lowerAlphaException.getMessage());
                illegalPasswords.add(message);
            } catch (NoDigitException digitException) {
                message = (passwords.get(i) + " -> " + digitException.getMessage());
                illegalPasswords.add(message);
            } catch (NoSpecialCharacterException specialCharacterException) {
                message = (passwords.get(i) + " -> " + specialCharacterException.getMessage());
                illegalPasswords.add(message);
            } catch (WhiteSpaceException whiteSpaceException) {
                message = (passwords.get(i) + " -> " + whiteSpaceException.getMessage());
                illegalPasswords.add(message);
            } catch (InvalidSequenceException sequenceException) {
                message = (passwords.get(i) + " -> " + sequenceException.getMessage());
                illegalPasswords.add(message);
            }
        }

        return illegalPasswords;
    }


    //Checks if the password is between six and nine characters
    public static boolean hasBetweenSixAndNineChars(String password){

        if(password.length() >= 6 && password.length() <= 9) {
                System.out.println("Password is weak");
                return true;
        }


        else{
            System.out.println("Password was good length");
            return false;
        }
    }

    //Checks for a digit
    public static boolean hasDigit(String password) throws NoDigitException{
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(password);
        if (m.find()) {
            return true;
        } else {
            throw new NoDigitException();
        }
    }

    //Checks if there is a character being repeated more than 2 times in a row.
    //regex sucks
    public static boolean hasSameCharInSequence(String password) throws InvalidSequenceException{

        //seriously this sucks
        Pattern p = Pattern.compile("([a-zA-Z0-9\\W])\\1\\1+");
        Matcher m = p.matcher(password);

        if (m.find()) {
            throw new InvalidSequenceException();
        } else {
            return true;
        }
    }


    //Checks for a lowercase letter
    public static boolean hasLowerAlpha(String password) throws NoLowerAlphaException{
        Pattern p = Pattern.compile("[a-z]");
        Matcher m = p.matcher(password);

            if (m.find()) {
                return true;
            } else {
                throw new NoLowerAlphaException();
            }
    }

    //checks for a special character
    public static boolean hasSpecialChar(String password) throws NoSpecialCharacterException{
        Pattern p = Pattern.compile("\\W");
        Matcher m = p.matcher(password);

            if (m.find()) {
                return true;
            }

            //throws if no special character(only other possible result)
            else {
                throw new NoSpecialCharacterException();
            }

    }

    //I've added in another function for testing to see if the password has spaces in it since I assume we will not want those
    public static boolean hasWhiteSpace(String password) throws WhiteSpaceException{
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(password);

        if(m.find()){
            throw new WhiteSpaceException();
        }
        else{
            return true;
        }
    }

    //Checks for at least 1 uppercase letter
    public static boolean hasUpperAlpha(String password) throws NoUpperAlphaException{
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(password);
            if (m.find()) {
                return true;
            } else {
                throw new NoUpperAlphaException();
            }


    }

    //Length check
    public static boolean isValidLength(String password) throws LengthException{
            if (password.length() < 6) {
                throw new LengthException();
            } else {
                return true;
            }

    }


    /*I remember you stating that we could create a super class for all the exceptions, I attempted in my testing area but wasn't able to get it to work initially
    so we are stuck with this wonderful list of commas */

    public static boolean isValidPassword(String password) throws LengthException, NoDigitException, NoLowerAlphaException, NoUpperAlphaException, NoSpecialCharacterException, InvalidSequenceException, WhiteSpaceException{
        boolean result;
        if(password == null){
            return false;
        }
        if(!isValidLength(password)){
            throw new LengthException();
        }
        if(!hasUpperAlpha(password)){
            throw new NoUpperAlphaException();
        }
        if(!hasLowerAlpha(password)){
            throw new NoLowerAlphaException();
        }
        if(!hasDigit(password)){
            throw new NoDigitException();
        }
        if(!hasWhiteSpace(password)){
            throw new WhiteSpaceException();
        }
        if(!hasSpecialChar(password)) {
            throw new NoSpecialCharacterException();
        }

        else {
            return true;
        }
    }

    public static boolean isWeakPassword(String password) throws WeakPasswordException{
        try {
            if(hasBetweenSixAndNineChars(password)){
                throw new WeakPasswordException();
            }
            else{
                return false;
            }
        } catch(WeakPasswordException weakPasswordException){
            System.out.println(weakPasswordException.getMessage());
            return true;
        }
    }
}
