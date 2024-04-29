package org.bearluxury.account;

import org.bearluxury.reservation.Reservation;

import java.util.ArrayList;

public class Guest extends Account{
    CreditCard card;

    public Guest(String firstName, String lastName,
                 String email, long phoneNumber, String password, Role role, CreditCard card) {
        super(firstName, lastName, email, phoneNumber, password, role);
        this.card = card;

    }
    public Guest(){}

    public void setCreditCard (CreditCard card) {
        this.card = card;
    }

    public CreditCard getCreditCard() {
        return this.card;
    }
}
