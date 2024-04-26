package org.bearluxury.UI;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import org.bearluxury.UI.shopUI.ShopHomePage;
import org.bearluxury.account.Account;
import org.bearluxury.account.AccountJDBCDAO;
import org.bearluxury.account.Guest;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.AccountController;
import org.bearluxury.controllers.ProductController;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.controllers.RoomController;
import org.bearluxury.product.ProductJDBCDAO;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.room.RoomCatalog;

import org.bearluxury.room.RoomJDBCDAO;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;


//Window
public class HotelManagementSystem  {

    public static void openRoomCatalogPane(int beds, LocalDate checkIn, LocalDate checkOut){
        try {
            RoomController rooms = new RoomController(new RoomJDBCDAO());
            RoomCatalog roomCatalog = new RoomCatalog();
            roomCatalog.setRooms(rooms.listRooms());
            ReservationController reservationController = new ReservationController(new ReservationJDBCDAO());
            ReservationCatalog reservationCatalog = new ReservationCatalog();
            reservationCatalog.setReservations(reservationController.listReservations());
            AvaliableRoomsGUI catalogPane = new AvaliableRoomsGUI(roomCatalog, beds, checkIn, checkOut,reservationCatalog);
            catalogPane.setVisible(true);
        }catch (SQLException exc){
            exc.printStackTrace();
        }
    }
    ///

    //added homepages for user role
    /*
    public static void openHomePage() {
        HotelHomePage hotelHomePage = new HotelHomePage();
        hotelHomePage.setVisible(true);
    }

     */

    public static void openGuestHomePage() {
        GuestHomePage guestHomePage = new GuestHomePage();
        guestHomePage.setVisible(true);
    }

    public static void openClerkHomePage() {
        ClerkHomePage clerkHomePage = new ClerkHomePage();
        clerkHomePage.setVisible(true);
    }

    public static void openAdminHomePage() {
        AdminHomePage adminHomePage = new AdminHomePage();
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

    public static void openHotelManagmentSystem(){
        InfoFilterPane window = new InfoFilterPane();
        window.setVisible(true);
    }
    public static void openBillingPage(){
        BillingPage page = new BillingPage();
        page.setVisible(true);
    }

    /*public static void openRegisterPane() {
        RegisterPane register = new RegisterPane();
        register.setVisible(true);
    }*/

    public static void openShopHomePage() {
        try {
            ProductJDBCDAO productDAO = new ProductJDBCDAO();
            ProductController productController = new ProductController(productDAO);
            ShopHomePage shopHomePage = new ShopHomePage(productController);
            shopHomePage.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatLightLaf.setup();
        AccountController controller = new AccountController(new AccountJDBCDAO());
        openLoginPage();
    }


}
