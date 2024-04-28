package org.bearluxury.UI.shopUI;

import org.bearluxury.account.CreditCard;
import org.bearluxury.shop.CreditCardPayment;
import org.bearluxury.shop.Payment;
import org.bearluxury.account.Guest;

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

    Guest guest;
    CreditCard card;
    double charge;

    public CreditCardEntryScreen(Guest guest, double charge) {
        this.guest = guest;
        this.card = guest.getCreditCard();
        this.charge = charge;

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

        if (card != null) {
            cardNumberField.setText(card.getCardNumber());
            expirationDateField.setText(card.getExpDate());
            cvvField.setText(card.getCvv());
            cardholderNameField.setText(card.getCardHolderName());
        }

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

            CreditCard tempCard = new CreditCard(cardNumber, cardholderName,expirationDate, cvv);

            if (this.card != null) {
                if (!this.card.equals(tempCard)) {
                    this.card = tempCard;
                }
            } else {
                this.card = tempCard;
            }

            Payment payment = new CreditCardPayment(this.charge, this.card);
            if (payment.processPayment()) {
                System.out.println("payment successful");
            } else {
                System.out.println("payment unsuccessful");
            }
        }
    }
}
