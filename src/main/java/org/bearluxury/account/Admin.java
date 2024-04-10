package org.bearluxury.account;

public class Admin extends Account {
    public Admin(String firstName, String lastName, String userName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, userName, email, phoneNumber, password, role);
    }

    public Clerk makeClerkAccount(String firstName, String lastName, String userName,
                                  String email, long phoneNumber, String password, Role role) {
        return new Clerk(firstName, lastName, userName, email, phoneNumber, password, role);
    }
}
