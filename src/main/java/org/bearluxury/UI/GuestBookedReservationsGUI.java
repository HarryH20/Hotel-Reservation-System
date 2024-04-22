package org.bearluxury.UI;

import org.bearluxury.reservation.ReservationCatalog;

import java.util.Timer;

public class GuestBookedReservationsGUI extends BookedReservationsGUI {
    private Timer timer;

    public GuestBookedReservationsGUI(ReservationCatalog reservationCatalog) {
        super(reservationCatalog);

    }
}
