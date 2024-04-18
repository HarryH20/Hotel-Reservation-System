package org.bearluxury.UI;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.RoomController;
import org.bearluxury.product.ProductBuilder;
import org.bearluxury.product.ProductCatalog;
import org.bearluxury.room.RoomCatalog;
import org.bearluxury.room.RoomJDBCDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;


//Window
public class HotelManagementSystem  {

    public static void openRoomCatalogPane(int beds, LocalDate checkIn, LocalDate checkOut, Role role){
        try {
            RoomController rooms = new RoomController(new RoomJDBCDAO());
            RoomCatalog roomCatalog = new RoomCatalog();
            roomCatalog.setRooms(rooms.listRooms());
            AvaliableRoomsGUI catalogPane = new AvaliableRoomsGUI(roomCatalog, beds, checkIn, checkOut, role);
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

    /*public static void openRegisterPane() {
        RegisterPane register = new RegisterPane();
        register.setVisible(true);
    }*/

    public static void openShopHomePage() {
        ProductBuilder productBuilder = new ProductBuilder("src/main/resources/ProductList.csv");
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setProducts(productBuilder.getProductList());
        ShopHomePage shopHomePage = new ShopHomePage(productCatalog);
        shopHomePage.setVisible(true);
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatLightLaf.setup();
        openLoginPage();
    }
}
