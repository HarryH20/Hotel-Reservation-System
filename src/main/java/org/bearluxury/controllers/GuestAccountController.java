package org.bearluxury.controllers;

import org.bearluxury.account.GuestAccountJDBCDAO;
import org.bearluxury.account.Guest;

import java.util.Optional;
import java.util.Set;

public class GuestAccountController {
    private GuestAccountJDBCDAO guestAccountDAO;

    public GuestAccountController(GuestAccountJDBCDAO accountDAO){
        this.guestAccountDAO = accountDAO;
    }
    public void insertAccount(Guest account) {
        guestAccountDAO.insert(account);
    }

    public Optional<Guest> getAccount(String email) {
        return guestAccountDAO.get(email);
    }

    public Set<Guest> listAccounts() {
        return guestAccountDAO.list();
    }

    public void updateAccounts(Guest account, String email) {
        guestAccountDAO.update(account, email);
    }

    public boolean deleteAccounts(String email) {
        return guestAccountDAO.delete(email);
    }

    public void clearAccounts() {
        guestAccountDAO.clear();
    }
    public void closeConnection() {
        guestAccountDAO.close();
    }


}
