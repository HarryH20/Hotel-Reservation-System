package org.bearluxury.controllers;

import org.bearluxury.account.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuestAccountControllerTest {

    @Mock
    private GuestAccountJDBCDAO mockAccountDAO;

    private GuestAccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountController = new GuestAccountController(mockAccountDAO);
    }

    @Test
    void insertAccount() {
        CreditCard card = new CreditCard("123", "Card Holder", "4/2025", "123");
        Guest account = new Guest("testfirstname", "testlastname", "test@example.com",
                12345678, "123", Role.GUEST, card);
        accountController.insertAccount(account);
        verify(mockAccountDAO, times(1)).insert(account);
    }

    @Test
    void getAccount() {
        CreditCard card = new CreditCard("123", "Card Holder", "4/2025", "123");
        String email = "test@example.com";
        Guest account = new Guest("testfirstname", "testlastname", email, 12345678, "123", Role.CLERK, card);
        when(mockAccountDAO.get(email)).thenReturn(Optional.of(account));
        Optional<Guest> retrievedAccount = accountController.getAccount(email);
        assertTrue(retrievedAccount.isPresent());
        assertEquals(account, retrievedAccount.get());
    }

    @Test
    void listAccounts() {
        CreditCard card = new CreditCard("123", "Card Holder", "4/2025", "123");
        Set<Guest> accounts = new HashSet<>();
        accounts.add(new Guest("testfirstname", "testlastname", "test@example.com", 12345678, "123", Role.CLERK, card));
        accounts.add(new Guest("testfirstname2", "testlastname2", "test2@example.com", 87654321, "123", Role.CLERK, card));
        when(mockAccountDAO.list()).thenReturn(accounts);
        Set<Guest> retrievedAccounts = accountController.listAccounts();
        assertEquals(accounts, retrievedAccounts);
    }

    @Test
    void updateAccounts() {
        CreditCard card = new CreditCard("123", "Card Holder", "4/2025", "123");
        Guest account = new Guest("testfirstname", "testlastname", "test@example.com", 12345678, "123", Role.CLERK, card);
        String email = "test@example.com";
        accountController.updateAccounts(account, email);
        verify(mockAccountDAO, times(1)).update(account, email);
    }

    @Test
    void deleteAccounts() {
        String email = "test@example.com";
        when(mockAccountDAO.delete(email)).thenReturn(true);
        assertTrue(accountController.deleteAccounts(email));
        verify(mockAccountDAO, times(1)).delete(email);
    }

    @Test
    void clearAccounts() {
        accountController.clearAccounts();
        verify(mockAccountDAO, times(1)).clear();
    }

    @Test
    void closeConnection() {
        accountController.closeConnection();
        verify(mockAccountDAO, times(1)).close();
    }
}

