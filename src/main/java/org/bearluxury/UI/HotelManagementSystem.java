package org.bearluxury.UI;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import org.bearluxury.account.Role;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationBuilder;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.room.Room;
import org.bearluxury.room.RoomBuilder;
import org.bearluxury.room.RoomCatalog;
import org.bearluxury.room.RoomJDBCDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//Window
public class HotelManagementSystem  {

    public static void openRoomCatalogPane(int beds, LocalDate checkIn, LocalDate checkOut, Role role){
        RoomCatalog rooms = new RoomCatalog();
        rooms.setRooms(new RoomBuilder("src/main/resources/RoomList.csv").getRoomList());
        AvaliableRoomsGUI catalogPane = new AvaliableRoomsGUI(rooms,beds, checkIn, checkOut,role);
        catalogPane.setVisible(true);
    }

   //added homepages for user role
    /*
    public static void openHomePage() {
        HotelHomePage hotelHomePage = new HotelHomePage();
        hotelHomePage.setVisible(true);
    }

     */

    public static void openGuestHomePage() {
        GuestHomePage guestHomePage = new GuestHomePage(Role.GUEST);
        guestHomePage.setVisible(true);
    }

    public static void openClerkHomePage() {
        ClerkHomePage clerkHomePage = new ClerkHomePage(Role.CLERK);
        clerkHomePage.setVisible(true);
    }

    public static void openAdminHomePage() {
        AdminHomePage adminHomePage = new AdminHomePage(Role.ADMIN);
        adminHomePage.setVisible(true);
    }

    public static void openLoginPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }

    public static void openRegisterPage() {
        RegisterPage registerPage = new RegisterPage();
        registerPage.setVisible(true);
    }

    public static void openHotelManagmentSystem(Role role){
        InfoFilterPane window = new InfoFilterPane(role);
        window.setVisible(true);
    }

    public static void openRegisterPane() {
        RegisterPane register = new RegisterPane();
        register.setVisible(true);
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatLightLaf.setup();
        ReservationJDBCDAO data = new ReservationJDBCDAO();
        List<Reservation> reservations = new ArrayList<>();
        ReservationBuilder builder = new ReservationBuilder("src/main/resources/ReservationList.csv");
        List<Reservation> res = builder.getReservationList();
        RoomJDBCDAO dao = new RoomJDBCDAO();
        RoomBuilder builder1 = new RoomBuilder("src/main/resources/RoomList.csv");
        Set<Room> res1 = builder1.getRoomList();
        for(Room res4: res1){
            dao.insert(res4);
        }
        Set<Room> res2 = dao.list();
        for(Room res4: res2){
            System.out.println(res4.getRoomNumber());
        }
        openLoginPage();
    }

}
