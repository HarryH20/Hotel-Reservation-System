package org.bearluxury.product;

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
}
