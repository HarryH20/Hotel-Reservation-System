package org.bearluxury.product;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class ProductCatalog {
    private Set<Product> products = new TreeSet<>();

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void sortByPriceHighToLow() {
        Set<Product> temp = products;
        products = new TreeSet<>(Comparator.comparing(Product::getPrice).reversed());
        products.addAll(temp);
    }

    public void sortByPriceLowToHigh() {
        Set<Product> temp = products;
        products = new TreeSet<>(Comparator.comparing(Product::getPrice));
        products.addAll(temp);
    }
}
