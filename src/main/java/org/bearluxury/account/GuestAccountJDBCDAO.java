package org.bearluxury.account;

import org.bearluxury.account.Account;
import org.bearluxury.account.CreditCard;
import org.bearluxury.account.Guest;
import org.bearluxury.account.Role;
import org.bearluxury.database.AccountDAO;
import org.bearluxury.database.DAO;

import java.sql.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class GuestAccountJDBCDAO implements DAO<Guest>, AccountDAO<Guest> {
    private Connection connection;
    private static String JDBC_URL = "jdbc:h2:~/guest1";
    public GuestAccountJDBCDAO(){
        try{
            connection = DriverManager.getConnection(JDBC_URL);
            createGuestTableIfNotExists();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
    private void createGuestTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            boolean tableExists = false;
            try {
                stmt.executeQuery("SELECT * FROM guests");
                tableExists = true;
            } catch (SQLException e) {
            }

            if (!tableExists) {
                String createTableSQL = "CREATE TABLE guests (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "firstName VARCHAR(50), " +
                        "lastName VARCHAR(50), " +
                        "userName VARCHAR(50) UNIQUE, " +
                        "email VARCHAR(100) UNIQUE, " +
                        "phoneNumber BIGINT, " +
                        "password VARCHAR(100), " +
                        "role VARCHAR(20), " +
                        "cardNumber VARCHAR(20),"+
                        "cardHolderName VARCHAR(50),"+
                        "expDate varchar(20),"+
                        "cvv varchar(15)"+
                        ")";



                stmt.executeUpdate(createTableSQL);
            } else {
                System.out.println("Account Table Already Exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Set<Guest> list() {
        Set<Guest> accounts = new TreeSet<>(Comparator.comparing(Account::getEmail));
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM guests")) {
            while (rs.next()) {
                Guest account = new Guest();
                account.setId(rs.getInt("id"));
                account.setFirstName(rs.getString("firstName"));
                account.setLastName(rs.getString("lastName"));
                account.setUserName(rs.getString("userName"));
                account.setEmail(rs.getString("email"));
                account.setPhoneNumber(rs.getLong("phoneNumber"));
                account.setPassword(rs.getString("password"));
                String roleStr = rs.getString("role");
                Role role = Role.valueOf(roleStr.toUpperCase());
                account.setCreditCard(new CreditCard(rs.getString("cardNumber"),
                        rs.getString("cardHolderName"),
                        rs.getString("expDate"),
                        rs.getString("cvv")));
                account.setRole(role);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }


    @Override
    public void insert(Guest account) {
        String insertSQL = "INSERT INTO guests (firstName, lastName, userName, email, phoneNumber, password, role, " +
                "cardNumber, cardHolderName, expDate, cvv) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setString(3, account.getUserName());
            pstmt.setString(4, account.getEmail());
            pstmt.setLong(5, account.getPhoneNumber());
            pstmt.setString(6, account.getPassword());
            pstmt.setString(7, account.getRole().toString());
            pstmt.setString(8,account.getCreditCard().getCardNumber());
            pstmt.setString(9,account.getCreditCard().getCardHolderName());
            pstmt.setString(10, account.getCreditCard().getExpDate());
            pstmt.setString(11,account.getCreditCard().getCvv());

            pstmt.executeUpdate();
        }
        catch (SQLException exc){
            exc.printStackTrace();
        }
    }

    @Override
    public Optional<Guest> get(String email) {
        String sql = "SELECT * FROM guests WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Guest account = new Guest();
                    account.setId(rs.getInt("id"));
                    account.setFirstName(rs.getString("firstName"));
                    account.setLastName(rs.getString("lastName"));
                    account.setUserName(rs.getString("userName"));
                    account.setEmail(rs.getString("email"));
                    account.setPhoneNumber(rs.getLong("phoneNumber"));
                    account.setPassword(rs.getString("password"));
                    String roleStr = rs.getString("role");
                    account.setCreditCard(new CreditCard(rs.getString("cardNumber"),
                            rs.getString("cardHolderName"),
                            rs.getString("expDate"),
                            rs.getString("cvv")));

                    Role role = Role.valueOf(roleStr.toUpperCase());
                    account.setRole(role);
                    return Optional.of(account);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Guest account, String email) {
        String sql = "UPDATE guests SET firstName = ?, lastName = ?, userName = ?, phoneNumber = ?, password = ?, role = ?, " +
                "cardNumber = ?, cardHolderName = ?, expDate = ?, cvv = ?WHERE email = ? ";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setString(3, account.getUserName());
            pstmt.setLong(4, account.getPhoneNumber());
            pstmt.setString(5, account.getPassword());
            pstmt.setString(6, account.getRole().toString());
            pstmt.setString(7,account.getCreditCard().getCardNumber());
            pstmt.setString(8,account.getCreditCard().getCardHolderName());
            pstmt.setString(9, account.getCreditCard().getExpDate());
            pstmt.setString(10,account.getCreditCard().getCvv());
            pstmt.setString(11, email);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(String email) {
        String sql = "DELETE FROM guests WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM guests");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
