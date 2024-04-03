package org.bearluxury;

import com.sun.source.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

class RoomCatalog {
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
