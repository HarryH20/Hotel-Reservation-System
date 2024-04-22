package org.bearluxury.account;

import org.bearluxury.CreditCard;
import org.bearluxury.reservation.Reservation;

import java.util.ArrayList;

public class Guest extends Account{
    ArrayList<Reservation> reservationList;
    CreditCard card;

    public Guest(String firstName, String lastName, String userName,
                 String email, long phoneNumber, String password, Role role) {
        super(firstName, lastName, userName, email, phoneNumber, password, role);
    }

}
