package org.bearluxury.room;

/**
 * The Room class represents a room in a hotel or similar accommodation facility.
 * Each room has a unique room number, price, smoking policy, room type, bed type,
 * quality level, and number of beds.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class Room {
    private int roomNumber;

    // not necessary
    // private boolean isClean;
    private boolean canSmoke;
    private double price;
    private ROOM_TYPE roomType;
    private BED_TYPE bed;
    private QUALITY_LEVEL qualityLevel;
    private int numberOfBeds;

    /**
     * Constructs a new Room with the specified attributes.
     *
     * @param roomNumber   The unique room number.
     * @param price        The price per night for the room.
     * @param canSmoke     Indicates whether smoking is allowed in the room.
     * @param roomType     The type of the room.
     * @param bed          The type of bed(s) available in the room.
     * @param qualityLevel The quality level of the room.
     * @param numberOfBeds The number of beds in the room.
     */
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

    /**
     * Returns a string representation of the Room object.
     *
     * @return A string representation of the Room object.
     */
    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", canSmoke=" + canSmoke +
                ", price=" + price +
                ", roomType=" + roomType +
                ", bed=" + bed +
                ", qualityLevel=" + qualityLevel +
                ", numberOfBeds=" + numberOfBeds +
                '}';
    }

    /**
     * Gets the room type.
     *
     * @return The room type.
     */
    public ROOM_TYPE getRoomType() {
        return roomType;
    }

    /**
     * Sets the room type.
     *
     * @param roomType The room type to set.
     */
    public void setRoomType(ROOM_TYPE roomType) {
        this.roomType = roomType;
    }

    /**
     * Gets the bed type.
     *
     * @return The bed type.
     */
    public BED_TYPE getBed() {
        return bed;
    }

    /**
     * Sets the bed type.
     *
     * @param bed The bed type to set.
     */
    public void setBed(BED_TYPE bed) {
        this.bed = bed;
    }

    /**
     * Gets the quality level of the room.
     *
     * @return The quality level of the room.
     */
    public QUALITY_LEVEL getQualityLevel() {
        return qualityLevel;
    }

    /**
     * Sets the quality level of the room.
     *
     * @param qualityLevel The quality level to set.
     */
    public void setQualityLevel(QUALITY_LEVEL qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    /**
     * Gets the room number.
     *
     * @return The room number.
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number.
     *
     * @param roomNumber The room number to set.
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Checks if smoking is allowed in the room.
     *
     * @return True if smoking is allowed, otherwise false.
     */
    public boolean isCanSmoke() {
        return canSmoke;
    }

    /**
     * Sets whether smoking is allowed in the room.
     *
     * @param canSmoke True if smoking is allowed, otherwise false.
     */
    public void setCanSmoke(boolean canSmoke) {
        this.canSmoke = canSmoke;
    }

    /**
     * Gets the price per night for the room.
     *
     * @return The price per night for the room.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per night for the room.
     *
     * @param price The price per night to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the number of beds in the room.
     *
     * @return The number of beds in the room.
     */
    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    /**
     * Sets the number of beds in the room.
     *
     * @param numberOfBeds The number of beds to set.
     */
    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
}
