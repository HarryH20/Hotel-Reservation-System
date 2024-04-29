package org.bearluxury.UI;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.bearluxury.account.Account;
import org.bearluxury.account.ClerkAccountDAO;
import org.bearluxury.account.GuestAccountJDBCDAO;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.ClerkAccountController;
import org.bearluxury.controllers.GuestAccountController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClerkRegisterPane extends JFrame {
    ImageIcon logo;
    Color backgroundColor = new Color(232, 223, 185, 255);
    private JPanel ClerkRegisterPanel;

    //private Container c;
    //private JLabel title;
    private JButton submitButton;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField userName;
    private JTextField email;
    private JTextField phoneNumber;
    private JTextField password;
    private JTextField confirmPassword;

    private JLabel emptyFirstNameLabel;
    private JLabel emptyLastNameLabel;
    private JLabel emptyEmailLabel;
    private JLabel emptyPhoneLabel;
    private JLabel invalidLengthPhoneNumberLbl;
    private JLabel emptyPasswordLabel;
    private JLabel emptyConfirmPasswordLabel;

    private JLabel emailInUseLabel;
    private JLabel phoneInUseLabel;
    private JLabel passwordNotMatchLabel;

    public ClerkRegisterPane() {
        setTitle("Clerk Registration");
        setSize(1200, 920);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        getContentPane().setBackground(backgroundColor);

        //panel
        logo = new ImageIcon("src/main/resources/bbl-logo-transparent.png");
        JLabel logoLabel = new JLabel(logo);

        firstName = new JTextField();
        lastName = new JTextField();
        email = new JTextField();
        phoneNumber = new JTextField();
        password = new JPasswordField();
        confirmPassword = new JPasswordField();

        submitButton = new JButton("Register");
        submitButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (checkCredentials()) {
                    saveClerkToDatabase();
                }
            }
        });

        ClerkRegisterPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 30 45", "fill,250:280"));
        ClerkRegisterPanel.setBackground(backgroundColor);
        ClerkRegisterPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:darken(@background,3%);");

        password.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        confirmPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        JLabel description = new JLabel("Please fill in the information below to get started");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        //error checking prompts
        emptyFirstNameLabel = new JLabel("First name is required");
        emptyFirstNameLabel.setForeground(Color.red);
        emptyLastNameLabel = new JLabel("Last name is required");
        emptyLastNameLabel.setForeground(Color.red);
        emptyEmailLabel = new JLabel("Email address is required");
        emptyEmailLabel.setForeground(Color.red);
        emptyPhoneLabel = new JLabel("Phone number is required");
        emptyPhoneLabel.setForeground(Color.red);
        invalidLengthPhoneNumberLbl = new JLabel("Enter a valid phone number");
        invalidLengthPhoneNumberLbl.setForeground(Color.red);
        emptyPasswordLabel = new JLabel("Password is required");
        emptyPasswordLabel.setForeground(Color.red);
        emptyConfirmPasswordLabel = new JLabel("Confirm password is required");
        emptyConfirmPasswordLabel.setForeground(Color.red);

        emailInUseLabel = new JLabel("This email is already in use");
        emailInUseLabel.setForeground(Color.red);
        phoneInUseLabel = new JLabel("This phone number is already in use");
        phoneInUseLabel.setForeground(Color.red);
        passwordNotMatchLabel = new JLabel("Passwords do not match");
        passwordNotMatchLabel.setForeground(Color.red);

        //Adding labels to panel
        ClerkRegisterPanel.add(logoLabel);
        ClerkRegisterPanel.add(description);
        ClerkRegisterPanel.add(new JLabel("First name"), "gapy 6");
        ClerkRegisterPanel.add(firstName);
        ClerkRegisterPanel.add(new JLabel("Last name"), "gapy 6");
        ClerkRegisterPanel.add(lastName);
        ClerkRegisterPanel.add(new JLabel("Email"), "gapy 6");
        ClerkRegisterPanel.add(email);
        ClerkRegisterPanel.add(new JLabel("Phone"), "gapy 6");
        ClerkRegisterPanel.add(phoneNumber);
        ClerkRegisterPanel.add(new JLabel("Password"), "gapy 6");
        ClerkRegisterPanel.add(password);
        ClerkRegisterPanel.add(new JLabel("Confirm password"), "gapy 6");
        ClerkRegisterPanel.add(confirmPassword);

        ClerkRegisterPanel.add(submitButton, "gapy 10");

        add(ClerkRegisterPanel);
    }

    private Boolean checkCredentials() {
        Boolean validCredentials = true;
        ClerkAccountController controller = new ClerkAccountController(new ClerkAccountDAO());

        // Check if fields are empty
        int addedComponentCount = 0;
        if (firstName.getText().isEmpty()) {
            ClerkRegisterPanel.add(emptyFirstNameLabel, 5 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else { ClerkRegisterPanel.remove(emptyFirstNameLabel); }
        if (lastName.getText().isEmpty()) {
            ClerkRegisterPanel.add(emptyLastNameLabel, 7 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else { ClerkRegisterPanel.remove(emptyLastNameLabel); }
        if (email.getText().isEmpty()) {
            ClerkRegisterPanel.add(emptyEmailLabel, 9 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {
            ClerkRegisterPanel.remove(emptyEmailLabel);
            ClerkRegisterPanel.remove(emailInUseLabel);

            // Check if email is in use
            for (Account account : controller.listAccounts()) {
                if (account.getEmail().equalsIgnoreCase(email.getText())) {
                    ClerkRegisterPanel.add(emailInUseLabel, 9 + addedComponentCount);
                    validCredentials = false;
                }
            }
        }
        if (phoneNumber.getText().isEmpty()) {
            ClerkRegisterPanel.add(emptyPhoneLabel, 11 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else if (phoneNumber.getText().length() != 10) {
            ClerkRegisterPanel.remove(emptyPhoneLabel);
            ClerkRegisterPanel.add(invalidLengthPhoneNumberLbl, 12 + addedComponentCount);
            validCredentials = false;
        } else {
            ClerkRegisterPanel.remove(emptyPhoneLabel);
            ClerkRegisterPanel.remove(phoneInUseLabel);
            ClerkRegisterPanel.remove(invalidLengthPhoneNumberLbl);

            // Check if phone is in use
            for (Account account : controller.listAccounts()) {
                if (account.getPhoneNumber() == Long.parseLong(phoneNumber.getText())) {
                    ClerkRegisterPanel.add(phoneInUseLabel, 11 + addedComponentCount);
                    validCredentials = false;
                }
            }
        }
        if (password.getText().isEmpty()) {
            ClerkRegisterPanel.add(emptyPasswordLabel, 13 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else { ClerkRegisterPanel.remove(emptyPasswordLabel); }
        if (confirmPassword.getText().isEmpty()) {
            ClerkRegisterPanel.add(emptyConfirmPasswordLabel, 15 + addedComponentCount);
            validCredentials = false;
        } else {
            ClerkRegisterPanel.remove(emptyConfirmPasswordLabel);
            ClerkRegisterPanel.remove(passwordNotMatchLabel);

            // Check if passwords match
            if (!password.getText().isEmpty()) {
                if (!password.getText().equals(confirmPassword.getText())) {
                    ClerkRegisterPanel.add(passwordNotMatchLabel, 15 + addedComponentCount);
                    validCredentials = false;
                }
            }
        }

        setVisible(true);

        return validCredentials;
    }

    //FIXME: SET ROLE TO GUEST
    public void saveClerkToDatabase() {
        String userFirstName = firstName.getText();
        String userLastName = lastName.getText();
        String userPhone = phoneNumber.getText();
        String userEmail = email.getText();
        String userPassword = password.getText();
        Role role = Role.CLERK;

        ClerkAccountController controller = new ClerkAccountController(new ClerkAccountDAO());

        // Insert the clerk into the database
        controller.insertAccount(new Account(userFirstName, userLastName, userEmail, Long.parseLong(userPhone), userPassword, role));
        JOptionPane.showMessageDialog(this, "Clerk successfully registered.");
        dispose();

    }

}