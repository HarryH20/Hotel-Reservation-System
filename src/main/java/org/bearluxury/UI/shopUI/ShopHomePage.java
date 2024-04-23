package org.bearluxury.UI.shopUI;

import com.formdev.flatlaf.FlatClientProperties;
import org.bearluxury.UI.HotelManagementSystem;
import org.bearluxury.UI.Style;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductCatalog;
import org.bearluxury.store.Cart;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/*
    User Interface for store. Uses ProductCard component class to create product cards that
    show on screen
 */
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

    List<ProductCard> productCards;
    JScrollPane productScrollPane;
    String productFilter;

    JLabel cartLabel;
    Map<Product, Integer> cartInventory;
    JList<String> cartList;
    Cart cart;
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

        cart = new Cart();
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

    private void initProductCards() {
        productCards = new ArrayList<>();
        // Create product cards from product catalog
        productCatalog.getProducts().forEach(product -> productCards.add(new ProductCard(product, this)));
        // Add cards to panel
        productCards.forEach(productCard -> itemPanel.add(productCard));
    }

    private void reloadProductCards(String filter) {
        itemPanel.removeAll();
        // Re-add products to panel if it matches the filter
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

    public void updateCart(Product product) {
        // Update the cart with the given product and quantity
        cart.addItem(product);

        // Refresh the cart display
        updateCartDisplay();
    }

    private void updateCartDisplay() {
        // Retrieve cart contents
        Map<Product, Integer> cartInventory = cart.getCartItems();
        List<String> cartItems = new ArrayList<>();

        // Populate cart items list
        for (Map.Entry<Product, Integer> entry : cartInventory.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            cartItems.add(quantity + "x " + product.getName());
        }

        // If cart is empty, add an "Empty" message
        if (cartItems.isEmpty()) {
            cartItems.add("Empty");
        }

        // Update the cart list
        cartList.setListData(cartItems.toArray(new String[0]));

        // Update total price label
        totalPriceNumber.setText("$" + String.format("%.2f", cart.calculateTotalPrice()));

        // Refresh the frame
        this.revalidate();
        this.repaint();
    }

    // Used for wrapping
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
            CheckoutDialog checkoutDialog = new CheckoutDialog(this, cart);
            checkoutDialog.setVisible(true);
        }
        if(e.getSource() == backButton){
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