package org.bearluxury.controllers;

import org.bearluxury.ProductDAO;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductBuilder;
import org.bearluxury.product.ProductJDBCDAO;
import org.bearluxury.room.Room;
import org.bearluxury.room.RoomBuilder;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class ProductController {
    private ProductJDBCDAO productDAO;

    public ProductController(ProductJDBCDAO productDAO) throws SQLException {
        this.productDAO = productDAO;
    }

    public void insertProduct(Product product){
        productDAO.insert(product);
    }

    public Optional<Product> getProduct(int id){
        return productDAO.get(id);
    }

    public Set<Product> listProducts(){
        return productDAO.list();
    }

    public boolean deleteProduct(int id){
        return productDAO.delete(id);
    }

    public void clearProducts(){
        productDAO.clear();
    }

    public void closeConnection(){
        productDAO.close();
    }

    public void addStock(int id, int amount) {
        productDAO.addStock(id, amount);
    }

    public void removeStock(int id, int amount) {
        productDAO.removeStock(id, amount);
    }

    public void preFillProducts() throws SQLException {
        ProductBuilder builder = new ProductBuilder("src/main/resources/ProductList.csv");
        ProductJDBCDAO productDAO = new ProductJDBCDAO();
        int i = 0;
        for(Product product : builder.getProductList()){
            productDAO.insert(product);
            i++;
            System.out.println("Product Inserted " + i);

        }

    }
}
