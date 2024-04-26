package org.bearluxury.Billing;

import org.bearluxury.database.DAO;
import org.bearluxury.shop.Sale;

import java.sql.*;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class SaleJDBCDAO implements DAO<Sale> {

    private Connection connection;

    private static String JDBC_URL = "jdbc:h2:~/sale3";

    public SaleJDBCDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            createSaleTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
        }
    }

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

    @Override
    public void clear() {
        String clearSQL = "DELETE FROM sale";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(clearSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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


}
