package org.bearluxury;

import java.util.ArrayList;
import java.util.List;

enum ROOM_TYPE{
    VINTAGE_CHARM,
    URBAN_ELEGANCE,
    NATURE_RETREAT;

    public String toString(){
        if(this == VINTAGE_CHARM){
            return "Vintage Charm";
        }
        else if(this == URBAN_ELEGANCE){
            return "Urban Elegance";
        }
        else if(this == NATURE_RETREAT){
            return "Nature Retreat";
        }
        return "EMPTY";
    }


}
enum BED_TYPE{
    QUEEN,
    KING,
    FULL,
    TWIN;
    public String toString(){
        if(this == QUEEN){
            return "Queen";
        }
        else if(this == KING){
            return "King";
        }
        else if(this == FULL){
            return "Full";
        }
        else if(this == TWIN){
            return "Twin";
        }
        return "EMPTY";
    }
}
enum QUALITY_LEVEL{
    EXECUTIVE,
    BUSINESS,
    COMFORT,
    ECONOMY;
    public String toString(){
        if(this == EXECUTIVE){
            return "Executive";
        }
        else if(this == BUSINESS){
            return "Business";
        }
        else if(this == COMFORT){
            return "Comfort";
        }
        else if(this == ECONOMY){
            return "Economy";
        }
        return "EMPTY";
    }
}
class Room {
    private int roomNumber;

    // not necessary
    // private boolean isClean;
    private boolean canSmoke;
    private double price;
    private ROOM_TYPE roomType;
    private BED_TYPE bed;
    private QUALITY_LEVEL qualityLevel;
    private int numberOfBeds;

    public Room(int roomNumber, double price, boolean canSmoke,
                ROOM_TYPE roomType, BED_TYPE bed, QUALITY_LEVEL qualityLevel, int numberOfBeds) {
        this.roomNumber = roomNumber;
        // this.isClean = isClean;
        this.canSmoke = canSmoke;
        this.price = price;
        this.roomType = roomType;
        this.bed = bed;
        this.qualityLevel = qualityLevel;
        this.numberOfBeds = numberOfBeds;
    }

    public ROOM_TYPE getRoomType() {
        return roomType;
    }

    public void setRoomType(ROOM_TYPE roomType) {
        this.roomType = roomType;
    }

    public BED_TYPE getBed() {
        return bed;
    }

    public void setBed(BED_TYPE bed) {
        this.bed = bed;
    }

    public QUALITY_LEVEL getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(QUALITY_LEVEL qualityLevel) {
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

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
}
