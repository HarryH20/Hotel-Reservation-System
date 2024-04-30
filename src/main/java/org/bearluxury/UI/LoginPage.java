package org.bearluxury.UI;

import org.bearluxury.account.*;
import org.bearluxury.controllers.GuestAccountController;
import org.bearluxury.state.SessionManager;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.bearluxury.controllers.ClerkAccountController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LoginPage extends JFrame implements ActionListener, ItemListener {
    final String SAVED_LOGIN_PATH = "src/main/resources/SavedLogin.csv";

    ImageIcon logo;

    private JPanel loginPanel;

    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JButton cmdRegister;

    private JLabel wrongMsg;

    private JComboBox<String> roleDropdown; // Dropdown for role selection
    private JCheckBox rememberMe;
    private boolean rememberAccount;

    public LoginPage() {
        setTitle("Login");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        getContentPane().setBackground(Style.backgroundColor);

        logo = new ImageIcon("src/main/resources/bbl-logo-transparent.png");
        JLabel logoLabel = new JLabel(logo);

        emailTextField = new JTextField();
        emailTextField.addActionListener(this);
        passwordTextField = new JPasswordField();
        passwordTextField.addActionListener(this);
        rememberMe = new JCheckBox("Remember me");
        rememberMe.addItemListener(this);

        loginButton = new JButton("Login");
        loginButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        loginButton.addActionListener(this);

        // Dropdown setup
        String[] roles = {"Guest", "Clerk", "Admin"};
        roleDropdown = new JComboBox<>(roles);
        roleDropdown.addActionListener(this);

        loginPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 30 45", "fill,250:280"));
        loginPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:darken(@background,3%);");

        passwordTextField.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        JLabel header = new JLabel("Welcome back!");
        header.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");

        JLabel description = new JLabel("Please sign in to access your account");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        wrongMsg = new JLabel("Wrong username, password, or login role");
        wrongMsg.setForeground(Color.red);

        loginPanel.add(logoLabel);
        loginPanel.add(header, "gapy 10");
        loginPanel.add(description);
        loginPanel.add(new JLabel("Email"), "gapy 8");
        loginPanel.add(emailTextField);
        loginPanel.add(new JLabel("Password"), "gapy 8");
        loginPanel.add(passwordTextField);
        loginPanel.add(new JLabel("Login as"), "gapy 8");
        loginPanel.add(roleDropdown); // Add the dropdown
        loginPanel.add(rememberMe, "grow 0");
        loginPanel.add(loginButton, "gapy 10");
        add(loginPanel);

        // Initially hide the register label
        cmdRegister = createRegisterLabel();
        loginPanel.add(cmdRegister, "gapy 10");

        // Get saved login
        if (getSavedLogin()) {
            System.out.println("Account remembered from previous login");
        }
    }

    private JButton createRegisterLabel() {
        JButton cmdRegister = new JButton("<html><a href=\"#\">Don't have an account register now</a></html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(this);
        return cmdRegister;
    }

    private Account doesAccountExist(String email, String password) {
        GuestAccountController controller = new GuestAccountController(new GuestAccountJDBCDAO());
        for (Account account : controller.listAccounts()) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }
    private Account doesClerkAccountExist(String email, String password) {
        ClerkAccountController controller = new ClerkAccountController(new ClerkAccountDAO());
        for (Account account : controller.listAccounts()) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    private void saveLoginInformation() {
        try (FileWriter writer = new FileWriter(SAVED_LOGIN_PATH)) {
            writer.append("Email,Password,Role\n");
            writer.append(emailTextField.getText())
                    .append(",")
                    .append(passwordTextField.getText())
                    .append(",")
                    .append((String) roleDropdown.getSelectedItem())
                    .append("\n");

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private void clearLoginInformation() {
        try (FileWriter writer = new FileWriter(SAVED_LOGIN_PATH)) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error clearing CSV file: " + e.getMessage());
        }
    }

    private boolean getSavedLogin() {
        try (BufferedReader br = new BufferedReader(new FileReader(SAVED_LOGIN_PATH))) {

            br.readLine();
            String accountLine = br.readLine();
            if (accountLine != null) {
                String[] columns = accountLine.split(",");
                emailTextField.setText(columns[0]);
                passwordTextField.setText(columns[1]);
                roleDropdown.setSelectedItem(columns[2]);
                rememberMe.setSelected(true);
            } else {
                return false;
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Change focus to password
        if (e.getSource() == emailTextField) {
            passwordTextField.requestFocusInWindow();
        }
        // Handle login
        if (e.getSource() == loginButton || e.getSource() == passwordTextField) {
            String selectedRole = (String) roleDropdown.getSelectedItem();
            Role role = Role.valueOf(selectedRole.toUpperCase()); // Convert the selected role to Role enum
            Account account;

            // Check if the role is Admin or Clerk
            if (role.equals(Role.ADMIN) || role.equals(Role.CLERK)) {
                account = doesClerkAccountExist(emailTextField.getText(), passwordTextField.getText());
                cmdRegister.setVisible(false); // Hide register button for Admin or Clerk
            } else { // For Guest role
                account = doesAccountExist(emailTextField.getText(), passwordTextField.getText());
                cmdRegister.setVisible(true); // Show register button for Guest
            }

            if (account != null && account.getRole().equals(role)) {
                //Save login information if requested
                if (rememberAccount) {
                    saveLoginInformation();
                } else {
                    clearLoginInformation();
                }
                SessionManager.getInstance().setCurrentAccount(account);
                dispose();
                if (role.equals(Role.GUEST)) {
                    HotelManagementSystem.openGuestHomePage();
                } else if (role.equals(Role.CLERK)) {
                    HotelManagementSystem.openClerkHomePage();
                } else if (role.equals(Role.ADMIN)) {
                    HotelManagementSystem.openAdminHomePage();
                }
            } else {
                loginPanel.add(wrongMsg, "gapy 8", 7);
                this.setVisible(true);
            }
        } else if (e.getSource() == cmdRegister) {
            dispose();
            HotelManagementSystem.openRegisterPage();
        } else if (e.getSource() == roleDropdown) {
            String selectedRole = (String) roleDropdown.getSelectedItem();
            if (selectedRole.equals("Guest")) {
                cmdRegister.setVisible(true);
            } else {
                cmdRegister.setVisible(false);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            rememberAccount = true;
        } else {
            rememberAccount = false;
        }
    }
}
