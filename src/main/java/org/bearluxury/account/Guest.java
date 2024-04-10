package org.bearluxury.account;

public class Guest extends Account{
    public Guest(String firstName, String lastName, String userName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, userName, email, phoneNumber, password, role);
    }

}
