package org.bearluxury;

import java.util.ArrayList;
import java.util.List;

enum TYPE{
    VINTAGE_CHARM,
    URBAN_ELEGANCE,
    NATURE_RETREAT


}
enum BED_TYPE{
    QUEEN,
    KING,
    FULL,
    TWIN
}
enum QUALITY_LEVEL{
    EXECUTIVE,
    BUISNESS,
    COMFORT,
    ECONOMY
}
class Room {
    private int roomNumber;

    // not necessary
    // private boolean isClean;
    private boolean canSmoke;
    private double price;
    private TYPE roomType;
    private BED_TYPE bed;
    private QUALITY_LEVEL qualityLevel;

    public Room(int roomNumber, double price, boolean canSmoke,
                TYPE roomType, BED_TYPE bed, QUALITY_LEVEL qualityLevel) {
        this.roomNumber = roomNumber;
        // this.isClean = isClean;
        this.canSmoke = canSmoke;
        this.price = price;
        this.roomType = roomType;
        this.bed = bed;
        this.qualityLevel = qualityLevel;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean isCanSmoke() {
        return canSmoke;
    }

    public void setCanSmoke(boolean canSmoke) {
        this.canSmoke = canSmoke;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



}
