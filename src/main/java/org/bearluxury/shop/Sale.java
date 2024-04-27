package org.bearluxury.shop;

import org.bearluxury.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
    private int saleId;
    private int accountId;
    private Date saleDate;
    private String productName;
    private double price;
    private int quantity;

    private int reservationId;


    public Sale(){}
    public Sale(Date saleDate, String productName, double price, int quantity) {
        this.saleDate = saleDate;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
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

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
}
