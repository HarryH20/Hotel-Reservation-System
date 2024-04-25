package org.bearluxury.shop;

import org.bearluxury.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
    private static int lastSaleId = 0; // Static variable to track the last assigned sale ID
    private int saleId;
    private int accountId;
    private Date saleDate;
    private String productName;
    private double price;
    private boolean isPaid;

    private int quantity;

    // Constructor
    public Sale() {
        this.saleId = ++lastSaleId; // Increment the last assigned sale ID and assign it to the current sale
        this.saleDate = new Date(); // Current date and time
        this.price = 0.0;
    }

//    // Method to add a product to the sale
//    public void addProduct(SaleLineItem lineItem) {
//        productsSold.add(lineItem);
//        //totalPrice += lineItem.getPrice();
//    }
//
//    // Method to remove a product from the sale
//    public void removeProduct(Product product) {
//        productsSold.remove(product);
//        totalPrice -= product.getPrice();
//    }

    // Getter methods
    public int getSaleId() {
        return saleId;
    }

    public Date getSaleDate() {
        return saleDate;
    }


    public void setIsPaid() {
        this.isPaid = true;
    }

    public static int getLastSaleId() {
        return lastSaleId;
    }

    public static void setLastSaleId(int lastSaleId) {
        Sale.lastSaleId = lastSaleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
