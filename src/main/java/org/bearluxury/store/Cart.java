package org.bearluxury.store;

import org.bearluxury.product.Product;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items; // Map to store products and their quantities

    // Constructor
    public Cart() {
        this.items = new HashMap<>();
    }

    // Method to add a product to the cart
    public void addItem(Product product) {
        items.put(product, items.getOrDefault(product, 0) + 1); // Increment quantity by 1
    }

    // Method to remove a product from the cart
    public void removeItem(Product product) {
        items.remove(product);
    }

    // Method to calculate the total price of all items in the cart
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += product.getPrice() * quantity;
        }
        return totalPrice;
    }

    // Method to display the contents of the cart
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

    // Method to clear the cart
    public void clearCart() {
        items.clear();
    }
}
