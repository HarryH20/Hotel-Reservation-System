package org.bearluxury;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Reservation {
    private int roomNumber;
    private String guestName;
    private Date startDate;
    private Date endDate;
    private double discount  = 1.0;



    public Reservation(int roomNumber, String guestName, Date startDate, Date endDate, double discount) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
