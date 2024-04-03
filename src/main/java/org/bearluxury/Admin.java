package org.bearluxury;

public class Admin extends Account {
    public Admin(String firstName, String lastName, String userName,
                 String email, long phoneNumber, String password) {
        super(firstName, lastName, userName, email, phoneNumber, password);
    }

    public Clerk makeClerkAccount(String firstName, String lastName, String userName,
                                  String email, long phoneNumber, String password) {
        return new Clerk(firstName, lastName, userName, email, phoneNumber, password);
    }
}
