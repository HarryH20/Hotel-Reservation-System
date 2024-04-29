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

public class LoginPage extends JFrame implements ActionListener {

    Color backgroundColor = new Color(232,223,185,255);

    ImageIcon logo;

    private JPanel loginPanel;

    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JButton cmdRegister;

    private JLabel wrongMsg;

    private JComboBox<String> roleDropdown; // Dropdown for role selection

    public LoginPage() {
        setTitle("Login");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        getContentPane().setBackground(backgroundColor);

        logo = new ImageIcon("src/main/resources/bbl-logo-transparent.png");
        JLabel logoLabel = new JLabel(logo);

        emailTextField = new JTextField();
        passwordTextField = new JPasswordField();

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
        loginPanel.setBackground(backgroundColor);
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
        loginPanel.add(loginButton, "gapy 10");
        add(loginPanel);

        // Initially hide the register label
        cmdRegister = createRegisterLabel();
        loginPanel.add(cmdRegister, "gapy 10");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
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

}
