package org.bearluxury.controllers;

import org.bearluxury.Billing.SaleJDBCDAO;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.shop.Sale;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class SaleController {
    private SaleJDBCDAO saleDAO;
    public SaleController(SaleJDBCDAO saleDao){
        this.saleDAO = saleDao;
    }
    public void insertSale(Sale sale) {
        try {
            saleDAO.insert(sale);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public Set<Sale> listSale() {
        return saleDAO.list();
    }



    public void clearReservation() {
        saleDAO.clear();
    }
    public void closeConnection() {
        saleDAO.close();
    }
}
