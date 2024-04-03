package org.bearluxury;

import com.formdev.flatlaf.FlatLightLaf;
import java.time.LocalDate;

//Window
public class HotelManagementSystem  {

    public static void openRoomCatalogPane(int beds, LocalDate checkIn, LocalDate checkOut){
        RoomCatalog rooms = new RoomCatalog();
        rooms.setRooms(new RoomBuilder("RoomList.csv").getRoomList());
        AvaliableRoomsGUI catalogPane = new AvaliableRoomsGUI(rooms,beds, checkIn, checkOut);
        catalogPane.setVisible(true);
    }
    public static void openHotelManagmentSystem(){
        FlatLightLaf.setup();
        InfoFilterPane window = new InfoFilterPane();
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
