package org.bearluxury.reservation;

import java.sql.*;
import java.util.*;
import java.util.Date;
import org.bearluxury.DAO;
import org.bearluxury.RoomResDAO;
import org.bearluxury.state.SessionManager;

public class ReservationJDBCDAO implements DAO<Reservation>, RoomResDAO<Reservation> {

    private Connection connection;

    private static String JDBC_URL = "jdbc:h2:~/reservation22";

    public ReservationJDBCDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            createReservationTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
        }
    }

    private void createReservationTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            boolean tableExists = false;
            try {
                stmt.executeQuery("SELECT * FROM reservations");
                tableExists = true;
            } catch (SQLException e) {
            }

            if (!tableExists) {
                String createTableSQL = "CREATE TABLE reservations (" +
                        "reservationId INT AUTO_INCREMENT PRIMARY KEY, " +
                        "id INT, " +
                        "roomNumber INT, " +
                        "firstName VARCHAR(255), " +
                        "lastName VARCHAR(255), " +
                        "email VARCHAR(255), " +
                        "numberOfGuests INT, " +
                        "startDate DATE, " +
                        "endDate DATE" +
                        ")";

                stmt.executeUpdate(createTableSQL);
                System.out.println("Table created successfully.");
            } else {
                System.out.println("Reservation table already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Set<Reservation> list() {
        Set<Reservation> reservations = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations");

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservationId");
                int id = resultSet.getInt("id");
                int roomNumber = resultSet.getInt("roomNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int numberOfGuests = resultSet.getInt("numberOfGuests");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate);
                reservation.setID(id);
                reservation.setReservationID(reservationId);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void insert(Reservation reservation) {
        try {
            String sql = "INSERT INTO reservations (id, roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            int accountId = SessionManager.getInstance().getCurrentAccount().getId();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, reservation.getRoomNumber());
            preparedStatement.setString(3, reservation.getFirstName());
            preparedStatement.setString(4, reservation.getLastName());
            preparedStatement.setString(5, reservation.getEmail());
            preparedStatement.setInt(6, reservation.getNumberOfGuests());
            preparedStatement.setDate(7, new java.sql.Date(reservation.getStartDate().getTime()));
            preparedStatement.setDate(8, new java.sql.Date(reservation.getEndDate().getTime()));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int reservationId = generatedKeys.getInt(1);
                    reservation.setReservationID(reservationId);
                    System.out.println("A new reservation was inserted successfully with ID: " + reservationId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Optional<Reservation> get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reservations WHERE id = ?");
            int accountId = SessionManager.getInstance().getCurrentAccount().getId();
            preparedStatement.setInt(1,accountId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("roomNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int numberOfGuests = resultSet.getInt("numberOfGuests");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate);
                reservation.setID(accountId);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Reservation reservation, int id) {
        try {
            String sql = "UPDATE reservations SET roomNumber = ?, firstName = ?, lastName = ?, email = ?, numberOfGuests = ?, startDate = ?, endDate = ? WHERE id = ?";

            int accountId = SessionManager.getInstance().getCurrentAccount().getId();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(1, reservation.getRoomNumber());
            preparedStatement.setString(2, reservation.getFirstName());
            preparedStatement.setString(3, reservation.getLastName());
            preparedStatement.setString(4, reservation.getEmail());
            preparedStatement.setInt(5, reservation.getNumberOfGuests());
            preparedStatement.setDate(6, new java.sql.Date(reservation.getStartDate().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(reservation.getEndDate().getTime()));
            preparedStatement.setInt(8, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("No reservation found with id: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations WHERE id = ?");
            statement.setInt(1, id);
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
            connection.close();
        }catch (SQLException exc){
            exc.printStackTrace();
        }

    }

    @Override
    public void clear() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reservations");
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " rows deleted from reservations table.");
        } catch (SQLException e) {
            System.out.println("Failed to clear the database!");
            e.printStackTrace();
        }
    }

    public Set<Reservation> list(int accountId) {
        Set<Reservation> reservations = new HashSet<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservations WHERE id = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservationId");
                int id = resultSet.getInt("id");
                int roomNumber = resultSet.getInt("roomNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int numberOfGuests = resultSet.getInt("numberOfGuests");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate);
                reservation.setID(id);
                reservation.setReservationID(reservationId);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    public Optional<Reservation> getByReservationId(int reservationId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reservations WHERE reservationId = ?");
            preparedStatement.setInt(1, reservationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int roomNumber = resultSet.getInt("roomNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int numberOfGuests = resultSet.getInt("numberOfGuests");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate);
                reservation.setID(id);
                reservation.setReservationID(reservationId);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean deleteByReservationId(int reservationId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations WHERE reservationId = ?");
            statement.setInt(1, reservationId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateByReservationId(Reservation reservation, int reservationId) {
        try {
            String sql = "UPDATE reservations SET roomNumber = ?, firstName = ?, lastName = ?, email = ?, numberOfGuests = ?, startDate = ?, endDate = ? WHERE reservationId = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, reservation.getRoomNumber());
            preparedStatement.setString(2, reservation.getFirstName());
            preparedStatement.setString(3, reservation.getLastName());
            preparedStatement.setString(4, reservation.getEmail());
            preparedStatement.setInt(5, reservation.getNumberOfGuests());
            preparedStatement.setDate(6, new java.sql.Date(reservation.getStartDate().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(reservation.getEndDate().getTime()));
            preparedStatement.setInt(8, reservationId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("No reservation found with reservationId: " + reservationId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

