package org.bearluxury.shop;

import java.util.Date;

/**
 * The Sale class represents a sale transaction in the shop.
 * It contains information about the sale, such as the sale ID, account ID, sale date,
 * product name, price, quantity, and reservation ID.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class Sale {
    private int saleId;
    private int accountId;
    private Date saleDate;
    private String productName;
    private double price;
    private int quantity;

    private int reservationId;


    /**
     * Constructs an empty Sale object.
     */
    public Sale(){}

    /**
     * Constructs a Sale object with the specified sale date, product name, price, and quantity.
     *
     * @param saleDate    The date of the sale.
     * @param productName The name of the product sold.
     * @param price       The price of the product.
     * @param quantity    The quantity of the product sold.
     */
    public Sale(Date saleDate, String productName, double price, int quantity) {
        this.saleDate = saleDate;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Gets the sale ID.
     *
     * @return The sale ID.
     */
    public int getSaleId() {
        return saleId;
    }

    /**
     * Gets the sale date.
     *
     * @return The sale date.
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * Sets the sale ID.
     *
     * @param saleId The sale ID to set.
     */
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    /**
     * Sets the sale date.
     *
     * @param saleDate The sale date to set.
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * Sets the price of the product.
     *
     * @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the account ID associated with the sale.
     *
     * @return The account ID.
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Sets the account ID associated with the sale.
     *
     * @param accountId The account ID to set.
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets the name of the product sold.
     *
     * @return The product name.
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product sold.
     *
     * @param productName The product name to set.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the price of the product.
     *
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the quantity of the product sold.
     *
     * @return The quantity of the product sold.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product sold.
     *
     * @param quantity The quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the reservation ID associated with the sale.
     *
     * @return The reservation ID.
     */
    public int getReservationId() {
        return reservationId;
    }

    /**
     * Sets the reservation ID associated with the sale.
     *
     * @param reservationId The reservation ID to set.
     */
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }
}
