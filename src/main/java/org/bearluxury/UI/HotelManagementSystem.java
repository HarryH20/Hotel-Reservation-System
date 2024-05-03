package org.bearluxury.UI;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import org.bearluxury.Billing.SaleJDBCDAO;
import org.bearluxury.UI.shopUI.ShopHomePage;
import org.bearluxury.account.*;
import org.bearluxury.controllers.*;
import org.bearluxury.product.ProductJDBCDAO;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.room.RoomCatalog;

import org.bearluxury.room.RoomJDBCDAO;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

/**
 * The HotelManagementSystem class is the main class that handles the initial setup for UI and Controllers.
 * Also used to open various UI frames.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class HotelManagementSystem  {

    /**
     * Opens the room catalog pane with the specified number of beds and date range.
     *
     * @param beds     The number of beds required.
     * @param checkIn  The check-in date.
     * @param checkOut The check-out date.
     */
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

    /**
     * Opens the guest home page.
     */
    public static void openGuestHomePage() {
        GuestHomePage guestHomePage = new GuestHomePage();
        guestHomePage.setVisible(true);
    }

    /**
     * Opens the clerk home page.
     */
    public static void openClerkHomePage() {
        ClerkHomePage clerkHomePage = new ClerkHomePage();
        clerkHomePage.setVisible(true);
    }

    /**
     * Opens the admin home page.
     */
    public static void openAdminHomePage() {
        AdminHomePage adminHomePage = new AdminHomePage();
        adminHomePage.setVisible(true);
    }

    /**
     * Opens the login page.
     */
    public static void openLoginPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }

    /**
     * Opens the register page.
     */
    public static void openRegisterPage() {
        RegisterPage registerPage = new RegisterPage();
        registerPage.setVisible(true);
    }

    /**
     * Opens the clerk register pane.
     *
     * @param modify indicates whether the pane is used for modifying or not
     */
    public static void openClerkRegisterPane(boolean modify) {
        ClerkRegisterPane clerkRegisterPane = new ClerkRegisterPane(modify);
        clerkRegisterPane.setVisible(true);
    }

    /**
     * Opens the info filter pane.
     */
    public static void openHotelManagmentSystem(){
        InfoFilterPane window = new InfoFilterPane();
        window.setVisible(true);
    }

    /**
     * Opens the billing page.
     */
    public static void openBillingPage(){
        BillingPage page = new BillingPage(0);
        page.setVisible(true);
    }

    /**
     * Opens the shop home page.
     */
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

    /**
     * Initializes the system and opens the login page.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatLightLaf.setup();
        ClerkAccountController controller = new ClerkAccountController(new ClerkAccountDAO());
        Optional<Account> existingAdmin = controller.getAccount("admin@admin.com");
        ReservationController controller1 = new ReservationController(new ReservationJDBCDAO());
        if (!existingAdmin.isPresent()) {
            // Create a Clerk with Admin role
            Account admin = new Account("Admin", "Admin", "admin@admin.com", 1234567890, "adminpassword", Role.ADMIN);
            // Insert the Clerk into the database
            controller.insertAccount(admin);
        }
        openLoginPage();
    }
}
