package org.bearluxury.UI.shopUI;

import com.formdev.flatlaf.FlatClientProperties;
import org.bearluxury.product.Product;
import org.bearluxury.store.Cart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutDialog extends JDialog implements ActionListener {
    private final double SALES_TAX = 0.0625;

    Cart cart;

    JPanel cartPanel;
    JPanel centerPanel;
    JPanel summaryPanel;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel totalPanel;
    JPanel purchasePanel;

    JLabel cartLabel;
    DefaultListModel<String> listModel;
    JList<String> cartList;
    JScrollPane cartScrollPane;

    JLabel summaryLabel;
    JLabel totalProductsLabel;
    JLabel totalProductsAmountLabel;
    JLabel taxLabel;
    JLabel taxAmountLabel;

    JLabel overallTotalLabel;
    JLabel overallTotalAmountLabel;
    double overallTotalCost;

    JButton purchaseButton;

    public CheckoutDialog(JFrame parent, Cart cart) {
        super(parent, "Checkout", true);
        //setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        setLocationRelativeTo(parent);
        setSize(400, 600);

        double totalPrice = cart.calculateTotalPrice();
        this.cart = cart;

        // Cart panel
        cartPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        cartPanel.setPreferredSize(new Dimension(this.getWidth(), 300));

        cartLabel = new JLabel("Your Cart");
        cartLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        listModel = new DefaultListModel<>();
        cartList = new JList<>(listModel);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        cartList.setFocusable(false);
        for (Map.Entry<Product, Integer> entry : cart.getCartItems().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            listModel.addElement(quantity + "x " + product.getName() + " - $" + (product.getPrice() * quantity));
        }

        cartScrollPane = new JScrollPane(cartList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cartScrollPane.setPreferredSize(new Dimension(300, 200));

        summaryLabel = new JLabel("Purchase Summary");
        summaryLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        cartPanel.add(cartLabel);
        cartPanel.add(cartScrollPane);
        cartPanel.add(summaryLabel);

        centerPanel = new JPanel(new BorderLayout());

        // Summary panel
        summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        leftPanel = new JPanel(new GridLayout(2, 1));
        rightPanel = new JPanel(new GridLayout(2, 1));

        totalProductsLabel = new JLabel(this.cart.getCartItems().size() + " products");
        taxLabel = new JLabel("Sales tax");
        leftPanel.add(totalProductsLabel);
        leftPanel.add(taxLabel);

        totalProductsAmountLabel = new JLabel("$" + String.format("%.2f", totalPrice));
        totalProductsAmountLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        taxAmountLabel = new JLabel("$" + String.format("%.2f", totalPrice * SALES_TAX));
        taxAmountLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        rightPanel.add(totalProductsAmountLabel);
        rightPanel.add(taxAmountLabel);

        summaryPanel.add(leftPanel, BorderLayout.WEST);
        summaryPanel.add(rightPanel, BorderLayout.EAST);

        overallTotalCost = totalPrice + (totalPrice * SALES_TAX);
        overallTotalAmountLabel = new JLabel("$" + String.format("%.2f", overallTotalCost));

        totalPanel = new JPanel(new BorderLayout());
        overallTotalLabel = new JLabel("Total:");
        overallTotalLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");
        overallTotalAmountLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");
        totalPanel.add(overallTotalLabel, BorderLayout.WEST);
        totalPanel.add(overallTotalAmountLabel, BorderLayout.EAST);

        centerPanel.add(summaryPanel, BorderLayout.NORTH);
        centerPanel.add(totalPanel, BorderLayout.CENTER);


        // Checkout panel
        purchasePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        purchaseButton = new JButton("Confirm purchase");
        purchaseButton.setPreferredSize(new Dimension(200, 30));
        purchasePanel.add(purchaseButton);

        add(cartPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(purchasePanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == purchaseButton) {
            // Access the cart items using the getCartItems() method of the Cart class
            Map<Product, Integer> cartItems = cart.getCartItems();

            // Iterate over cart items and perform necessary actions
            for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                // Perform database operations or any other necessary actions
                // For example:
                // - Update product inventory
                // - Record the purchase in the database
                // - Send confirmation email to the user
            }

            // After processing the purchase, you might want to clear the cart
            cart.clearCart();

            // Close the dialog or perform any other necessary actions
            dispose();
        }
    }
}
