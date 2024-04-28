package org.bearluxury.shop;

import org.bearluxury.product.Product;

class SaleLineItem {
    private Product product;
    private int quantity;

    public SaleLineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters
    public Product getProduct() {
        return product;
    }
}
