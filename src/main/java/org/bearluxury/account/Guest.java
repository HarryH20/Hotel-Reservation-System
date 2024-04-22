package org.bearluxury.account;

import org.bearluxury.Billing;
import org.bearluxury.CreditCard;
import org.bearluxury.reservation.Reservation;

import java.util.ArrayList;

public class Guest extends Account{
    ArrayList<Reservation> reservationList;
    CreditCard card;
    Billing bill;

    public Guest(String firstName, String lastName, String userName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, userName, email, phoneNumber, password, role);
        reservationList = new ArrayList<>();
    }

    public void setCreditCard (CreditCard card) {
        this.card = card;
    }

    public CreditCard getCreditCard() {
        return this.card;
    }
}
