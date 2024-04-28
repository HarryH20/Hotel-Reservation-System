package org.bearluxury.account;

import java.util.regex.Pattern;

public class EmailSpecifier {
    public static boolean isValidEmail(String email){
        // uses regex to determine if an email fits the desired pattern
        return patternMatches(email, "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@"
                + "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
    }
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
