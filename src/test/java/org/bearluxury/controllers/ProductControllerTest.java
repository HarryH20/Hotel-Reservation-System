package org.bearluxury.controllers;

import org.bearluxury.product.PRODUCT_TYPE;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductJDBCDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProductControllerTest {
    @Mock
    private ProductJDBCDAO mockProductDAO;
    private ProductController productController;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(mockProductDAO);
    }

    @Test
    void insertProductTest() {
        Product product = new Product("Underwear", 12.99, 3, PRODUCT_TYPE.CLOTHING);
        productController.insertProduct(product);
        verify(mockProductDAO, times(1)).insert(product);
    }

    @Test
    void getProductTest() {
        Product product = new Product("Underwear", 12.99, 3, PRODUCT_TYPE.CLOTHING);
        when(mockProductDAO.get(product.getId())).thenReturn(Optional.of(product));
        Optional<Product> retrievedProduct = productController.getProduct(product.getId());
        assertTrue(retrievedProduct.isPresent());
        assertEquals(product, retrievedProduct.get());
    }

    @Test
    void listProductsTest() {
        Set<Product> products = new HashSet<>();
        products.add(new Product("Underwear", 12.99, 3, PRODUCT_TYPE.CLOTHING));
        products.add(new Product("product2", 10.99, 2, PRODUCT_TYPE.ACCESSORY));
        when(mockProductDAO.list()).thenReturn(products);
        Set<Product> retrievedProducts = productController.listProducts();
        assertEquals(products, retrievedProducts);
    }

    @Test
    void deleteProductTest() {
        Product product = new Product("Underwear", 12.99, 3, PRODUCT_TYPE.CLOTHING);
        when(mockProductDAO.delete(product.getId())).thenReturn(true);
        assertTrue(productController.deleteProduct(product.getId()));
        verify(mockProductDAO, times(1)).delete(product.getId());
    }

    @Test
    void clearProductsTest() {
        productController.clearProducts();
        verify(mockProductDAO, times(1)).clear();
    }

    @Test
    void closeConnectionTest() {
        productController.closeConnection();
        verify(mockProductDAO, times(1)).close();
    }

    //TODO: This test is failing
    @Test
    void addStockTest() {
        int productId = 1;
        int initialStock = 10;
        int stockToAdd = 5;

        Product product = new Product("Test Product", 10.0, initialStock, PRODUCT_TYPE.CLOTHING);

        when(mockProductDAO.get(productId)).thenReturn(Optional.of(product));
        productController.addStock(productId, stockToAdd);

        Optional<Product> retrievedProduct = productController.getProduct(productId);
        assertTrue(retrievedProduct.isPresent(), "Product should be present after adding stock");

        Product updatedProduct = retrievedProduct.get();

        assertEquals(initialStock + stockToAdd, updatedProduct.getQuantity(), "Stock quantity should be updated correctly");
        verify(mockProductDAO, times(1)).addStock(productId, stockToAdd);
    }

    //TODO: This test is failing
    @Test
    void removeStockTest() {
        int productId = 1;
        int initialStock = 10;
        int stockToRemove = 3;

        Product product = new Product("Test Product", 10.0, initialStock, PRODUCT_TYPE.CLOTHING);

        when(mockProductDAO.get(productId)).thenReturn(Optional.of(product));
        productController.removeStock(productId, stockToRemove);

        Optional<Product> retrievedProduct = productController.getProduct(productId);
        assertTrue(retrievedProduct.isPresent(), "Product should be present after removing stock");

        Product updatedProduct = retrievedProduct.get();

        assertEquals(initialStock - stockToRemove, updatedProduct.getQuantity(), "Stock quantity should be updated correctly");
        verify(mockProductDAO, times(1)).removeStock(productId, stockToRemove);
    }
}
