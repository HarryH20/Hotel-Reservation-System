package org.bearluxury;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Date;
@Component
@Scope("prototype")
class Reservation {
    private int roomNumber;
    private String guestName;
    private Date startDate;
    private Date endDate;
    private double discount  = 1.0;


    public Reservation(){}
    public Reservation(int roomNumber, String guestName, Date startDate, Date endDate, double discount) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return  roomNumber +
                "          " + guestName +
                "          " + startDate +
                "    " + endDate +
                "    " + discount+"\n";
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
