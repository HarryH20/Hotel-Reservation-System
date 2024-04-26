package org.bearluxury.controllers;

import org.bearluxury.account.Account;
import org.bearluxury.account.AccountJDBCDAO;
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

class AccountControllerTest {

    @Mock
    private AccountJDBCDAO mockAccountDAO;

    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        accountController = new AccountController(mockAccountDAO);
    }

    @Test
    void insertAccount() {
        Account account = new Account("testfirstname", "testlastname", "testusername", "test@example.com", 12345678, "123", Role.GUEST);
        accountController.insertAccount(account);
        verify(mockAccountDAO, times(1)).insert(account);
    }

    @Test
    void getAccount() {
        String email = "test@example.com";
        Account account = new Account("testfirstname", "testlastname", "testusername", email, 12345678, "123", Role.GUEST);
        when(mockAccountDAO.get(email)).thenReturn(Optional.of(account));
        Optional<Account> retrievedAccount = accountController.getAccount(email);
        assertTrue(retrievedAccount.isPresent());
        assertEquals(account, retrievedAccount.get());
    }

    @Test
    void listAccounts() {
        Set<Account> accounts = new HashSet<>();
        accounts.add(new Account("testfirstname", "testlastname", "testusername", "test@example.com", 12345678, "123", Role.GUEST));
        accounts.add(new Account("testfirstname2", "testlastname2", "testusername2", "test2@example.com", 87654321, "123", Role.GUEST));
        when(mockAccountDAO.list()).thenReturn(accounts);
        Set<Account> retrievedAccounts = accountController.listAccounts();
        assertEquals(accounts, retrievedAccounts);
    }

    @Test
    void updateAccounts() {
        Account account = new Account("testfirstname", "testlastname", "testusername", "test@example.com", 12345678, "123", Role.GUEST);
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