package org.bearluxury.room;

import org.bearluxury.DAO;
import org.bearluxury.room.BED_TYPE;
import org.bearluxury.room.QUALITY_LEVEL;
import org.bearluxury.room.ROOM_TYPE;
import org.bearluxury.room.Room;

import java.sql.*;
import java.util.*;

public class RoomJDBCDAO implements DAO<Room> {
    private Connection connection;

    private static String JDBC_URL = "jdbc:h2:mem:roomdb";

    public RoomJDBCDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            createRoomTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
        }
    }

    private void createRoomTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            boolean tableExists = false;
            try {
                stmt.executeQuery("SELECT * FROM rooms");
                tableExists = true;
            } catch (SQLException e) {
            }

            if (!tableExists) {
                String createTableSQL = "CREATE TABLE rooms (" +
                        "roomNumber INT PRIMARY KEY, " +
                        "price DOUBLE, " +
                        "canSmoke BOOLEAN, " +
                        "roomType VARCHAR(50), " +
                        "bedType VARCHAR(50), " +
                        "qualityLevel VARCHAR(50), " +
                        "numberOfBeds INT" +
                        ")";


                stmt.executeUpdate(createTableSQL);
            } else {
                System.out.println("Employee table already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Set<Room> list() {
        Set<Room> rooms = new TreeSet<>(Comparator.comparing(Room::getRoomNumber));
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms");

            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("roomNumber");
                double price = resultSet.getDouble("price");
                boolean canSmoke = resultSet.getBoolean("canSmoke");
                String roomTypeString = resultSet.getString("roomType");
                String bedTypeString = resultSet.getString("bedType");
                String qualityLevelString = resultSet.getString("qualityLevel");
                int numberOfBeds = resultSet.getInt("numberOfBeds");

                ROOM_TYPE roomType = ROOM_TYPE.valueOf(roomTypeString.toUpperCase());
                BED_TYPE bedType = BED_TYPE.valueOf(bedTypeString.toUpperCase());
                QUALITY_LEVEL qualityLevel = QUALITY_LEVEL.valueOf(qualityLevelString.toUpperCase());

                Room room = new Room(roomNumber, price, canSmoke, roomType, bedType, qualityLevel, numberOfBeds);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }


    @Override
    public void insert(Room room) {
        try {
            String sql = "INSERT INTO rooms(roomNumber, price, canSmoke, roomType, bedType, qualityLevel, numberOfBeds) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setBoolean(3, room.isCanSmoke());
            preparedStatement.setString(4, room.getRoomType().csvFormat());
            preparedStatement.setString(5, room.getBed().toString());
            preparedStatement.setString(6, room.getQualityLevel().csvFormat());
            preparedStatement.setInt(7, room.getNumberOfBeds());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new room was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Room> get(int roomNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rooms WHERE roomNumber = ?");
            preparedStatement.setInt(1, roomNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roomNumberResult = resultSet.getInt("roomNumber");
                double price = resultSet.getDouble("price");
                boolean canSmoke = resultSet.getBoolean("canSmoke");
                ROOM_TYPE roomType = ROOM_TYPE.valueOf(resultSet.getString("roomType").toUpperCase());
                BED_TYPE bedType = BED_TYPE.valueOf(resultSet.getString("bedType").toUpperCase());
                QUALITY_LEVEL qualityLevel = QUALITY_LEVEL.valueOf(resultSet.getString("qualityLevel").toUpperCase());
                int numberOfBeds = resultSet.getInt("numberOfBeds");

                Room room = new Room(roomNumberResult, price, canSmoke, roomType, bedType, qualityLevel, numberOfBeds);
                return Optional.of(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Room room, int roomNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE rooms SET roomNumber = ?, price = ?, canSmoke = ?, roomType = ?, bedType = ?, qualityLevel = ?, numberOfBeds = ? WHERE roomNumber = ?");
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setBoolean(3, room.isCanSmoke());
            preparedStatement.setString(4, room.getRoomType().csvFormat());
            preparedStatement.setString(5, room.getBed().toString());
            preparedStatement.setString(6, room.getQualityLevel().csvFormat());
            preparedStatement.setInt(7, room.getNumberOfBeds());
            preparedStatement.setInt(8, roomNumber);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Room updated successfully!");
            } else {
                System.out.println("No room found with room number: " + roomNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int roomNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM rooms WHERE roomNumber=?");
            statement.setInt(1, roomNumber);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection!");
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM rooms");
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " rows deleted from rooms table.");
        } catch (SQLException e) {
            System.out.println("Failed to clear the database!");
            e.printStackTrace();
        }
    }

}
