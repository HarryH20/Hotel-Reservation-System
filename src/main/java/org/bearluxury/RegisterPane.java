package org.bearluxury;

import javax.swing.*;
import java.awt.*;

public class RegisterPane extends JFrame {
    private Container c;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField userName;
    private JTextField email;
    private JTextField phoneNumber;
    private JTextField password;
    private JTextField cardNumber;
    private JTextField cardCVS;

    public RegisterPane() {
        setTitle("Account Registration");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        JPanel personalInfoPanel = new JPanel();
        personalInfoPanel.setBorder(BorderFactory.createTitledBorder("Personal Information"));
        personalInfoPanel.setBounds(20, 20, 400, 180);
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

        JLabel phoneNum = new JLabel("Phone Number");
        phoneNum.setFont(new Font("Arial", Font.PLAIN, 15));
        phoneNum.setBounds(20, 90, 120, 20);
        personalInfoPanel.add(phoneNum);
        phoneNumber = new JTextField();
        phoneNumber.setFont(new Font("Arial", Font.PLAIN, 15));
        phoneNumber.setBounds(170, 90, 190, 20);
        personalInfoPanel.add(phoneNumber);

        c.add(personalInfoPanel);

        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setBorder(BorderFactory.createTitledBorder("Account Information"));
        accountInfoPanel.setBounds(20, 220, 400, 180);
        accountInfoPanel.setLayout(null);

        c.add(accountInfoPanel);
    }
}
