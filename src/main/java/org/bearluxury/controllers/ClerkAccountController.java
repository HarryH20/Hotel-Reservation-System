package org.bearluxury.controllers;

import org.bearluxury.account.Account;
import org.bearluxury.account.ClerkAccountDAO;

import java.util.Optional;
import java.util.Set;
/**
 * The clerk account controller
 * accesses a clerk database object
 * for database modification
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class ClerkAccountController {
    private ClerkAccountDAO accountDAO;

    /**
     * creates a Clerk Account Controller
     * @param accountDAO the clerk dao to use for the controller
     */
    public ClerkAccountController(ClerkAccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * inserts an account into the database
     * @param account the account to insert
     */
    public void insertAccount(Account account) {
        accountDAO.insert(account);
    }

    /**
     * gets an account from the database
     * @param email the email to search with
     * @return the account
     */
    public Optional<Account> getAccount(String email) {
        return accountDAO.get(email);
    }

    /**
     * lists accounts from a database
     * @return the set of accounts from the database
     */

    public Set<Account> listAccounts() {
        return accountDAO.list();
    }

    /**
     * updates an existing account in the database
     * @param account the account to update with
     * @param email the email to search the database with
     */

    public void updateAccounts(Account account, String email) {
        accountDAO.update(account, email);
    }

    /**
     * deletes an account from a database
     * @param email the email to search with
     * @return whether the deletion was successful
     */

    public boolean deleteAccounts(String email) {
        return accountDAO.delete(email);
    }

    /**
     * clears database
     */
    public void clearAccounts() {
        accountDAO.clear();
    }

    /**
     * closes database connection
     */
    public void closeConnection() {
        accountDAO.close();
    }
}
