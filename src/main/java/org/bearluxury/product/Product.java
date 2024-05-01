package org.bearluxury.product;
/**
 * Represents a product object.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class Product {

    private String name;
    private double price;
    private int quantity;
    private PRODUCT_TYPE productType;
    private int id;

    /**
     * Constructs a new Product with default values.
     */
    public Product() {}

    /**
     * Constructs a new Product with the specified attributes.
     *
     * @param name the name of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     * @param productType the type of the product
     */
    public Product(String name, double price, int quantity, PRODUCT_TYPE productType) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productType = productType;
    }

    /**
     * Gets the ID of the product.
     *
     * @return the ID of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     *
     * @param id the ID of the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the product.
     *
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the quantity of the product.
     *
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity the quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the type of the product.
     *
     * @return the type of the product
     */
    public PRODUCT_TYPE getProductType() {
        return productType;
    }

    /**
     * Sets the type of the product.
     *
     * @param productType the type of the product
     */
    public void setProductType(PRODUCT_TYPE productType) {
        this.productType = productType;
    }

    /**
     * Returns a string representation of the product.
     *
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", productType=" + productType +
                '}';
    }
}

