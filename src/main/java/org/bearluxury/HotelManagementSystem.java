package org.bearluxury;

import javax.swing.*;
import java.awt.*;

public class HotelManagementSystem extends JFrame {

    ImageIcon icon;
    Color backgroundColor = new Color(232,223,185,255);
    Color blackColor = new Color(1,12,15,255);
    Color goldColor = new Color(170,141,71,255);

    Font f1 = new Font(Font.SANS_SERIF, Font.BOLD, 10);

    public HotelManagementSystem() {
        setTitle("Bear Luxury");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);

        icon = new ImageIcon("logo.png");
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDirectory);

        setIconImage(icon.getImage());

        JPanel northPanel = new JPanel();
        northPanel.setPreferredSize(new Dimension(northPanel.getPreferredSize().width, 200));
        northPanel.setBackground(backgroundColor);

        ImageIcon logo = new ImageIcon("bbl-logo.png");

        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logo);
        northPanel.add(logoLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(backgroundColor);
        centerPanel.setLayout(new GridLayout(2, 2, 1, 1));
        //centerPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        JLabel guestsLabel = new JLabel("Beds", SwingConstants.CENTER);
        guestsLabel.setForeground(blackColor);
        guestsLabel.setFont(new Font("Gill Sans", Font.PLAIN, 50));

        JLabel roomsLabel = new JLabel("Rooms", SwingConstants.CENTER);
        roomsLabel.setForeground(blackColor);

        centerPanel.add(guestsLabel);
        centerPanel.add(roomsLabel);

        JPanel guestsPanel = new JPanel();
        guestsPanel.setBackground(backgroundColor);
        guestsPanel.setLayout(new FlowLayout());
        JButton minusGuestButton = new JButton("-");
        JTextField guestsField = new JTextField("1", 3);
        JButton plusGuestButton = new JButton("+");
        guestsPanel.add(minusGuestButton);
        guestsPanel.add(guestsField);
        guestsPanel.add(plusGuestButton);

        JPanel roomsPanel = new JPanel();
        roomsPanel.setBackground(backgroundColor);
        roomsPanel.setLayout(new FlowLayout());
        JButton minusRoomButton = new JButton("-");
        JTextField roomsField = new JTextField("1", 3);
        JButton plusRoomButton = new JButton("+");
        roomsPanel.add(minusRoomButton);
        roomsPanel.add(roomsField);
        roomsPanel.add(plusRoomButton);

        centerPanel.add(guestsPanel);
        centerPanel.add(roomsPanel);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(backgroundColor);
        eastPanel.setPreferredSize(new Dimension(200, eastPanel.getPreferredSize().height));

        JPanel westPanel = new JPanel();
        westPanel.setBackground(backgroundColor);
        westPanel.setPreferredSize(new Dimension(200, westPanel.getPreferredSize().height));

        add(northPanel, BorderLayout.NORTH);
        add(eastPanel, BorderLayout.EAST);
        add(westPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        HotelManagementSystem window = new HotelManagementSystem();
        window.setVisible(true);
    }
}
