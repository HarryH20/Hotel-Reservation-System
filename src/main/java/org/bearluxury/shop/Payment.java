package org.bearluxury.shop;

import java.util.Date;

/**
 * The Payment class represents a generic payment.
 * It provides functionality to store the payment date and amount, and defines an abstract
 * method for processing the payment.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public abstract class Payment {
    private Date paymentDate;
    private double amount;

    /**
     * Constructs a new Payment object with the specified amount and sets the payment date to the current date.
     *
     * @param amount The amount of the payment.
     */
    public Payment(double amount) {
        this.paymentDate = new Date();
        this.amount = amount;
    }

    /**
     * Gets the date when the payment was made.
     *
     * @return The payment date.
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * Gets the amount of the payment.
     *
     * @return The payment amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Abstract method to be implemented by subclasses to process the payment.
     *
     * @return True if the payment is successfully processed, otherwise false.
     */
    public abstract boolean processPayment();
}
