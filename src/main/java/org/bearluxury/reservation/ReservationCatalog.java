package org.bearluxury.reservation;

import org.bearluxury.room.Room;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The ReservationCatalog class manages a collection of reservations.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class ReservationCatalog {
    private Set<Reservation> reservations = new TreeSet<>(Comparator.comparing(Reservation::getRoomNumber));

    /**
     * Retrieves the set of reservations.
     *
     * @return the set of reservations
     */
    public Set<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the reservations in the catalog.
     *
     * @param reservations the set of reservations to set
     */
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    /**
     * Checks if a room is available for the specified date range.
     *
     * @param room the room to check availability for
     * @param startDate the start date of the date range
     * @param endDate the end date of the date range
     * @return true if the room is available for the date range, false otherwise
     */
    public boolean isAvailableDate(Room room, LocalDate startDate, LocalDate endDate){
        boolean isAvailable = true;

        Date startDateAsDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateAsDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Set<Reservation> listOfAllDatesAtRoom = reservations.stream().filter(reservation
                -> reservation.getRoomNumber() ==
                room.getRoomNumber()).collect(Collectors.toSet());

        for(Reservation r : listOfAllDatesAtRoom){


            // NO COMMENT LOL
            if(!(endDateAsDate.before(r.getStartDate()) || startDateAsDate.after(r.getEndDate()))){
               isAvailable = false;
                System.out.println("THE ROOM IS NOT AVAILABLE");
            }
        }
        return isAvailable;
    }

}
