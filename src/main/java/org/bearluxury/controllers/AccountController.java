package org.bearluxury.controllers;

import org.bearluxury.account.Account;
import org.bearluxury.account.AccountJDBCDAO;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;

import java.util.Optional;
import java.util.Set;

public class AccountController {
    private AccountJDBCDAO accountDAO;

    public AccountController(AccountJDBCDAO accountDAO){
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

    public void updateAccounts(Account reservation, String email) {
        accountDAO.update(reservation, email);
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
