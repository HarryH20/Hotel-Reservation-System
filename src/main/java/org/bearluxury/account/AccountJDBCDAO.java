package org.bearluxury.account;

import org.bearluxury.database.AccountDAO;
import org.bearluxury.database.DAO;

import java.sql.*;
import java.util.*;

public class AccountJDBCDAO implements DAO<Account>, AccountDAO<Account> {

    private Connection connection;

    private static String JDBC_URL = "jdbc:h2:~/account25";

    public AccountJDBCDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            createAccountTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
        }
    }

    private void createAccountTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            boolean tableExists = false;
            try {
                stmt.executeQuery("SELECT * FROM accounts");
                tableExists = true;
            } catch (SQLException e) {
            }

            if (!tableExists) {
                String createTableSQL = "CREATE TABLE accounts (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "firstName VARCHAR(50), " +
                        "lastName VARCHAR(50), " +
                        "userName VARCHAR(50) UNIQUE, " +
                        "email VARCHAR(100) UNIQUE, " +
                        "phoneNumber BIGINT, " +
                        "password VARCHAR(100), " +
                        "role VARCHAR(20) " + // Assuming role names won't exceed 20 characters
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
    public Set<Account> list() {
        Set<Account> accounts = new TreeSet<>(Comparator.comparing(Account::getEmail));
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM accounts")) {
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setFirstName(rs.getString("firstName"));
                account.setLastName(rs.getString("lastName"));
                account.setUserName(rs.getString("userName"));
                account.setEmail(rs.getString("email"));
                account.setPhoneNumber(rs.getLong("phoneNumber"));
                account.setPassword(rs.getString("password"));
                String roleStr = rs.getString("role");
                Role role = Role.valueOf(roleStr.toUpperCase());
                account.setRole(role);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }



    @Override
    public void insert(Account account) {
        String insertSQL = "INSERT INTO accounts (firstName, lastName, userName, email, phoneNumber, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setString(3, account.getUserName());
            pstmt.setString(4, account.getEmail());
            pstmt.setLong(5, account.getPhoneNumber());
            pstmt.setString(6, account.getPassword());
            pstmt.setString(7, account.getRole().toString());
            pstmt.executeUpdate();
        }
        catch (SQLException exc){
            exc.printStackTrace();
        }
    }

    @Override
    public Optional<Account> get(String email) {
        String sql = "SELECT * FROM accounts WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setFirstName(rs.getString("firstName"));
                    account.setLastName(rs.getString("lastName"));
                    account.setUserName(rs.getString("userName"));
                    account.setEmail(rs.getString("email"));
                    account.setPhoneNumber(rs.getLong("phoneNumber"));
                    account.setPassword(rs.getString("password"));
                    String roleStr = rs.getString("role");
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
    public void update(Account account, String email) {
        String sql = "UPDATE accounts SET firstName = ?, lastName = ?, userName = ?, phoneNumber = ?, password = ?, role = ? WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, account.getFirstName());
            pstmt.setString(2, account.getLastName());
            pstmt.setString(3, account.getUserName());
            pstmt.setLong(4, account.getPhoneNumber());
            pstmt.setString(5, account.getPassword());
            pstmt.setString(6, account.getRole().toString());
            pstmt.setString(7, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(String email) {
        String sql = "DELETE FROM accounts WHERE email = ?";
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
            stmt.executeUpdate("DELETE FROM accounts");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
