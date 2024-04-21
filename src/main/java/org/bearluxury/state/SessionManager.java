package org.bearluxury.state;

public class SessionManager {
    private static SessionManager instance;
    private String currentUserEmail;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String email) {
        currentUserEmail = email;
    }
}

