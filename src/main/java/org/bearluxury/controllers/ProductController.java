package org.bearluxury.controllers;

import org.bearluxury.product.Product;
import org.bearluxury.product.ProductBuilder;
import org.bearluxury.product.ProductJDBCDAO;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

/**
 * The product controller provides methods to interact with product data in the database.
 * It utilizes a ProductJDBCDAO object for database operations.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */

public class ProductController {
    private ProductJDBCDAO productDAO;

    /**
     * Constructs a ProductController with the specified ProductJDBCDAO.
     *
     * @param productDAO the ProductJDBCDAO instance to be used for database operations
     * @throws SQLException if a database access error occurs
     */
    public ProductController(ProductJDBCDAO productDAO) throws SQLException {
        this.productDAO = productDAO;
    }

    /**
     * Inserts a new product into the database.
     *
     * @param product the product to be inserted
     */
    public void insertProduct(Product product){
        productDAO.insert(product);
    }

    /**
     * Retrieves a product from the database based on its ID.
     *
     * @param id the ID of the product to retrieve
     * @return an Optional containing the retrieved product, or empty if not found
     */
    public Optional<Product> getProduct(int id){
        return productDAO.get(id);
    }

    /**
     * Retrieves a set of all products stored in the database.
     *
     * @return a Set containing all products stored in the database
     */
    public Set<Product> listProducts(){
        return productDAO.list();
    }

    /**
     * Deletes a product from the database based on its ID.
     *
     * @param id the ID of the product to delete
     * @return true if the product was successfully deleted, false otherwise
     */
    public boolean deleteProduct(int id){
        return productDAO.delete(id);
    }

    /**
     * Clears all products from the database.
     */
    public void clearProducts(){
        productDAO.clear();
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection(){
        productDAO.close();
    }

    /**
     * Adds stock to a product in the database.
     *
     * @param id     the ID of the product to add stock to
     * @param amount the amount of stock to add
     */
    public void addStock(int id, int amount) {
        productDAO.addStock(id, amount);
    }

    /**
     * Removes stock from a product in the database.
     *
     * @param id     the ID of the product to remove stock from
     * @param amount the amount of stock to remove
     */
    public void removeStock(int id, int amount) {
        productDAO.removeStock(id, amount);
    }

    /**
     * Prefills the database with products from a CSV file.
     *
     * @throws SQLException if a database access error occurs
     */
    public void preFillProducts() throws SQLException {
        ProductBuilder builder = new ProductBuilder("src/main/resources/ProductList.csv");
        int i = 0;
        for(Product product : builder.getProductList()){
            productDAO.insert(product);
            i++;
            System.out.println("Product Inserted " + i);
        }
    }
}