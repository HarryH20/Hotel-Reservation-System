package org.bearluxury;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoomCatalog {
    private List<Room> rooms = new ArrayList<>();

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> sortByPrice(){
        List<Room> roomsByPrice = rooms.stream().sorted
                (Comparator.comparing(room -> room.getPrice())).collect(Collectors.toList());
        return roomsByPrice;
    }



}
