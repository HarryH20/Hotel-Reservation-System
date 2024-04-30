package org.bearluxury.UI;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.bearluxury.account.*;
import org.bearluxury.controllers.ClerkAccountController;
import org.bearluxury.controllers.GuestAccountController;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Optional;

public class ClerkRegisterPane extends JFrame {
    ImageIcon logo;
    private JPanel clerkRegisterPanel;

    Account existingAccount;
    boolean modify;

    //private Container c;
    //private JLabel title;
    private JButton submitButton;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JFormattedTextField phoneNumber;
    private JTextField password;
    private JTextField confirmPassword;

    private JLabel emptyFirstNameLabel;
    private JLabel badFirstNameLabel;
    private JLabel emptyLastNameLabel;
    private JLabel badLastNameLabel;
    private JLabel emptyEmailLabel;
    private JLabel emptyPhoneLabel;
    private JLabel invalidLengthPhoneNumberLbl;
    private JLabel emptyPasswordLabel;
    private JLabel badPasswordLabel;

    private JLabel emptyConfirmPasswordLabel;

    private JLabel emailInUseLabel;
    private JLabel badEmailLabel;

    private JLabel phoneInUseLabel;
    private JLabel passwordNotMatchLabel;

    private PasswordSpecifier passwordSpecifier = new PasswordSpecifier();


    public ClerkRegisterPane(boolean modify) {
        if (modify) {
            setTitle("Modify Clerk Account");
        } else {
            setTitle("Clerk Registration");
        }
        setSize(1200, 920);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        getContentPane().setBackground(Style.backgroundColor);

        //panel
        logo = new ImageIcon("src/main/resources/bbl-logo-transparent.png");
        JLabel logoLabel = new JLabel(logo);

        firstName = new JTextField();
        lastName = new JTextField();
        email = new JTextField();
        if (modify) {
            email.setEnabled(false);
            email.setFocusable(false);
        }
        try {
            MaskFormatter maskFormatter = new MaskFormatter("###-###-####");
            phoneNumber = new JFormattedTextField(maskFormatter);
        }catch(ParseException ignored){
        }
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
                    if (modify) {
                        updateClerkAccount();
                    } else {
                        saveClerkToDatabase();
                    }
                }
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                // Open the previous window or go back to the main menu

                if (modify) {
                    HotelManagementSystem.openClerkHomePage();
                } else {
                    HotelManagementSystem.openAdminHomePage(); // Assuming MainMenu is the class for the main menu
                }
            }
        });




        clerkRegisterPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 30 45", "fill,250:280"));
        clerkRegisterPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:darken(@background,3%);");

        password.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        confirmPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        JLabel description = new JLabel("Please fill in the information below to get started");
        if (modify) {
            description.setText("Modify personal information");
        }
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        //error checking prompts
        emptyFirstNameLabel = new JLabel("First name is required");
        emptyFirstNameLabel.setForeground(Color.red);
        badFirstNameLabel = new JLabel("First name must be alphabetical");
        badFirstNameLabel.setForeground(Color.red);
        emptyLastNameLabel = new JLabel("Last name is required");
        emptyLastNameLabel.setForeground(Color.red);
        badLastNameLabel = new JLabel("Last name must be alphabetical");
        badLastNameLabel.setForeground(Color.red);
        emptyEmailLabel = new JLabel("Email address is required");
        emptyEmailLabel.setForeground(Color.red);
        emptyPhoneLabel = new JLabel("Phone number is required");
        emptyPhoneLabel.setForeground(Color.red);
        invalidLengthPhoneNumberLbl = new JLabel("Enter a valid phone number");
        invalidLengthPhoneNumberLbl.setForeground(Color.red);
        emptyPasswordLabel = new JLabel("Password is required");
        emptyPasswordLabel.setForeground(Color.red);
        badPasswordLabel = new JLabel();
        badPasswordLabel.setForeground(Color.red);
        emptyConfirmPasswordLabel = new JLabel("Confirm password is required");
        emptyConfirmPasswordLabel.setForeground(Color.red);

        emailInUseLabel = new JLabel("This email is already in use");
        emailInUseLabel.setForeground(Color.red);

        badEmailLabel = new JLabel("Email address not valid.");
        badEmailLabel.setForeground(Color.red);

        phoneInUseLabel = new JLabel("This phone number is already in use");
        phoneInUseLabel.setForeground(Color.red);
        passwordNotMatchLabel = new JLabel("Passwords do not match");
        passwordNotMatchLabel.setForeground(Color.red);

        //Fill fields if modiyfing
        existingAccount = SessionManager.getInstance().getCurrentAccount();
        if (modify) {
            fillExistingInformation(existingAccount);
        }

        //Adding labels to panel
        clerkRegisterPanel.add(logoLabel);
        clerkRegisterPanel.add(description);
        clerkRegisterPanel.add(new JLabel("First name"), "gapy 6");
        clerkRegisterPanel.add(firstName);
        clerkRegisterPanel.add(new JLabel("Last name"), "gapy 6");
        clerkRegisterPanel.add(lastName);
        clerkRegisterPanel.add(new JLabel("Email"), "gapy 6");
        clerkRegisterPanel.add(email);
        clerkRegisterPanel.add(new JLabel("Phone"), "gapy 6");
        clerkRegisterPanel.add(phoneNumber);
        clerkRegisterPanel.add(new JLabel("Password"), "gapy 6");
        clerkRegisterPanel.add(password);
        clerkRegisterPanel.add(new JLabel("Confirm password"), "gapy 6");
        clerkRegisterPanel.add(confirmPassword);

        clerkRegisterPanel.add(submitButton, "gapy 10");
        clerkRegisterPanel.add(backButton, "gapy 10");

        add(clerkRegisterPanel);
    }

    private void fillExistingInformation(Account account) {
        firstName.setText(account.getFirstName());
        lastName.setText(account.getLastName());
        email.setText(account.getEmail());
        try {
            MaskFormatter maskFormatter = new MaskFormatter("###-###-####");
            maskFormatter.setPlaceholder(String.valueOf(account.getPhoneNumber()));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        phoneNumber.setText(String.valueOf(account.getPhoneNumber()));
        password.setText(account.getPassword());
    }

    private void removeErrorLabels() {
        clerkRegisterPanel.remove(emptyFirstNameLabel);
        clerkRegisterPanel.remove(badFirstNameLabel);
        clerkRegisterPanel.remove(emptyLastNameLabel);
        clerkRegisterPanel.remove(badLastNameLabel);
        clerkRegisterPanel.remove(emptyEmailLabel);
        clerkRegisterPanel.remove(badEmailLabel);
        clerkRegisterPanel.remove(emailInUseLabel);
        clerkRegisterPanel.remove(emptyPhoneLabel);
        clerkRegisterPanel.remove(phoneInUseLabel);
        clerkRegisterPanel.remove(emptyPasswordLabel);
        clerkRegisterPanel.remove(badPasswordLabel);
        clerkRegisterPanel.remove(emptyConfirmPasswordLabel);
        clerkRegisterPanel.remove(passwordNotMatchLabel);
    }

    private boolean checkCredentials() {
        boolean validCredentials = true;
        GuestAccountController controller = new GuestAccountController(new GuestAccountJDBCDAO());
        removeErrorLabels();

        // Check if fields are empty
        int addedComponentCount = 0;
        if (firstName.getText().isEmpty()) {
            clerkRegisterPanel.add(emptyFirstNameLabel, 4 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {

            // Check if alphabetical
            if (!firstName.getText().matches("[a-zA-Z]*$")) {
                clerkRegisterPanel.add(badFirstNameLabel, 4 + addedComponentCount);
                addedComponentCount++;
                validCredentials = false;
            }
        }
        if (lastName.getText().isEmpty()) {
            clerkRegisterPanel.add(emptyLastNameLabel, 6 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {

            // Check if alphabetical
            if (!lastName.getText().matches("[a-zA-Z]*$")) {
                clerkRegisterPanel.add(badLastNameLabel, 6 + addedComponentCount);
                addedComponentCount++;
                validCredentials = false;
            }
        }
        if (email.getText().isEmpty()) {
            clerkRegisterPanel.add(emptyEmailLabel, 8 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {

            // Check if email is in use
            for (Account account : controller.listAccounts()) {
                if (account.getEmail().equalsIgnoreCase(email.getText())) {
                    clerkRegisterPanel.add(emailInUseLabel, 8 + addedComponentCount);
                    validCredentials = false;
                    addedComponentCount++;

                }
            }
            if(!EmailSpecifier.isValidEmail(email.getText())){
                clerkRegisterPanel.add(badEmailLabel, 8 + addedComponentCount);
                validCredentials = false;
                addedComponentCount++;
            }
        }
        System.out.println(phoneNumber.getValue());
        if (phoneNumber.getText().length() < 12) {
            clerkRegisterPanel.add(emptyPhoneLabel, 10 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {

            // Check if phone is in use
            for (Account account : controller.listAccounts()) {
                if (account.getPhoneNumber() == Long
                        .parseLong(String.valueOf(phoneNumber.getText())
                                .replaceAll("-",""))) {
                    clerkRegisterPanel.add(phoneInUseLabel, 10 + addedComponentCount);
                    addedComponentCount++;
                    validCredentials = false;
                }
            }
        } // password is empty, show error
        if (password.getText().isEmpty()) {
            clerkRegisterPanel.add(emptyPasswordLabel, 12 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        }else{

            // password does not meet specification, show error
            if(!passwordSpecifier.checkPassword(password.getText())){
                // if there is a problem with the password, it's not empty
                clerkRegisterPanel.remove(emptyPasswordLabel);

                badPasswordLabel.setText(passwordSpecifier.getPasswordProblem());
                clerkRegisterPanel.add(badPasswordLabel, 12 + addedComponentCount);
                addedComponentCount++;
                validCredentials = false;
            }
        }
        if (confirmPassword.getText().isEmpty()) {
            clerkRegisterPanel.add(emptyConfirmPasswordLabel, 14 + addedComponentCount);
            validCredentials = false;
        } else {

            // Check if passwords match
            if (!password.getText().isEmpty()) {
                if (!password.getText().equals(confirmPassword.getText())) {
                    clerkRegisterPanel.add(passwordNotMatchLabel, 14 + addedComponentCount);
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
        String userPhone = phoneNumber.getText().replaceAll("-","");
        String userEmail = email.getText();
        String userPassword = password.getText();
        Role role = Role.CLERK;

        ClerkAccountController controller = new ClerkAccountController(new ClerkAccountDAO());

        // Insert the clerk into the database
        controller.insertAccount(new Account(userFirstName, userLastName, userEmail, Long.parseLong(userPhone), userPassword, role));
        JOptionPane.showMessageDialog(this, "Clerk successfully registered.");
        dispose();
    }

    public void updateClerkAccount() {
        ClerkAccountController clerkAccountController = new ClerkAccountController(new ClerkAccountDAO());
        Optional<Account> loggedInAccountOptional = clerkAccountController.getAccount(existingAccount.getEmail());
        Account loggedInAccount = loggedInAccountOptional.get();

        // Update the account with the modified information
        loggedInAccount.setFirstName(firstName.getText());
        loggedInAccount.setLastName(lastName.getText());
        loggedInAccount.setEmail(email.getText());
        loggedInAccount.setPhoneNumber(Long.parseLong(phoneNumber.getText().replaceAll("-", "")));
        loggedInAccount.setPassword(password.getText());

        // Call the update method in AccountController to update the account in the database
        clerkAccountController.updateAccounts(loggedInAccount, existingAccount.getEmail());

        // Close the dialog
        JOptionPane.showMessageDialog(this, "Account information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        HotelManagementSystem.openClerkHomePage();
    }

}
