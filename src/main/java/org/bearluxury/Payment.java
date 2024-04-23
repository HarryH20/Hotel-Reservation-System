package org.bearluxury;

import java.util.Date;

public abstract class Payment {
    private Date paymentDate;
    private double amount;

    public Payment(double amount) {
        this.paymentDate = new Date();
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public abstract void processPayment();
}
