package org.bearluxury.UI.shopUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.miginfocom.swing.MigLayout;
import org.bearluxury.UI.HotelManagementSystem;

public class CreditCardFrame extends JFrame {


    private JTextField cardNumberField;
    private JTextField expDateField;
    private JTextField cvvField;
    private JTextField cardHolderNameField;
    public CreditCardFrame() {
        setTitle("Credit Card Information");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel creditCardPanel = new JPanel(new MigLayout("wrap 2", "[][grow]"));

        cardNumberField = new JTextField(20);
        expDateField = new JTextField(20);
        cvvField = new JTextField(20);
        cardHolderNameField = new JTextField(20);

        creditCardPanel.add(new JLabel("Card Number"));
        creditCardPanel.add(cardNumberField, "wrap");
        creditCardPanel.add(new JLabel("Expiration Date (MM/YY)"));
        creditCardPanel.add(expDateField, "wrap");
        creditCardPanel.add(new JLabel("CVV"));
        creditCardPanel.add(cvvField, "wrap");
        creditCardPanel.add(new JLabel("Cardholder Name"));
        creditCardPanel.add(cardHolderNameField, "wrap");

        JButton submitButton = new JButton("Finish Registration"); // Create the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Account successfully registered.");
                HotelManagementSystem.openLoginPage();
                dispose();
            }
        });

        // Add the submit button to the panel
        creditCardPanel.add(submitButton, "span 2, align center");

        add(creditCardPanel);
    }

    public String getCardNumberField() {
        return cardNumberField.getText();
    }

    public String getExpDateField() {
        return expDateField.getText();
    }

    public String getCvvField() {
        return cvvField.getText();
    }

    public String getCardHolderNameField() {
        return cardHolderNameField.getText();
    }
}

