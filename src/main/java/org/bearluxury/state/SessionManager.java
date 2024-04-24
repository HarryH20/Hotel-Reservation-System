package org.bearluxury.state;

import org.bearluxury.account.*;
import org.bearluxury.controllers.AccountController;

public class SessionManager {
    private static SessionManager instance;
    private Account account;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Account getCurrentAccount() {
        if (account == null) {
            return null; // No account set
        }

        Role role = account.getRole();

        if (role == Role.GUEST) {
            if (account instanceof Guest) {
                return account;
            } else {
                // Create a new Guest instance based on the account's information
                Guest guest = new Guest(account.getFirstName(), account.getLastName(),
                        account.getUserName(), account.getEmail(),
                        account.getPhoneNumber(), account.getPassword(), Role.GUEST, 0); // Setting credit card number to 0
                return guest;
            }
        } else if (role == Role.CLERK) {
            if (account instanceof Clerk) {
                return account;
            } else {
                // Create a new Clerk instance based on the account's information
                return new Clerk(account.getFirstName(), account.getLastName(),
                        account.getUserName(), account.getEmail(),
                        account.getPhoneNumber(), account.getPassword(), Role.CLERK);
            }
        } else if (role == Role.ADMIN) {
            if (account instanceof Admin) {
                return account;
            } else {
                // Create a new Admin instance based on the account's information
                return new Admin(account.getFirstName(), account.getLastName(),
                        account.getUserName(), account.getEmail(),
                        account.getPhoneNumber(), account.getPassword(), Role.ADMIN);
            }
        } else {
            return null; // Unknown role
        }
    }

    public void setCurrentAccount(Account account) {
        this.account = account;
    }
}
