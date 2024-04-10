package org.bearluxury.UI;

import org.bearluxury.controllers.RoomController;
import org.bearluxury.room.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class AddRoomPane extends JFrame {
    private JLabel roomNumberLabel;
    private JTextField roomNumber;
    private JLabel priceLabel;
    private JComboBox<String> priceChoices;
    private JLabel smokingStatusLabel;
    private JCheckBox smokingStatus;
    private JLabel roomTypeLabel;
    private JComboBox<String> roomTypes;
    private JLabel bedTypeLabel;
    private JComboBox<String> bedTypes;
    private JLabel qualityLabel;
    private JComboBox<String> qualityTypes;
    private JLabel bedsNumberLabel;
    private JSpinner bedNumber;
    private JButton createRoom;

    public AddRoomPane() {
        setTitle("Add Room");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        roomNumberLabel = new JLabel("Room Number");
        roomNumber = new JTextField();
        priceLabel = new JLabel("Desired Price: ");
        String[] priceOptions = {"70", "100","150", "200", "Other"};
        priceChoices = new JComboBox<>(priceOptions);
        smokingStatusLabel = new JLabel("Can Smoke: ");
        smokingStatus = new JCheckBox();
        roomTypeLabel = new JLabel("Room Type: ");
        String[] roomOptions = {ROOM_TYPE.VINTAGE.csvFormat(),ROOM_TYPE.URBAN.csvFormat(),
                ROOM_TYPE.NATURE.csvFormat()};
        roomTypes = new JComboBox<>(roomOptions);
        bedTypeLabel  = new JLabel("Bed: ");
        String[] bedOptions = {BED_TYPE.KING.toString(), BED_TYPE.QUEEN.toString(),
                BED_TYPE.FULL.toString(),BED_TYPE.TWIN.toString()};
        bedTypes = new JComboBox<>(bedOptions);
        qualityLabel = new JLabel("Room Quality: ");
        String[] qualityOptions = {QUALITY_LEVEL.EXECUTIVE.csvFormat(),QUALITY_LEVEL.BUSINESS.csvFormat(),
                QUALITY_LEVEL.COMFORT.csvFormat(),QUALITY_LEVEL.ECONOMY.csvFormat()};
        qualityTypes = new JComboBox<>(qualityOptions);
        bedsNumberLabel = new JLabel("Number Of Beds:");
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 8, 1);
        bedNumber = new JSpinner(spinnerModel);
        createRoom = new JButton("Add Room");
        createRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {double priceSelection = getPriceSelection();
                saveRoom(priceSelection);
            }
        });



        add(roomNumberLabel);
        add(roomNumber);
        add(priceLabel);
        add(priceChoices);
        add(smokingStatusLabel);
        add(smokingStatus);
        add(roomTypeLabel);
        add(roomTypes);
        add(bedTypeLabel);
        add(bedTypes);
        add(qualityLabel);
        add(qualityTypes);
        add(bedsNumberLabel);
        add(bedNumber);
        add(createRoom);
    }

    private void saveRoom(double priceSelection) {

        try {
            RoomController controller = new RoomController(new RoomJDBCDAO());
            boolean added = false;
            RoomBuilder builder = new RoomBuilder("src/main/resources/RoomList.csv");
            controller.insertRoom(new Room(
                    Integer.parseInt(roomNumber.getText()),
                    priceSelection,
                    smokingStatus.isSelected(),
                    builder.readAsRoomType(roomTypes.getSelectedItem().toString()),
                    builder.readAsBedType(bedTypes.getSelectedItem().toString()),
                    builder.readAsQualityLevel(qualityTypes.getSelectedItem().toString()),
                    Integer.parseInt(bedNumber.getValue().toString())

            ));

            showConfirmationDialog();
        }catch (Exception exc){
            showFailureDialog();
        }
    }
    private double getPriceSelection() {
        double priceSelection = 0;
        if (priceChoices.getSelectedItem().toString().equals("Other")) {
            String enteredPrice = JOptionPane.showInputDialog(null, "Enter Desired Price: ");
            if (enteredPrice != null) {
                priceSelection = Double.parseDouble(enteredPrice);
            }
        }
        else {
            priceSelection = Double.parseDouble(priceChoices.getSelectedItem().toString());
        }
        return priceSelection;
    }

    private void showConfirmationDialog() {
        int selection = JOptionPane.showConfirmDialog(null, "Room Created! " +
                "Would you like to add another?");
        if (selection == JOptionPane.YES_OPTION) {
            dispose();
            openAddRoomPane();
        } else {
            dispose();
        }
    }
    private void showFailureDialog() {
        int selection = JOptionPane.showConfirmDialog(null, "Room already exists! " +
                "Would you like to add another?");
        if (selection == JOptionPane.YES_OPTION) {
            dispose();
            openAddRoomPane();
        } else {
            dispose();
        }
    }

    public static void openAddRoomPane(){
        AddRoomPane pane = new AddRoomPane();
        pane.setVisible(true);
    }

}
