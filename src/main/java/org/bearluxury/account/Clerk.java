package org.bearluxury.account;

/**
 * Each clerk object models the account
 * information for a clerk
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class Clerk extends Account {


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
    public Clerk(String firstName, String lastName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, email, phoneNumber, password, role);
    }
}
