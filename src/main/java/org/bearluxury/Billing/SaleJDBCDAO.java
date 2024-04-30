package org.bearluxury.Billing;

import org.bearluxury.database.DAO;
import org.bearluxury.shop.Sale;

import java.sql.*;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Each SaleJDBCDAO object stores
 * the data in a database for a sale object
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */

public class SaleJDBCDAO implements DAO<Sale> {

    private Connection connection;

    private static String JDBC_URL = "jdbc:h2:~/testingSales";

    /**
     * connects to a new database and creates a table for it
     */
    public SaleJDBCDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            createSaleTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
        }
    }

    /**
     * builds a sale table if it doesn't already exist
     */
    private void createSaleTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            boolean tableExists = false;
            try {
                stmt.executeQuery("SELECT * FROM sale");
                tableExists = true;
            } catch (SQLException e) {
            }

            if (!tableExists) {
                String createTableSQL = "CREATE TABLE sale (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "acctId INT, " +
                        "saleDate DATE,"+
                        "productName VARCHAR(50), " +
                        "price DOUBLE PRECISION," +
                        "quantity INT " +
                        ")";



                stmt.executeUpdate(createTableSQL);
            } else {
                System.out.println("Sale table already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * lists the sales that are in the database
     * @return the list of sales in the database
     */
    @Override
    public Set<Sale> list() {
        Set<Sale> sales = new TreeSet<>(Comparator.comparing(Sale::getSaleId));
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM sale")) {
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSaleId(rs.getInt("id"));
                sale.setAccountId(rs.getInt("acctId"));
                sale.setSaleDate(rs.getDate("saleDate"));
                sale.setProductName(rs.getString("productName"));
                sale.setPrice(rs.getDouble("price"));
                sale.setQuantity(rs.getInt("quantity"));
                sales.add(sale);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    /**
     * inserts a sale into the database
     * @param sale the sale to insert
     * @throws SQLException
     */
    @Override
    public void insert(Sale sale) throws SQLException {
        String insertSQL = "INSERT INTO sale (acctId, saleDate,productName, price, quantity) VALUES (?,?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, sale.getAccountId());
            pstmt.setDate(2, new java.sql.Date(sale.getSaleDate().getTime()));
            pstmt.setString(3, sale.getProductName());
            pstmt.setDouble(4, sale.getPrice());
            pstmt.setInt(5, sale.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * closes the database connection
     */
    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * clears the entire database
     */
    @Override
    public void clear() {
        String clearSQL = "DELETE FROM sale";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(clearSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * lists the sales by the account id in the table
     * @param accountId the id to search with
     * @return the set of sales associated with an account
     */

    public Set<Sale> listSalesByAccountId(int accountId) {
        Set<Sale> sales = new TreeSet<>(Comparator.comparing(Sale::getSaleId));
        String query = "SELECT * FROM sale WHERE acctId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleId(rs.getInt("id"));
                    sale.setAccountId(rs.getInt("acctId"));
                    sale.setSaleDate(rs.getDate("saleDate"));
                    sale.setProductName(rs.getString("productName"));
                    sale.setPrice(rs.getDouble("price"));
                    sale.setQuantity(rs.getInt("quantity"));
                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    /**
     * deletes the sales for an account
     * @param accountId the accountId to search with
     */
    public void deleteSalesByAccountId(int accountId) {
        String deleteSQL = "DELETE FROM sale WHERE acctId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, accountId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deletes a sale by the sale id
     * @param saleId the id of the sale
     */
    public void deleteSaleById(int saleId) {
        String deleteSQL = "DELETE FROM sale WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, saleId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
