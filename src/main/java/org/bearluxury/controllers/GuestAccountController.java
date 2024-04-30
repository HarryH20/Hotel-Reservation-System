package org.bearluxury.controllers;

import org.bearluxury.account.GuestAccountJDBCDAO;
import org.bearluxury.account.Guest;

import java.util.Optional;
import java.util.Set;

/**
 * The guest account controller
 * accesses a guest database object
 * for database modification
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */

public class GuestAccountController {
    private GuestAccountJDBCDAO guestAccountDAO;

    /**
     * creates a guests account controller
     * @param accountDAO the guest dao to use for the controller
     */
    public GuestAccountController(GuestAccountJDBCDAO accountDAO){
        this.guestAccountDAO = accountDAO;
    }

    /**
     * inserts an account
     * @param account the guest to insert
     */
    public void insertAccount(Guest account) {
        guestAccountDAO.insert(account);
    }

    /**
     * gets a guest account from the database
     * @param email the email for searching
     * @return the guest account
     */

    public Optional<Guest> getAccount(String email) {
        return guestAccountDAO.get(email);
    }

    /**
     * lists the accounts in the database
     * @return the set of guest accounts
     */

    public Set<Guest> listAccounts() {
        return guestAccountDAO.list();
    }

    /**
     * updates an existing account
     * @param account the account being updated
     * @param email the email to search with
     */
    public void updateAccounts(Guest account, String email) {
        guestAccountDAO.update(account, email);
    }

    /**
     * deletes an account from database
     * @param email the email to search with
     * @return whether deletion was successful or not
     */

    public boolean deleteAccounts(String email) {
        return guestAccountDAO.delete(email);
    }

    /**
     * clears the guest database
     */

    public void clearAccounts() {
        guestAccountDAO.clear();
    }

    /**
     * closes the guest database
     */
    public void closeConnection() {
        guestAccountDAO.close();
    }


}
