package org.bearluxury.reservation;

import java.util.*;

public class ReservationCatalog {
    private Set<Reservation> reservations = new TreeSet<>(Comparator.comparing(Reservation::getRoomNumber));

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }



}
