package org.bearluxury.shop;

import org.bearluxury.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
    private static int lastSaleId = 0; // Static variable to track the last assigned sale ID
    private int saleId;
    private Date saleDate;
    private List<Product> productsSold;
    private double totalPrice;

    // Constructor
    public Sale() {
        this.saleId = ++lastSaleId; // Increment the last assigned sale ID and assign it to the current sale
        this.saleDate = new Date(); // Current date and time
        this.productsSold = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    // Method to add a product to the sale
    public void addProduct(Product product) {
        productsSold.add(product);
        totalPrice += product.getPrice();
    }

    // Method to remove a product from the sale
    public void removeProduct(Product product) {
        productsSold.remove(product);
        totalPrice -= product.getPrice();
    }

    // Getter methods
    public int getSaleId() {
        return saleId;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public List<Product> getProductsSold() {
        return productsSold;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}