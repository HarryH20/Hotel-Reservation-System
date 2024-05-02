package org.bearluxury.product;


import org.bearluxury.database.DAO;
import org.bearluxury.database.ProductDAO;

import java.sql.*;
import java.util.*;

/**
 * The ProductJDBCDAO class implements both DAO and ProductDAO interfaces to interact with a database
 * and perform CRUD operations on Product objects.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class ProductJDBCDAO implements DAO<Product>, ProductDAO<Product> {

    private Connection connection;

    private static final String JDBC_URL = "jdbc:h2:~/testingProduct00";

    /**
     * Constructs a ProductJDBCDAO object and establishes a connection to the database.
     */
    public ProductJDBCDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            createProductTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
        }
    }

    /**
     * Creates the product table in the database if it does not exist.
     */
    private void createProductTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            boolean tableExists = false;
            try {
                stmt.executeQuery("SELECT * FROM products");
                tableExists = true;
            } catch (SQLException e) {
            }

            if (!tableExists) {
                String createTableSQL = "CREATE TABLE products (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "price DOUBLE, " +
                        "quantity INT, " +
                        "productType VARCHAR(255)" +
                        ")";

                stmt.executeUpdate(createTableSQL);
                System.out.println("Product table created successfully!");
            } else {
                System.out.println("Product table already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a set of all products from the database.
     *
     * @return a set of all products
     */
    @Override
    public Set<Product> list() {
        Set<Product> products = new TreeSet<>(Comparator.comparing(Product::getId));
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id")); // Assuming the id column exists in your database
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                String productTypeStr = rs.getString("productType");
                PRODUCT_TYPE productType = PRODUCT_TYPE.valueOf(productTypeStr);

                product.setProductType(productType);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    /**
     * Inserts a new product into the database.
     *
     * @param product the product to insert
     */
    @Override
    public void insert(Product product) {
        try {
            String sql = "INSERT INTO products (name, price, quantity, productType) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getProductType().name());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new product was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a product from the database by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return an optional containing the retrieved product, or empty if not found
     */
    @Override
    public Optional<Product> get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM products WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String productTypeStr = resultSet.getString("productType");
                PRODUCT_TYPE productType = PRODUCT_TYPE.valueOf(productTypeStr);

                Product product = new Product(name, price, quantity, productType);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Updates an existing product in the database.
     *
     * @param product the updated product
     * @param id the ID of the product to update
     */
    @Override
    public void update(Product product, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET name = ?, price = ?, quantity = ?, productType = ? WHERE id = ?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getProductType().name());
            preparedStatement.setInt(5, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("No product found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds stock to an existing product in the database.
     *
     * @param id the ID of the product to add stock to
     * @param amount the amount of stock to add
     */
    public void addStock(int id, int amount) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET quantity = quantity + ? WHERE id = ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(amount + " items added to stock successfully!");
            } else {
                System.out.println("No product found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes stock from an existing product in the database.
     *
     * @param id the ID of the product to remove stock from
     * @param amount the amount of stock to remove
     */
    public void removeStock(int id, int amount) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, amount);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(amount + " items removed from stock successfully!");
            } else {
                System.out.println("Not enough stock to remove or no product found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Deletes a product from the database by its ID.
     *
     * @param id the ID of the product to delete
     * @return true if the product was successfully deleted, false otherwise
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?");
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
     * Closes the database connection.
     */
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

    /**
     * Clears all data from the product table in the database.
     */
    @Override
    public void clear() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products");
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " rows deleted from products table.");
        } catch (SQLException e) {
            System.out.println("Failed to clear the database!");
            e.printStackTrace();
        }
    }
}

