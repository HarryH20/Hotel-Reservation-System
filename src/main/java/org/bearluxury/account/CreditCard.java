package org.bearluxury.account;

import java.util.Objects;

/**
 * Each credit card is linked to
 * a guest account and has card info
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */

public class CreditCard {
    private String cardNumber;
    private String cardHolderName;
    private String expDate;
    private String cvv;
    private double balance = 20000.0;

    /**
     * creates a credit card object
     * @param cardNumber
     * @param cardHolderName
     * @param expDate
     * @param cvv
     */

    public CreditCard(String cardNumber, String cardHolderName, String expDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    /**
     * gets the card number
     * @return the card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * gets the cardholder name
     * @return the cardholder name
     */

    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * gets the expiration date
     * @return the exp date
     */

    public String getExpDate() {
        return expDate;
    }

    /**
     * gets the cvv
     * @return the cvv
     */

    public String getCvv() {
        return cvv;
    }

    /**
     * gets the balance
     * @return the card balance
     */

    public double getBalance() {
        return this.balance;
    }

    /**
     * charges a card
     * @param charge the charge to a card
     */

    public void chargeCard(double charge) {
        this.balance -= charge;
    }
}