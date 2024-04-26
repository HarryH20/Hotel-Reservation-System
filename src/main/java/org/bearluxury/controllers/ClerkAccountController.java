package org.bearluxury.controllers;

import org.bearluxury.account.Account;
import org.bearluxury.account.ClerkAccountDAO;

import java.util.Optional;
import java.util.Set;

public class ClerkAccountController {
    private ClerkAccountDAO accountDAO;

    public ClerkAccountController(ClerkAccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    public void insertAccount(Account account) {
        accountDAO.insert(account);
    }

    public Optional<Account> getAccount(String email) {
        return accountDAO.get(email);
    }

    public Set<Account> listAccounts() {
        return accountDAO.list();
    }

    public void updateAccounts(Account account, String email) {
        accountDAO.update(account, email);
    }

    public boolean deleteAccounts(String email) {
        return accountDAO.delete(email);
    }

    public void clearAccounts() {
        accountDAO.clear();
    }
    public void closeConnection() {
        accountDAO.close();
    }
}
