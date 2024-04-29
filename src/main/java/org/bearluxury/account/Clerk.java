package org.bearluxury.account;

public class Clerk extends Account {

    public Clerk(String firstName, String lastName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, email, phoneNumber, password, role);
    }
}
