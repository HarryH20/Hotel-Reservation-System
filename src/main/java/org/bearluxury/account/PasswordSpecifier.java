package org.bearluxury.account;
import java.lang.Object.*;

/**
 * Each password specifier
 * is used to make sure passwords are
 * in correct format
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class PasswordSpecifier {
    private String passwordProblem;

    /**
     * constructs an empty passwordSpecifier
     */
    public PasswordSpecifier(){
        passwordProblem = "";

    }

    /**
     * gets the password problem
     * @return the password problem
     */

    public String getPasswordProblem() {
        return passwordProblem;
    }

    /**
     * checks if the password is valid
     * @param password the password to check
     * @return whether the password is valid
     */
    public boolean checkPassword(String password){
        boolean isGood = true;
        if(password.length() < 10) {
            isGood = false;
            passwordProblem = "Password must be at least 10 characters long.";
        }else if(!containsAny(password,"!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~")){
            isGood = false;
            passwordProblem = "Password needs to contain at least one special character.";
        }else if(numCaps(password) < 1){
            isGood = false;
            passwordProblem = "Password must contain at least 1 capital letter.";
        }
        return isGood;
    }

    /**
     * checks if a string contains another string
     * @param password the string to check
     * @param symbols the other string to check
     * @return whether str1 contains any of str2
     */
    public boolean containsAny(String password, String symbols){
        boolean contains = false;
        for(int i = 0; i < password.length(); i++){
            for(int j = 0; j < symbols.length(); j++){
                if(password.charAt(i) == symbols.charAt(j)){
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

    /**
     * checks for the number of capitals in a string
     * @param str1 the string to check
     * @return the number of caps it has
     */
    public int numCaps(String str1){
        int numberOfCapitals = 0;
        for(int i = 0; i < str1.length(); i++){
            if(Character.isUpperCase(str1.charAt(i))){
                numberOfCapitals++;
            }
        }
        return numberOfCapitals;
    }
}
