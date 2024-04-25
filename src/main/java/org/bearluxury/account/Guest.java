package org.bearluxury.account;

import org.bearluxury.reservation.Reservation;

import java.util.ArrayList;

public class Guest extends Account{
    CreditCard card;
    Billing bill;

    public Guest(String firstName, String lastName, String userName,
                 String email, long phoneNumber, String password, Role role, CreditCard card) {
        super(firstName, lastName, userName, email, phoneNumber, password, role);
        this.card = card;

    }

    public void setCreditCard (CreditCard card) {
        this.card = card;
    }

    public CreditCard getCreditCard() {
        return this.card;
    }
}
