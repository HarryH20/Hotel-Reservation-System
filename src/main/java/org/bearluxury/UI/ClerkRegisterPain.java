package org.bearluxury.UI;

import org.bearluxury.account.Account;
import org.bearluxury.account.ClerkAccountDAO;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.ClerkAccountController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClerkRegisterPain extends JFrame {
    private Container c;
    private JLabel title;
    private JButton submitButton;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField userName;
    private JTextField email;
    private JTextField phoneNumber;
    private JTextField password;

    public ClerkRegisterPain() {
        setTitle("Clerk Registration");
        setBounds(300, 90, 500, 600);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Clerk Registration");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(100, 30);
        c.add(title);

        JPanel personalInfoPanel = new JPanel();
        personalInfoPanel.setBorder(BorderFactory.createTitledBorder("Personal Information"));
        personalInfoPanel.setBounds(40, 80, 400, 160);
        personalInfoPanel.setLayout(null);

        JLabel fName = new JLabel("First Name");
        fName.setFont(new Font("Arial", Font.PLAIN, 15));
        fName.setBounds(20, 30, 100, 20);
        personalInfoPanel.add(fName);
        firstName = new JTextField();
        firstName.setFont(new Font("Arial", Font.PLAIN, 15));
        firstName.setBounds(170, 30, 190, 20);
        personalInfoPanel.add(firstName);

        JLabel lName = new JLabel("Last Name");
        lName.setFont(new Font("Arial", Font.PLAIN, 15));
        lName.setBounds(20, 60, 100, 20);
        personalInfoPanel.add(lName);
        lastName = new JTextField();
        lastName.setFont(new Font("Arial", Font.PLAIN, 15));
        lastName.setBounds(170, 60, 190, 20);
        personalInfoPanel.add(lastName);

        JLabel phoneNumberLbl = new JLabel("Phone Number");
        phoneNumberLbl.setFont(new Font("Arial", Font.PLAIN, 15));
        phoneNumberLbl.setBounds(20, 90, 120, 20);
        personalInfoPanel.add(phoneNumberLbl);
        phoneNumber = new JTextField();
        phoneNumber.setFont(new Font("Arial", Font.PLAIN, 15));
        phoneNumber.setBounds(170, 90, 190, 20);
        personalInfoPanel.add(phoneNumber);

        JLabel emailLbl = new JLabel("Email");
        emailLbl.setFont(new Font("Arial", Font.PLAIN, 15));
        emailLbl.setBounds(20, 120, 120, 20);
        personalInfoPanel.add(emailLbl);
        email = new JTextField();
        email.setFont(new Font("Arial", Font.PLAIN, 15));
        email.setBounds(170, 120, 190, 20);
        personalInfoPanel.add(email);

        c.add(personalInfoPanel);

        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));
        accountInfoPanel.setBounds(40, 280, 400, 100);
        accountInfoPanel.setLayout(null);

        JLabel usernameLbl = new JLabel("Username");
        usernameLbl.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameLbl.setBounds(20, 30, 120, 20);
        accountInfoPanel.add(usernameLbl);
        userName = new JTextField();
        userName.setFont(new Font("Arial", Font.PLAIN, 15));
        userName.setBounds(170, 30, 190, 20);
        accountInfoPanel.add(userName);

        JLabel passwordLbl = new JLabel("Password");
        passwordLbl.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordLbl.setBounds(20, 60, 120, 20);
        accountInfoPanel.add(passwordLbl);
        password = new JTextField("defaultpassword");
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setBounds(170, 60, 190, 20);
        accountInfoPanel.add(password);
        c.add(accountInfoPanel);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 400, 100, 40);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveClerkToDatabase();
            }
        });
        add(submitButton);
    }

    //FIXME: SET ROLE TO GUEST
    public void saveClerkToDatabase() {

        String userFirstName = firstName.getText();
        String userLastName = lastName.getText();
        String userPhone = phoneNumber.getText();
        String userEmail = email.getText();
        String guestUsername = userName.getText();
        String userPassword = password.getText();
        //FIXME
        Role role = Role.CLERK;

        if (userFirstName.isEmpty() || userLastName.isEmpty() || userPhone.isEmpty() ||
                userEmail.isEmpty() || guestUsername.isEmpty() || userPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            ClerkAccountController controller = new ClerkAccountController(new ClerkAccountDAO());
            //FIXME
            controller.insertAccount(new Account(userFirstName, userLastName, guestUsername, userEmail, Long.parseLong(userPhone), userPassword, role));



            JOptionPane.showMessageDialog(this, "Account registered.");
            dispose();
        } catch (RuntimeException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}