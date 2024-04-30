package org.bearluxury.controllers;

import org.bearluxury.account.CreditCard;
import org.bearluxury.account.Guest;
import org.bearluxury.account.Role;
import org.bearluxury.product.PRODUCT_TYPE;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductJDBCDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Optional;

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
        //idk if this is how this works
        when(mockProductDAO.get(product.getId())).thenReturn(Optional.of(product));
        Optional<Product> retrievedProduct = productController.getProduct(product.getId());
        assertTrue(retrievedProduct.isPresent());
        assertEquals(product, retrievedProduct.get());
    }
}
