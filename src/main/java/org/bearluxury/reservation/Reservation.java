package org.bearluxury.reservation;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The Reservation class represents a reservation made by a guest.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class Reservation {
    private int roomNumber;
    private String firstName;
    private String lastName;
    private String email;
    private int numberOfGuests;
    private Date startDate;
    private Date endDate;

    private boolean checkedIn;
    private int id;

    private int reservationID;


    /** Default constructor */
    public Reservation(){}

    /**
     * Constructs a Reservation object with specified parameters.
     *
     * @param roomNumber the room number of the reservation
     * @param firstName the first name of the guest
     * @param lastName the last name of the guest
     * @param email the email address of the guest
     * @param numberOfGuests the number of guests included in the reservation
     * @param startDate the start date of the reservation
     * @param endDate the end date of the reservation
     * @param checkedIn indicates whether the guest has checked in
     */
    public Reservation(int roomNumber, String firstName,
                       String lastName, String email,
                       int numberOfGuests, Date startDate,
                       Date endDate, boolean checkedIn) {
        this.roomNumber = roomNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfGuests = numberOfGuests;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checkedIn = checkedIn;

    }


    /**
     * Generates a string representation of the reservation.
     *
     * @return a string representation of the reservation
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = dateFormat.format(startDate);
        String endDateStr = dateFormat.format(endDate);

        return roomNumber            +
                "," + firstName      +
                "," + lastName       +
                "," + email          +
                "," + numberOfGuests +
                // Date Format: yyyy-MM-dd
                "," + startDateStr +
                "," + endDateStr +
                '\n';
    }

    /**
     * Retrieves the room number of the reservation.
     *
     * @return the room number of the reservation
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number of the reservation.
     *
     * @param roomNumber the room number of the reservation
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }


    /**
     * Retrieves the start date of the reservation.
     *
     * @return the start date of the reservation
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the reservation.
     *
     * @param startDate the start date of the reservation
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Retrieves the end date of the reservation.
     *
     * @return the end date of the reservation
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the reservation.
     *
     * @param endDate the end date of the reservation
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Retrieves the first name of the guest.
     *
     * @return the first name of the guest
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the guest.
     *
     * @param firstName the first name of the guest
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the guest.
     *
     * @return the last name of the guest
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the guest.
     *
     * @param lastName the last name of the guest
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the email address of the guest.
     *
     * @return the email address of the guest
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the guest.
     *
     * @param email the email address of the guest
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the number of guests included in the reservation.
     *
     * @return the number of guests included in the reservation
     */
    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    /**
     * Sets the number of guests included in the reservation.
     *
     * @param numberOfGuests the number of guests included in the reservation
     */
    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    /**
     * Sets the ID of the reservation.
     *
     * @param id the ID of the reservation
     */
    public void setID(int id){
        this.id = id;
    }

    /**
     * Retrieves the ID of the reservation.
     *
     * @return the ID of the reservation
     */
    public int getId(){return id;}

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                roomNumber == that.roomNumber;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, roomNumber);
    }

    /**
     * Retrieves the reservation ID.
     *
     * @return the reservation ID
     */
    public int getReservationID() {
        return reservationID;
    }

    /**
     * Sets the reservation ID.
     *
     * @param reservationID the reservation ID
     */
    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    /**
     * Checks if the guest has checked in.
     *
     * @return true if the guest has checked in, false otherwise
     */

    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Sets whether the guest has checked in.
     *
     * @param checkedIn true if the guest has checked in, false otherwise
     */
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
}
