package org.bearluxury.UI;

import com.formdev.flatlaf.FlatClientProperties;
import org.bearluxury.product.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutDialog extends JDialog {
    private final double SALES_TAX = 6.25;

    JLabel cartLabel;
    DefaultListModel<String> listModel;
    JList<String> cartList;
    JScrollPane cartScrollPane;

    JLabel purchaseSummaryLabel;

    public CheckoutDialog(JFrame parent, Map<Product, Integer> cart) {
        super(parent, "Checkout", true);
        setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));
        setLocationRelativeTo(parent);
        setSize(400, 600);

        cartLabel = new JLabel("Your Cart");
        cartLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        listModel = new DefaultListModel<>();
        cartList = new JList<>(listModel);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        cartList.setFocusable(false);
        cart.forEach((product, quantity) -> listModel.addElement(quantity + "x " + product.getName() + " - $" + (product.getPrice() * quantity)));

        cartScrollPane = new JScrollPane(cartList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cartScrollPane.setPreferredSize(new Dimension(300, 200));

        purchaseSummaryLabel = new JLabel("Purchase Summary");
        purchaseSummaryLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        add(cartLabel);
        add(cartScrollPane);
        add(purchaseSummaryLabel);
    }
}
