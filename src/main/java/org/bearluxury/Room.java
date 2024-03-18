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
    private boolean isClean;
    private boolean canSmoke;
    private boolean isOpen;
    private double price;
    private TYPE roomType;
    private List<BED_TYPE> bed = new ArrayList<>();
    private QUALITY_LEVEL qualityLevel;

    public Room(int roomNumber, boolean isClean, boolean canSmoke, boolean isOpen, double price,
                TYPE roomType, List<BED_TYPE> bed, QUALITY_LEVEL qualityLevel) {
        this.roomNumber = roomNumber;
        this.isClean = isClean;
        this.canSmoke = canSmoke;
        this.isOpen = isOpen;
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

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public boolean isCanSmoke() {
        return canSmoke;
    }

    public void setCanSmoke(boolean canSmoke) {
        this.canSmoke = canSmoke;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



}
