package org.bearluxury;

public class CreditCard {
    private String cardNumber;
    private String cardHolderName;
    private String expDate;
    private String cvv;

    public CreditCard(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expDate = expiryDate;
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
}