package org.bearluxury.room;

import org.bearluxury.room.Room;

import java.util.*;
import java.util.stream.Collectors;

public class RoomCatalog {
    private Set<Room> rooms = new TreeSet<>();

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> sortByPrice(){
        List<Room> roomsByPrice = rooms.stream().sorted
                (Comparator.comparing(room -> room.getPrice())).collect(Collectors.toList());
        return roomsByPrice;
    }



}
