package org.bearluxury.controllers;

import org.bearluxury.Billing.SaleJDBCDAO;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.shop.Sale;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
/**
 * The sale controller provides methods to interact with sale data in the database.
 * It utilizes a SaleJDBCDAO object for database operations.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class SaleController {
    private SaleJDBCDAO saleDAO;

    /**
     * Constructs a SaleController with the specified SaleJDBCDAO.
     *
     * @param saleDao the SaleJDBCDAO instance to be used for database operations
     */
    public SaleController(SaleJDBCDAO saleDao){
        this.saleDAO = saleDao;
    }

    /**
     * Inserts a new sale into the database.
     *
     * @param sale the sale to be inserted
     */
    public void insertSale(Sale sale) {
        try {
            saleDAO.insert(sale);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a set of all sales stored in the database.
     *
     * @return a Set containing all sales stored in the database
     */
    public Set<Sale> listSale() {
        return saleDAO.list();
    }

    /**
     * Retrieves a set of sales associated with a specific account ID.
     *
     * @param acctId the account ID to retrieve sales for
     * @return a Set containing sales associated with the specified account ID
     */
    public Set<Sale> listSale(int acctId) {
        return saleDAO.listSalesByAccountId(acctId);
    }

    /**
     * Deletes all sales associated with a specific account ID from the database.
     *
     * @param acctId the account ID to delete sales for
     */
    public void deleteSaleByAcctId(int acctId){
        saleDAO.deleteSalesByAccountId(acctId);
    }

    /**
     * Deletes a sale from the database based on its sale ID.
     *
     * @param saleId the sale ID of the sale to delete
     */
    public void deleteSaleBySaleId(int saleId){
        saleDAO.deleteSaleById(saleId);
    }

    /**
     * Clears all sales from the database.
     */
    public void clearReservation() {
        saleDAO.clear();
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        saleDAO.close();
    }
}