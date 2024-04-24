package org.bearluxury;

import org.bearluxury.CreditCard;
import org.bearluxury.Payment;

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
        if (amount < balance) {
            card.chargeCard(amount);
            return true;
        }
        return false;
    }
}
