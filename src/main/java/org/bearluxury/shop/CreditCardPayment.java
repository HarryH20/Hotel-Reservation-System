package org.bearluxury.shop;

import org.bearluxury.account.CreditCard;

/**
 * The CreditCardPayment class represents a payment made using a credit card.
 * It extends the Payment class and includes a reference to the CreditCard used for the payment.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class CreditCardPayment extends Payment {
   CreditCard card;

    /**
     * Constructs a new CreditCardPayment object with the specified amount and credit card.
     *
     * @param amount The amount of the payment.
     * @param card   The CreditCard object used for the payment.
     */
    public CreditCardPayment(double amount, CreditCard card) {
        super(amount);
        this.card = card;
    }

    /**
     * Processes the credit card payment.
     * Checks if the payment amount is valid and if the credit card has sufficient balance.
     * If both conditions are met, the payment is processed by charging the credit card.
     *
     * @return True if the payment is successfully processed, otherwise false.
     */
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

    /**
     * Gets the CreditCard object associated with this payment.
     *
     * @return The CreditCard object used for the payment.
     */
    public CreditCard getCard() {
        return card;
    }
}
