package org.bearluxury.store;

import org.bearluxury.product.Product;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public void addItem(Product product) {
        items.put(product, items.getOrDefault(product, 0) + 1);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += product.getPrice() * quantity;
        }
        return totalPrice;
    }

    public void displayCart() {
        System.out.println("Shopping Cart:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("- " + product.getName() + ": $" + product.getPrice() + " x " + quantity);
        }
        System.out.println("Total Price: $" + calculateTotalPrice());
    }

    public Map<Product, Integer> getCartItems() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
}
