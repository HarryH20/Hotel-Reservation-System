package org.bearluxury.account;

public class Guest extends Account {
    private int creditCardNumber;

    public Guest(String firstName, String lastName, String userName,
                 String email, long phoneNumber, String password, Role role, int creditCardNumber) {
        super(firstName, lastName, userName, email, phoneNumber, password, role);
        this.creditCardNumber = creditCardNumber;
    }

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
