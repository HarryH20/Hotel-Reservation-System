package org.bearluxury;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Reservation {
    private Date startDate;
    private Date endDate;
    private double discount  = 1.0;

    List<Room> rooms = new ArrayList<>();


    public Reservation(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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
