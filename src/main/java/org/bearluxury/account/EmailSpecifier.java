package org.bearluxury.account;

import java.util.regex.Pattern;

/**
 * Each email specifier
 * is used to make sure emails are
 * in correct format
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class EmailSpecifier {

    /**
     * checks if an email is valid
     * @param email the email to check
     * @return whether an email has valid format
     */
    public static boolean isValidEmail(String email){
        // uses regex to determine if an email fits the desired pattern
        // elite use of regex
        return patternMatches(email, "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@"
                + "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    }

    /**
     * checks for pattern matching
     * @param emailAddress the email to check
     * @param regexPattern the pattern to us
     * @return whether the pattern matches the regex pattern
     */
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
