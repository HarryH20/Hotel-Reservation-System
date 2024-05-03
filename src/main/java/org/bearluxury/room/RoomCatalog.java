package org.bearluxury.room;

import org.bearluxury.room.Room;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to get and set rooms to
 * pass to a database or function.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class RoomCatalog {
    private Set<Room> rooms = new TreeSet<>();

    /**
     * A getter for rooms
     * @return a set of rooms
     */

    public Set<Room> getRooms() {
        return rooms;
    }

    /**
     * A setter for rooms
     * @param rooms
     */

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }




}
