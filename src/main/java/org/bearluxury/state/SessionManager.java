package org.bearluxury.state;

import org.bearluxury.account.*;

/**
 * The SessionManager class manages the current user session and provides methods
 * to get and set the current account.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class SessionManager {
    private static SessionManager instance;
    private Account account;

    /**
     * Private constructor to prevent direct instantiation of SessionManager.
     */
    private SessionManager() {}

    /**
     * Returns the singleton instance of SessionManager.
     *
     * @return The SessionManager instance.
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Retrieves the current account associated with the session.
     *
     * @return The current Account object, or null if no account is set.
     */
    public Account getCurrentAccount() {
        if (account == null) {
            return null; // No account set
        }

        Role role = account.getRole();

        if (role == Role.GUEST) {
            if (account instanceof Guest) {
                return account;
            } else {
                Guest guest = new Guest(account.getFirstName(), account.getLastName(),
                         account.getEmail(),
                        account.getPhoneNumber(), account.getPassword(), Role.GUEST, new CreditCard("123"," ", " ", "123")); // Setting credit card number to 0
                guest.setId(account.getId());
                return guest;
            }
        } else if (role == Role.CLERK) {
            if (account instanceof Clerk) {
                return account;
            } else {

                Clerk clerk = new Clerk(account.getFirstName(), account.getLastName(),
                         account.getEmail(),
                        account.getPhoneNumber(), account.getPassword(), Role.CLERK);
                clerk.setId(account.getId());
                return clerk;
            }
        } else if (role == Role.ADMIN) {
            if (account instanceof Admin) {
                return account;
            } else {
                Admin admin = new Admin(account.getFirstName(), account.getLastName(),
                         account.getEmail(),
                        account.getPhoneNumber(), account.getPassword(), Role.ADMIN);
                admin.setId(account.getId());
                return admin;
            }
        } else {
            return null; // Unknown role
        }
    }

    /**
     * Sets the current account for the session.
     *
     * @param account The Account object to set as the current account.
     */
    public void setCurrentAccount(Account account) {
        this.account = account;
    }
}

