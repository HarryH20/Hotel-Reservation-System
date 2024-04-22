package org.bearluxury;

import java.util.Date;

public abstract class Payment {
    private int paymentId;
    private Date paymentDate;
    private double amount;

    public Payment(int paymentId, double amount) {
        this.paymentId = paymentId;
        this.paymentDate = new Date();
        this.amount = amount;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public abstract void processPayment();
}
