package org.bearluxury;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

//Window
public class HotelManagementSystem extends JFrame implements DateChangeListener, ActionListener {

    ImageIcon icon;
    Color backgroundColor = new Color(232,223,185,255);
    Color blackColor = new Color(1,12,15,255);
    Font font = new Font("Goudy Old Style", Font.PLAIN, 30);
    //Color goldColor = new Color(170,141,71,255);

    // Panels
    JPanel mainPanel;
    JPanel outerNorthPanel;
    JPanel outerSouthPanel;
    JPanel outerEastPanel;
    JPanel outerWestPanel;
    JPanel northPanel;
    JPanel centerPanel;
    JPanel bedsPanel;
    JPanel roomsPanel;
    JPanel southPanel;

    // Labels
    JLabel checkInLabel;
    JLabel checkOutLabel;
    JLabel bedsLabel;
    JLabel roomsLabel;

    // Fields
    JTextField bedsField;
    JTextField roomsField;

    // Buttons
    JButton minusBedsButton;
    JButton plusBedsButton;
    JButton minusRoomButton;
    JButton plusRoomButton;
    JButton checkAvailabilityButton;

    // Date pickers
    DatePicker checkInDatePicker;
    DatePicker checkOutDatePicker;

    // Attributes
    LocalDate checkInDate;
    LocalDate checkOutDate;
    private int numBeds;
    private int numRooms;

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

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        northPanel = new JPanel();
        northPanel.setBackground(backgroundColor);
        northPanel.setLayout(new GridLayout(2, 2, 10, 10));

        checkInLabel = new JLabel("Check in", SwingConstants.LEFT);
        checkInLabel.setForeground(blackColor);
        checkInLabel.setFont(font);

        checkOutLabel = new JLabel("Check out", SwingConstants.LEFT);
        checkOutLabel.setFont(font);
        checkOutLabel.setForeground(blackColor);

        northPanel.add(checkInLabel);
        northPanel.add(checkOutLabel);

        DatePickerSettings checkInDatePickerSettings = new DatePickerSettings();
        checkInDatePicker = new DatePicker(checkInDatePickerSettings);
        checkInDatePickerSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(1));
        checkInDatePicker.setDateToToday();
        checkInDatePicker.setPreferredSize(new Dimension(500, 0));
        checkInDatePicker.addDateChangeListener(this);

        DatePickerSettings checkOutDatePickerSettings = new DatePickerSettings();
        checkOutDatePicker = new DatePicker(checkOutDatePickerSettings);
        checkOutDatePickerSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(1));
        checkOutDatePicker.setDate(LocalDate.now().plusDays(2));
        checkOutDatePicker.addDateChangeListener(this);

        northPanel.add(checkInDatePicker);
        northPanel.add(checkOutDatePicker);

        centerPanel = new JPanel();
        centerPanel.setBackground(backgroundColor);
        centerPanel.setLayout(new GridLayout(2, 2, 10, 10));

        bedsLabel = new JLabel("Beds", SwingConstants.CENTER);
        bedsLabel.setForeground(blackColor);
        bedsLabel.setFont(font);
        roomsLabel = new JLabel("Rooms", SwingConstants.CENTER);
        roomsLabel.setForeground(blackColor);
        roomsLabel.setFont(font);

        centerPanel.add(bedsLabel);
        centerPanel.add(roomsLabel);

        bedsPanel = new JPanel();
        bedsPanel.setBackground(backgroundColor);
        bedsPanel.setLayout(new FlowLayout());
        minusBedsButton = new JButton("-");
        minusBedsButton.setEnabled(false);
        minusBedsButton.addActionListener(this);
        bedsField = new JTextField("1", 3);
        plusBedsButton = new JButton("+");
        plusBedsButton.addActionListener(this);
        bedsPanel.add(minusBedsButton);
        bedsPanel.add(bedsField);
        bedsPanel.add(plusBedsButton);

        roomsPanel = new JPanel();
        roomsPanel.setBackground(backgroundColor);
        roomsPanel.setLayout(new FlowLayout());
        minusRoomButton = new JButton("-");
        minusRoomButton.setEnabled(false);
        minusRoomButton.addActionListener(this);
        roomsField = new JTextField("1", 3);
        plusRoomButton = new JButton("+");
        plusRoomButton.addActionListener(this);
        roomsPanel.add(minusRoomButton);
        roomsPanel.add(roomsField);
        roomsPanel.add(plusRoomButton);

        centerPanel.add(bedsPanel);
        centerPanel.add(roomsPanel);

        southPanel = new JPanel();
        southPanel.setBackground(backgroundColor);

        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.setOpaque(true);
        checkAvailabilityButton.setBorderPainted(false);
        checkAvailabilityButton.addActionListener(this);

        southPanel.add(checkAvailabilityButton);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        outerNorthPanel = new JPanel();
        outerNorthPanel.setBackground(backgroundColor);
        outerNorthPanel.setPreferredSize(new Dimension(getContentPane().getWidth(), 200));
        outerEastPanel = new JPanel();
        outerEastPanel.setBackground(backgroundColor);
        outerEastPanel.setPreferredSize(new Dimension(200, getContentPane().getHeight()));
        outerWestPanel = new JPanel();
        outerWestPanel.setBackground(backgroundColor);
        outerWestPanel.setPreferredSize(new Dimension(200, getContentPane().getHeight()));
        outerSouthPanel = new JPanel();
        outerSouthPanel.setBackground(backgroundColor);
        outerSouthPanel.setPreferredSize(new Dimension(getContentPane().getWidth(), 200));

        add(outerNorthPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(outerEastPanel, BorderLayout.EAST);
        add(outerWestPanel, BorderLayout.WEST);
        add(outerSouthPanel, BorderLayout.SOUTH);


        numBeds = 1;
        numRooms = 1;

        checkInDate = checkInDatePicker.getDate();
        checkOutDate = checkOutDatePicker.getDate();
    }

    @Override
    public void dateChanged(DateChangeEvent dateChangeEvent) {
        checkInDate = checkInDatePicker.getDate();
        checkOutDate = checkOutDatePicker.getDate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == minusBedsButton) {
            numBeds--;
            if (numBeds <= 1) {
                minusBedsButton.setEnabled(false);
            }
            bedsField.setText(String.valueOf(numBeds));
        } else if (e.getSource() == plusBedsButton) {
            numBeds++;
            bedsField.setText(String.valueOf(numBeds));
            minusBedsButton.setEnabled(true);
        } else if (e.getSource() == minusRoomButton) {
            numRooms--;
            if (numRooms <= 1) {
                minusRoomButton.setEnabled(false);
            }
            roomsField.setText(String.valueOf(numRooms));
        } else if (e.getSource() == plusRoomButton) {
            numRooms++;
            roomsField.setText(String.valueOf(numRooms));
            minusRoomButton.setEnabled(true);
        } else if (e.getSource() == checkAvailabilityButton) {
            System.out.println("Check in date: " + checkInDate);
            System.out.println("Check out date: " + checkOutDate);
            System.out.println("Number of beds: " + numBeds);
            System.out.println("Number of rooms: " + numRooms);
            dispose();
            HotelManagementSystem.openRoomCatalogPane(numBeds);
        }

    }
    private static void openRoomCatalogPane(int beds){
        RoomCatalog rooms = new RoomCatalog();
        rooms.setRooms(new RoomBuilder("RoomList.csv").getRoomList());
        AvaliableRoomsGUI catalogPane = new AvaliableRoomsGUI(rooms,beds);
        catalogPane.setVisible(true);
    }
    public static void openHotelManagmentSystem(){
        FlatLightLaf.setup();
        HotelManagementSystem window = new HotelManagementSystem();
        window.setVisible(true);
    }

    public static void openRegisterPane() {
        RegisterPane register = new RegisterPane();
        register.setVisible(true);
    }

    public static void main(String[] args) {
        HotelHomePage page = new HotelHomePage();
        page.setVisible(true);

    }
}
