package org.bearluxury;

public class Clerk extends Account {

    public Clerk(String firstName, String lastName, String userName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, userName, email, phoneNumber, password, role);
    }
}
