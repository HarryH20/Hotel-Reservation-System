package org.bearluxury.shop;

import org.bearluxury.account.CreditCard;

public class CreditCardPayment extends Payment {
   CreditCard card;

    public CreditCardPayment(double amount, CreditCard card) {
        super(amount);
        this.card = card;
    }

    @Override
    public boolean processPayment() {
        double balance = card.getBalance();
        double amount = getAmount();
        if (amount > 0.01 && amount < balance) {
            card.chargeCard(amount);
            return true;
        }
        return false;
    }

    public CreditCard getCard() {
        return card;
    }
}
