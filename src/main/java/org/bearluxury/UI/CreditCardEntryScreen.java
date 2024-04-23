package org.bearluxury.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditCardEntryScreen extends JFrame implements ActionListener {
    private JTextField cardNumberField;
    private JTextField expirationDateField;
    private JTextField cvvField;
    private JTextField cardholderNameField;

    private JButton submitButton;

    public CreditCardEntryScreen() {
        setTitle("Credit Card Information");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();

        JLabel expirationDateLabel = new JLabel("Expiration Date:");
        expirationDateField = new JTextField();

        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JTextField();

        JLabel cardholderNameLabel = new JLabel("Cardholder Name:");
        cardholderNameField = new JTextField();

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(expirationDateLabel);
        panel.add(expirationDateField);
        panel.add(cvvLabel);
        panel.add(cvvField);
        panel.add(cardholderNameLabel);
        panel.add(cardholderNameField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Retrieve credit card information
            String cardNumber = cardNumberField.getText();
            String expirationDate = expirationDateField.getText();
            String cvv = cvvField.getText();
            String cardholderName = cardholderNameField.getText();

            // Do something with the credit card information (e.g., validate, process payment)
            // For example:
            // - Validate the credit card details
            // - Process the payment
            // - Close the credit card entry screen
            // - Display a message to the user indicating success or failure
        }
    }
}
