package org.bearluxury.reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;
import org.bearluxury.database.DAO;
import org.bearluxury.database.RoomResDAO;
import org.bearluxury.state.SessionManager;

/**
 * The ReservationJDBCDAO class implements both DAO and RoomResDAO interfaces to interact with a database
 *  * and perform CRUD operations on Reservation objects.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class ReservationJDBCDAO implements DAO<Reservation>, RoomResDAO<Reservation> {

    private Connection connection;

    private static String JDBC_URL = "jdbc:h2:~/testingReservations2222";

    /**
     * Default constructor. Establishes a connection to the database
     */
    public ReservationJDBCDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            createReservationTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
        }
    }

    /**
     * Creates a table in the database for reservations.
     */
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
                        "endDate DATE," +
                        "checkedIn BIT" +
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

    /**
     * Retrieves a set of all reservations in the database.
     *
     * @return a set of all reservations
     */
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
                boolean checkedIn = resultSet.getBoolean("checkedIn");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate,checkedIn);
                reservation.setID(id);
                reservation.setReservationID(reservationId);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Inserts a reservation into the database.
     *
     * @param reservation the object to be inserted into the database
     */
    @Override
    public void insert(Reservation reservation) {
        try {
            String sql = "INSERT INTO reservations (id, roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate, checkedIn) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";

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
            preparedStatement.setBoolean(9, reservation.isCheckedIn());

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

    /**
     * Retrieves a reservation from the database given its id.
     *
     * @param id the value used to search for the room reservation (e.g., reservation ID)
     * @return the reservation found by the id
     */
    @Override
    public Optional<Reservation> get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reservations WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roomNumber = resultSet.getInt("roomNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int numberOfGuests = resultSet.getInt("numberOfGuests");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                boolean checkedIn = resultSet.getBoolean("checkedIn");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate, checkedIn);
                reservation.setID(id);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Updates a current reservation in the database with a new reservation.
     *
     * @param reservation the updated room reservation object
     * @param id the value used to identify the room reservation to be updated
     */
    @Override
    public void update(Reservation reservation, int id) {
        try {
            String sql = "UPDATE reservations SET roomNumber = ?, firstName = ?, lastName = ?, email = ?, numberOfGuests = ?, startDate = ?, endDate = ?, checkedIn = ? WHERE reservationId = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, reservation.getRoomNumber());
            preparedStatement.setString(2, reservation.getFirstName());
            preparedStatement.setString(3, reservation.getLastName());
            preparedStatement.setString(4, reservation.getEmail());
            preparedStatement.setInt(5, reservation.getNumberOfGuests());
            preparedStatement.setDate(6, new java.sql.Date(reservation.getStartDate().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(reservation.getEndDate().getTime()));
            preparedStatement.setBoolean(8, reservation.isCheckedIn());
            preparedStatement.setInt(9, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("No reservation found with reservationId: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a reservation in the database.
     *
     * @param id the value used to identify the room reservation to be deleted
     * @return true if reservation is deleted, false otherwise
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reservations WHERE reservationId = ?");
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

    /**
     * Closes the connection to the database.
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Clears all reservations in the database.
     */
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

    /**
     * Lists the reservations tied to a certain account in the database.
     *
     * @param accountId the account id to find the tied reservations
     * @return a set of reservations tied with the account id
     */
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
                boolean checkedIn = resultSet.getBoolean("checkedIn");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate,checkedIn);
                reservation.setID(id);
                reservation.setReservationID(reservationId);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    /**
     * Retrieves a reservation given its id
     *
     * @param reservationId the reservation id to be searched
     * @return the reservation tied with the id
     */
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
                boolean checkedIn = resultSet.getBoolean("checkedIn");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate,checkedIn);
                reservation.setID(id);
                reservation.setReservationID(reservationId);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * deletes a reservation in the database given its id
     *
     * @param reservationId the reservation id to be searched and deleted
     * @return true if reservation is deleted, false otherwise
     */
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

    /**
     * Updates an existing reservation in the database given its reservation id and new reservation.
     *
     * @param reservation the new reservation to replace the existing one
     * @param reservationId the reservation id to be found
     */
    public void updateByReservationId(Reservation reservation, int reservationId) {
        try {
            String sql = "UPDATE reservations SET roomNumber = ?, firstName = ?, lastName = ?, email = ?, numberOfGuests = ?, startDate = ?, endDate = ?, checkedIn = ? WHERE reservationId = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, reservation.getRoomNumber());
            preparedStatement.setString(2, reservation.getFirstName());
            preparedStatement.setString(3, reservation.getLastName());
            preparedStatement.setString(4, reservation.getEmail());
            preparedStatement.setInt(5, reservation.getNumberOfGuests());
            preparedStatement.setDate(6, new java.sql.Date(reservation.getStartDate().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(reservation.getEndDate().getTime()));
            preparedStatement.setBoolean(8,reservation.isCheckedIn());
            preparedStatement.setInt(9, reservationId);



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

    /**
     * Checks if a new reservation has conflicting information with existing reservations in the database.
     *
     * @param roomNumber the room number to be checked
     * @param startDate the start date to be checked
     * @param endDate the end date to be checked
     * @return true if conflicts occur, false otherwise
     */
    public boolean hasConflictingReservations(int roomNumber, LocalDate startDate, LocalDate endDate) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reservations WHERE roomNumber = ? AND ((startDate <= ? AND endDate >= ?) OR (startDate <= ? AND endDate >= ?) OR (startDate >= ? AND endDate <= ?))");
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(4, java.sql.Date.valueOf(endDate));
            preparedStatement.setDate(5, java.sql.Date.valueOf(endDate));
            preparedStatement.setDate(6, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(7, java.sql.Date.valueOf(endDate));

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If there is any result, there are conflicting reservations
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

