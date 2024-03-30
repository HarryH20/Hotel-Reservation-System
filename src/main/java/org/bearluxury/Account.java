package org.bearluxury;

public class Account {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private int phoneNumber;
    private String password;
    private int cardNumber;
    private int cardCVS;

    public Account(String firstName, String lastName, String userName, String email, int phoneNumber, String password, int cardNumber, int cardCVS) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.cardNumber = cardNumber;
        this.cardCVS = cardCVS;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardCVS() {
        return cardCVS;
    }

    public void setCardCVS(int cardCVS) {
        this.cardCVS = cardCVS;
    }

    @Override
    public String toString() {
        return firstName            +
                "," + lastName      +
                "," + userName      +
                "," + email         +
                "," + phoneNumber   +
                "," + password      +
                "," + cardNumber    +
                "," + cardCVS       +
                '\n';
    }
}
