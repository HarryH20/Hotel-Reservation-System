package org.bearluxury.product;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * The ProductCatalog class represents a catalog of products and provides methods to manage and sort them.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class ProductCatalog {
    private Set<Product> products = new TreeSet<>();


    /**
     * Retrieves the set of products.
     *
     * @return the set of products
     */
    public Set<Product> getProducts() {
        return products;
    }

    /**
     * Sets the set of products.
     *
     * @param products the set of products to set
     */
    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    /**
     * Sorts the products by price from high to low.
     */
    public void sortByPriceHighToLow() {
        Set<Product> temp = products;
        products = new TreeSet<>(Comparator.comparing(Product::getPrice).reversed());
        products.addAll(temp);
    }

    /**
     * Sorts the products by price from low to high.
     */
    public void sortByPriceLowToHigh() {
        Set<Product> temp = products;
        products = new TreeSet<>(Comparator.comparing(Product::getPrice));
        products.addAll(temp);
    }
}
