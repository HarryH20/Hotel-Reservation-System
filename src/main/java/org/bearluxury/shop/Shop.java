package org.bearluxury.shop;

import org.bearluxury.product.Product;
import org.bearluxury.product.ProductCatalog;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    Map<Product, Integer> cart;
    ProductCatalog productCatalog;
    Sale sale;
    Payment paymentMethod;

    public Shop(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
        cart = new HashMap<>();
        sale = new Sale();
    }

    public void addToCart(Product product) {
        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) + 1);
        } else {
            cart.put(product, 1);
        }
    }

    public void removeItem(Product product) {
        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) - 1);
        }
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    public Map<Product, Integer> getCart() {
        return this.cart;
    }

    public ProductCatalog getProductCatalog() {
        return this.productCatalog;
    }

}
