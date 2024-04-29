package org.bearluxury.account;

public class Admin extends Account {
    public Admin(String firstName, String lastName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, email, phoneNumber, password, role);
    }

    public Clerk makeClerkAccount(String firstName, String lastName,
                                  String email, long phoneNumber, String password, Role role) {
        return new Clerk(firstName, lastName, email, phoneNumber, password, role);
    }
}
