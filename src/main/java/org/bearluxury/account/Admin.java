package org.bearluxury.account;


/**
 * Each admin object models the account
 * information for an admin
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class Admin extends Account {

    /**
     * makes an admin account
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     * @param role
     */
    public Admin(String firstName, String lastName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, email, phoneNumber, password, role);
    }

    /**
     * makes a clerk account
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     * @param role
     * @return
     */
    public Clerk makeClerkAccount(String firstName, String lastName,
                                  String email, long phoneNumber, String password, Role role) {
        return new Clerk(firstName, lastName, email, phoneNumber, password, role);
    }
}
