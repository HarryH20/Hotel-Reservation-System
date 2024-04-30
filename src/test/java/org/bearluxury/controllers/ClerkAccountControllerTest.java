package org.bearluxury.controllers;

import org.bearluxury.account.Account;
import org.bearluxury.account.Clerk;
import org.bearluxury.account.ClerkAccountDAO;
import org.bearluxury.account.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClerkAccountControllerTest {

    @Mock
    private ClerkAccountDAO mockAccountDAO;

    private ClerkAccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountController = new ClerkAccountController(mockAccountDAO);
    }

    @Test
    void insertAccount() {
        Account account = new Clerk("testfirstname", "testlastname", "test@example.com", 12345678, "123", Role.CLERK);
        accountController.insertAccount(account);
        verify(mockAccountDAO, times(1)).insert(account);
    }

    @Test
    void getAccount() {
        String email = "test@example.com";
        Account account = new Account("testfirstname", "testlastname", email, 12345678, "123", Role.CLERK);
        when(mockAccountDAO.get(email)).thenReturn(Optional.of(account));
        Optional<Account> retrievedAccount = accountController.getAccount(email);
        assertTrue(retrievedAccount.isPresent());
        assertEquals(account, retrievedAccount.get());
    }

    @Test
    void listAccounts() {
        Set<Account> accounts = new HashSet<>();
        accounts.add(new Account("testfirstname", "testlastname", "test@example.com", 12345678, "123", Role.CLERK));
        accounts.add(new Account("testfirstname2", "testlastname2", "test2@example.com", 87654321, "123", Role.CLERK));
        when(mockAccountDAO.list()).thenReturn(accounts);
        Set<Account> retrievedAccounts = accountController.listAccounts();
        assertEquals(accounts, retrievedAccounts);
    }

    @Test
    void updateAccounts() {
        Account account = new Account("testfirstname", "testlastname", "test@example.com", 12345678, "123", Role.CLERK);
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
