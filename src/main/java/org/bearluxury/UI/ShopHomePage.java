package org.bearluxury.UI;

import com.formdev.flatlaf.FlatClientProperties;
import org.bearluxury.account.Role;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductCatalog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ShopHomePage extends JFrame implements ActionListener, ListSelectionListener {

    ProductCatalog productCatalog;

    JPanel sideBar;
    JPanel itemPanel;
    JPanel checkoutPanel;

    JButton backButton;

    JLabel categoryLabel;
    JList<String> categories;
    JScrollPane categoryPane;

    JLabel sortLabel;
    JComboBox sortDropDown;

    JScrollPane productScrollPane;
    String productFilter;

    JLabel cartLabel;
    Map<Product, Integer> cartInventory;
    JList<String> cartList;
    JScrollPane cartScrollPane;

    JLabel totalPriceLabel;
    JLabel totalPriceNumber;
    double totalPrice;
    JButton checkoutButton;
    public ShopHomePage(ProductCatalog productCatalog) {
        setTitle("Shop Home");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Style.backgroundColor);

        this.productCatalog = productCatalog;

        sideBar = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 10));
        sideBar.setPreferredSize(new Dimension(230, this.getHeight()));

        itemPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
        itemPanel.setBackground(Style.backgroundColor);
        itemPanel.setPreferredSize(new Dimension(0, 150 * (this.productCatalog.getProducts().size() / 3)));

        checkoutPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 10));
        checkoutPanel.setPreferredSize(new Dimension(180, this.getHeight()));

        backButton = new JButton("Back");

        categoryLabel = new JLabel("Category");
        categoryLabel.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");

        String[] categoryTypes = {"All items", "Clothing", "Accessory", "Artesian Good", "Toiletry", "Pharmaceutical"};
        productFilter = categoryTypes[0];
        categories = new JList<>(categoryTypes);
        categories.addListSelectionListener(this);
        categoryPane = new JScrollPane(categories);

        sortLabel = new JLabel("Sort By");
        sortLabel.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        String[] sortTypes = {"None", "Price: High to Low", "Price: Low to High"};
        sortDropDown = new JComboBox(sortTypes);
        sortDropDown.addActionListener(this);

        sideBar.add(backButton);
        sideBar.add(fillSpace(100));
        sideBar.add(categoryLabel);
        sideBar.add(fillSpace(10));
        sideBar.add(categoryPane);
        sideBar.add(fillSpace(100));
        sideBar.add(sortLabel);
        sideBar.add(sortDropDown);

        loadProductCards(productFilter);
        productScrollPane = new JScrollPane(itemPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        cartLabel = new JLabel("Cart");
        cartLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        String[] cartItems = {"Empty"};
        cartList = new JList<>(cartItems);
        cartList.setEnabled(false);
        cartScrollPane = new JScrollPane(cartList);
        cartScrollPane.setPreferredSize(new Dimension(140, 200));

        cartInventory = new LinkedHashMap<>();

        totalPriceLabel = new JLabel("Total Price:");
        totalPriceLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        totalPriceNumber = new JLabel("$0.00");
        totalPriceNumber.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");

        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(this);
        backButton.addActionListener(this);

        checkoutPanel.add(cartLabel);
        checkoutPanel.add(fillSpace(15));
        checkoutPanel.add(cartScrollPane);
        checkoutPanel.add(fillSpace(100));
        checkoutPanel.add(totalPriceLabel);
        checkoutPanel.add(totalPriceNumber);
        checkoutPanel.add(fillSpace(15));
        checkoutPanel.add(checkoutButton);

        add(sideBar, BorderLayout.WEST);
        add(productScrollPane, BorderLayout.CENTER);
        add(checkoutPanel, BorderLayout.EAST);
    }

    private void loadProductCards(String filter) {
        for (Product product : this.productCatalog.getProducts()) {
            if (!filter.equals("All items")) {
                if (product.getProductType().toString().equalsIgnoreCase(filter)) {
                    itemPanel.add(new ProductCard(product, this));
                }
            } else {
                itemPanel.add(new ProductCard(product, this));
            }
        }
    }

    public void updateCart(Product product, int quantity) {
        //This code kinda goofy, but it works (for now)
        cartInventory.put(product, quantity);
        if (cartInventory.get(product) == 0) {
            cartInventory.remove(product);
        }
        List<String> newProducts = new ArrayList<>();
        totalPrice = 0;
        if (cartInventory.isEmpty()) {
            newProducts.add("Empty");
        } else {
            cartInventory.forEach((key, value) -> newProducts.add(value + "x " + key.getName()));
            cartInventory.forEach((key, value) -> totalPrice += (key.getPrice() * value));
        }
        checkoutPanel.remove(cartScrollPane);
        cartScrollPane = new JScrollPane(new JList<>(newProducts.toArray()));
        cartScrollPane.setPreferredSize(new Dimension(140, 200));
        checkoutPanel.add(cartScrollPane, 2);
        totalPriceNumber.setText("$" + String.format("%.2f", totalPrice));
        this.setVisible(true);
    }

    private JLabel fillSpace(int whitespace) {
        StringBuilder filler = new StringBuilder();
        for (int i = 0; i < whitespace; ++i) {
            filler.append(" ");
        }
        return new JLabel(String.valueOf(filler));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sortDropDown) {
            if (sortDropDown.getSelectedIndex() != 0) {
                switch (sortDropDown.getSelectedIndex()) {
                    case 1:
                        productCatalog.sortByPriceHighToLow();
                        break;
                    case 2:
                        productCatalog.sortByPriceLowToHigh();
                        break;
                }
                itemPanel.removeAll();
                loadProductCards(productFilter);
                productScrollPane.revalidate();
                productScrollPane.repaint();
            }
        }
        if(e.getSource() == backButton){
            HotelManagementSystem.openGuestHomePage();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            itemPanel.removeAll();
            productFilter = categories.getSelectedValue();
            loadProductCards(productFilter);
            productScrollPane.revalidate();
            productScrollPane.repaint();
        }
    }
}
