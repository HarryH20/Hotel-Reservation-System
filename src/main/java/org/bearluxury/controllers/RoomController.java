package org.bearluxury.controllers;

import org.bearluxury.room.Room;
import org.bearluxury.room.RoomBuilder;
import org.bearluxury.room.RoomJDBCDAO;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class RoomController {
    private RoomJDBCDAO roomDAO;

    public RoomController(RoomJDBCDAO roomDAO) throws SQLException {
        this.roomDAO = roomDAO;
        preFillRooms();
    }
    public void insertRoom(Room room) throws SQLException {
        roomDAO.insert(room);
    }
    private void preFillRooms() throws SQLException {
        roomDAO.clear();
        RoomBuilder builder = new RoomBuilder("src/main/resources/RoomList.csv");
        for(Room room : builder.getRoomList()){
            roomDAO.insert(room);
        }

    }

    public Optional<Room> getRoom(int roomNumber) {
        return roomDAO.get(roomNumber);
    }

    public Set<Room> listRooms() {
        return roomDAO.list();
    }

    public void updateRoom(Room room, int roomNumber) {
        roomDAO.update(room, roomNumber);
    }

    public boolean deleteRoom(int roomNumber) {
        return roomDAO.delete(roomNumber);
    }

    public void clearRooms() {
        roomDAO.clear();
    }

    public void closeConnection() {
        roomDAO.close();
    }

}
