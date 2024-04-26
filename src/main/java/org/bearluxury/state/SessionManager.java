package org.bearluxury.state;

import org.bearluxury.account.Account;
import org.bearluxury.controllers.AccountController;

public class SessionManager {
    private static SessionManager instance;
    private Account account;

    private SessionManager() {}

    //

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Account getCurrentAccount() {
        return account;
    }

    public void setCurrentAccount(Account account) {
        this.account = account;
    }
}

