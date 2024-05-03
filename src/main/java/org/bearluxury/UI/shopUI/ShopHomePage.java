package org.bearluxury.UI.shopUI;

import com.formdev.flatlaf.FlatClientProperties;
import org.bearluxury.UI.shopUI.CheckoutDialog;
import org.bearluxury.UI.HotelManagementSystem;
import org.bearluxury.UI.Style;
import org.bearluxury.UI.shopUI.ProductCard;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.ProductController;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductCatalog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * The ShopHomePage class represents the main shopping interface for the application.
 * It allows users to browse products, add them to the cart, and proceed to checkout.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class ShopHomePage extends JFrame implements ActionListener, ListSelectionListener {

    ProductController productController;

    JPanel sideBar;
    JPanel itemPanel;
    JPanel checkoutPanel;

    JButton backButton;

    JLabel categoryLabel;
    JList<String> categories;
    JScrollPane categoryPane;

    JLabel sortLabel;
    JComboBox sortDropDown;

    List<ProductCard> productCards;
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

    /**
     * Constructs a new ShopHomePage with the specified ProductController.
     *
     * @param productController The ProductController to manage product data.
     */
    public ShopHomePage(ProductController productController) {
        setTitle("Shop Home");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Style.backgroundColor);

        this.productController = productController;

        sideBar = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 10));
        sideBar.setPreferredSize(new Dimension(230, this.getHeight()));

        itemPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
        itemPanel.setBackground(Style.backgroundColor);
        itemPanel.setPreferredSize(new Dimension(0, 150 * (this.productController.listProducts().size() / 3)));

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
        String[] sortTypes = {"A-Z", "Price: High to Low", "Price: Low to High"};
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

        initProductCards();
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
        checkoutButton.setEnabled(false);
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

    /**
     * Initializes the product cards for display.
     */
    private void initProductCards() {
        productCards = new ArrayList<>();
        // Retrieve products from the database
        productController.listProducts().forEach(product -> productCards.add(new ProductCard(product, this)));
        // Add cards to panel
        productCards.forEach(itemPanel::add);
    }



    /**
     * Reloads the product cards based on the selected filter.
     *
     * @param filter The filter to apply to the product list.
     */
    private void reloadProductCards(String filter) {
        itemPanel.removeAll();
        // Retrieve products from the database based on the filter
        if (!filter.equals("All items")) {
            productCards.forEach(productCard -> {
                if (productCard.getProduct().getProductType().toString().equalsIgnoreCase(filter)) {
                    itemPanel.add(productCard);
                }
            });
        } else {
            productCards.forEach(productCard -> itemPanel.add(productCard));
        }
        productScrollPane.revalidate();
        productScrollPane.repaint();
    }

    /**
     * Updates the cart with the specified product and quantity.
     *
     * @param product The product to be added or updated in the cart.
     * @param quantity The quantity of the product to add or update.
     */
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
        // Enable checkout button
        if (totalPrice > 0) {
            checkoutButton.setEnabled(true);
        } else {
            checkoutButton.setEnabled(false);
        }
        this.setVisible(true);
    }

    /**
     * Creates a JLabel with whitespace for layout spacing.
     *
     * @param whitespace The amount of whitespace to add.
     * @return The created JLabel with the specified whitespace.
     */
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
            switch (sortDropDown.getSelectedIndex()) {
                case 0:
                    productCards.sort(ProductPriceComparator.A_TO_Z);
                    break;
                case 1:
                    productCards.sort(ProductPriceComparator.HIGH_TO_LOW);
                    break;
                case 2:
                    productCards.sort(ProductPriceComparator.LOW_TO_HIGH);
                    break;
            }
            reloadProductCards(productFilter);
        } else if (e.getSource() == checkoutButton) {
            CheckoutDialog checkoutDialog = new CheckoutDialog(this, cartInventory, totalPrice);
            checkoutDialog.setVisible(true);
        }

        if(e.getSource() == backButton){
            dispose();
            HotelManagementSystem.openGuestHomePage();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            productFilter = categories.getSelectedValue();
            reloadProductCards(productFilter);
        }
    }


    /**
     * Defines comparators for sorting ProductCard objects based on price and name.
     */
    public enum ProductPriceComparator implements Comparator<ProductCard>{
        LOW_TO_HIGH {
            @Override
            public int compare(ProductCard o1, ProductCard o2) {
                return Double.compare(o1.getProduct().getPrice(), o2.getProduct().getPrice());
            }
        },
        HIGH_TO_LOW {
            @Override
            public int compare(ProductCard o1, ProductCard o2) {
                return Double.compare(o2.getProduct().getPrice(), o1.getProduct().getPrice());
            }
        },
        A_TO_Z {
            @Override
            public int compare(ProductCard o1, ProductCard o2) {
                return o1.getProduct().getName().compareTo(o2.getProduct().getName());
            }
        }
    }
}
