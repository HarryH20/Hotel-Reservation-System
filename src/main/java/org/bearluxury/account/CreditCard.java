package org.bearluxury.account;

import java.util.Objects;

public class CreditCard {
    private String cardNumber;
    private String cardHolderName;
    private String expDate;
    private String cvv;
    private double balance = 20000.0;

    public CreditCard(String cardNumber, String cardHolderName, String expDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getCvv() {
        return cvv;
    }

    public double getBalance() {
        return this.balance;
    }

    public void chargeCard(double charge) {
        this.balance -= charge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(getCardNumber(), that.getCardNumber()) && Objects.equals(getCardHolderName(), that.getCardHolderName()) && Objects.equals(getExpDate(), that.getExpDate()) && Objects.equals(getCvv(), that.getCvv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getCardHolderName(), getExpDate(), getCvv());
    }
}