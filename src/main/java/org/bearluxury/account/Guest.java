package org.bearluxury.account;

import org.bearluxury.reservation.Reservation;

import java.util.ArrayList;
/**
 * Each guest object models the account
 * information for a guest
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */

public class Guest extends Account{
    CreditCard card;

    /**
     * creates a new guest account
     * @param firstName
     * @param lastName
     * @param email
     * @param phoneNumber
     * @param password
     * @param role
     * @param card
     */
    public Guest(String firstName, String lastName,
                 String email, long phoneNumber, String password, Role role, CreditCard card) {
        super(firstName, lastName, email, phoneNumber, password, role);
        this.card = card;

    }

    /**
     * create an empty guest account
     */
    public Guest(){}

    /**
     * sets card info
     * @param card the card to update with
     */
    public void setCreditCard (CreditCard card) {
        this.card = card;
    }

    /**
     * gets the credit card
     * @return the card
     */

    public CreditCard getCreditCard() {
        return this.card;
    }
}
