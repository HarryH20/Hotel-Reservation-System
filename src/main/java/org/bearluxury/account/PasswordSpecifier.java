package org.bearluxury.account;
import java.lang.Object.*;
public class PasswordSpecifier {
    private String passwordProblem;
    public PasswordSpecifier(){
        passwordProblem = "";

    }

    public String getPasswordProblem() {
        return passwordProblem;
    }

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
    public boolean containsAny(String str1, String str2){
        boolean contains = false;
        for(int i = 0; i < str1.length(); i++){
            for(int j = 0; j < str2.length(); j++){
                if(str1.charAt(i) == str2.charAt(j)){
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }
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
