package org.bearluxury.controllers;

import org.bearluxury.room.Room;
import org.bearluxury.room.RoomBuilder;
import org.bearluxury.room.RoomJDBCDAO;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
/**
 * The room controller provides methods to interact with room data in the database.
 * It utilizes a RoomJDBCDAO object for database operations.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class RoomController {
    private RoomJDBCDAO roomDAO;

    /**
     * Constructs a RoomController with the specified RoomJDBCDAO and pre-fills rooms from a CSV file.
     *
     * @param roomDAO the RoomJDBCDAO instance to be used for database operations
     * @throws SQLException if a database access error occurs
     */
    public RoomController(RoomJDBCDAO roomDAO) throws SQLException {
        this.roomDAO = roomDAO;
        preFillRooms();
    }

    /**
     * Inserts a new room into the database if it doesn't already exist.
     *
     * @param room the room to be inserted
     * @throws SQLException if a room with the same room number already exists in the database
     */
    public void insertRoom(Room room) throws SQLException {
        Optional<Room> existingRoom = roomDAO.get(room.getRoomNumber());

        if (existingRoom.isPresent()) {
            // Room already exists, do not insert again
            System.out.println("Room " + room.getRoomNumber() + " already exists in the database.");
            throw new SQLException();
        } else {
            // Room does not exist, insert it
            roomDAO.insert(room);
        }
    }

    /**
     * Pre-fills rooms in the database from a CSV file.
     *
     * @throws SQLException if a database access error occurs
     */
    private void preFillRooms() throws SQLException {
        RoomBuilder builder = new RoomBuilder("src/main/resources/RoomList.csv");
        for (Room room : builder.getRoomSet()) {
            Optional<Room> existingRoom = roomDAO.get(room.getRoomNumber());

            if (existingRoom.isPresent()) {
                System.out.println("Room " + room.getRoomNumber() + " already exists in the database.");

            } else {
                // Room does not exist, insert it
                roomDAO.insert(room);
            }
        }
    }

    /**
     * Retrieves a room from the database based on its room number.
     *
     * @param roomNumber the room number of the room to retrieve
     * @return an Optional containing the retrieved room, or empty if not found
     */
    public Optional<Room> getRoom(int roomNumber) {
        return roomDAO.get(roomNumber);
    }

    /**
     * Retrieves a set of all rooms stored in the database.
     *
     * @return a Set containing all rooms stored in the database
     */
    public Set<Room> listRooms() {
        return roomDAO.list();
    }

    /**
     * Updates an existing room in the database.
     *
     * @param room the updated room
     * @param roomNumber the room number of the room to be updated
     */
    public void updateRoom(Room room, int roomNumber) {
        roomDAO.update(room, roomNumber);
    }

    /**
     * Deletes a room from the database based on its room number.
     *
     * @param roomNumber the room number of the room to delete
     * @return true if the room was successfully deleted, false otherwise
     */
    public boolean deleteRoom(int roomNumber) {
        return roomDAO.delete(roomNumber);
    }

    /**
     * Clears all rooms from the database.
     */
    public void clearRooms() {
        roomDAO.clear();
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        roomDAO.close();
    }
}