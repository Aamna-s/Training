package com.example.application.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.application.account.Account;
import com.example.application.account.AccountRepository;
import com.example.application.exceptionhandling.TransactionException;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TransactionService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TransactionServiceDiffblueTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    /**
     * Method under test: {@link TransactionService#save(Transaction)}
     */
    @Test
    void testSave() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId(1L);
        transaction.setAmount(10.0f);
        transaction.setDate(LocalDate.of(1970, 1, 1));
        transaction.setDbCrIndicator("Db Cr Indicator");
        transaction.setToFromAccountId(1L);
        transaction.setTransactionId(1L);
        when(transactionRepository.save(Mockito.<Transaction>any())).thenReturn(transaction);

        Account account = new Account();
        account.setAccountId(1L);
        account.setActive(true);
        account.setAddress("42 Main St");
        account.setBankBalance(10.0f);
        account.setName("Name");
        account.setPassword("iloveyou");
        account.setRoles("Roles");
        account.setUseremail("jane.doe@example.org");
        account.setUsername("janedoe");
        Optional<Account> ofResult = Optional.of(account);

        Account account2 = new Account();
        account2.setAccountId(1L);
        account2.setActive(true);
        account2.setAddress("42 Main St");
        account2.setBankBalance(10.0f);
        account2.setName("Name");
        account2.setPassword("iloveyou");
        account2.setRoles("Roles");
        account2.setUseremail("jane.doe@example.org");
        account2.setUsername("janedoe");
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account2);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Transaction transaction2 = new Transaction();
        transaction2.setAccountId(1L);
        transaction2.setAmount(10.0f);
        transaction2.setDate(LocalDate.of(1970, 1, 1));
        transaction2.setDbCrIndicator("Db Cr Indicator");
        transaction2.setToFromAccountId(1L);
        transaction2.setTransactionId(1L);

        // Act
        Transaction actualSaveResult = transactionService.save(transaction2);

        // Assert
        verify(accountRepository, atLeast(1)).findById(eq(1L));
        verify(accountRepository, atLeast(1)).save(isA(Account.class));
        verify(transactionRepository, atLeast(1)).save(Mockito.<Transaction>any());
        assertEquals("DEBIT", transaction2.getDbCrIndicator());
        assertSame(transaction2, actualSaveResult);
    }

    /**
     * Method under test: {@link TransactionService#save(Transaction)}
     */
    @Test
    void testSave2() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setAccountId(1L);
        transaction.setAmount(10.0f);
        transaction.setDate(LocalDate.of(1970, 1, 1));
        transaction.setDbCrIndicator("Db Cr Indicator");
        transaction.setToFromAccountId(1L);
        transaction.setTransactionId(1L);
        when(transactionRepository.save(Mockito.<Transaction>any())).thenReturn(transaction);

        Account account = new Account();
        account.setAccountId(1L);
        account.setActive(true);
        account.setAddress("42 Main St");
        account.setBankBalance(10.0f);
        account.setName("Name");
        account.setPassword("iloveyou");
        account.setRoles("Roles");
        account.setUseremail("jane.doe@example.org");
        account.setUsername("janedoe");
        Optional<Account> ofResult = Optional.of(account);
        when(accountRepository.save(Mockito.<Account>any())).thenThrow(new TransactionException("An error occurred"));
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Transaction transaction2 = new Transaction();
        transaction2.setAccountId(1L);
        transaction2.setAmount(10.0f);
        transaction2.setDate(LocalDate.of(1970, 1, 1));
        transaction2.setDbCrIndicator("Db Cr Indicator");
        transaction2.setToFromAccountId(1L);
        transaction2.setTransactionId(1L);

        // Act and Assert
        assertThrows(TransactionException.class, () -> transactionService.save(transaction2));
        verify(accountRepository, atLeast(1)).findById(eq(1L));
        verify(accountRepository).save(isA(Account.class));
    }

    /**
     * Method under test: {@link TransactionService#save(Transaction)}
     */
    @Test
    void testSave3() {
        // Arrange
        Account account = new Account();
        account.setAccountId(1L);
        account.setActive(true);
        account.setAddress("42 Main St");
        account.setBankBalance(10.0f);
        account.setName("Name");
        account.setPassword("iloveyou");
        account.setRoles("admin");
        account.setUseremail("jane.doe@example.org");
        account.setUsername("janedoe");
        Optional<Account> ofResult = Optional.of(account);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Transaction transaction = new Transaction();
        transaction.setAccountId(1L);
        transaction.setAmount(10.0f);
        transaction.setDate(LocalDate.of(1970, 1, 1));
        transaction.setDbCrIndicator("Db Cr Indicator");
        transaction.setToFromAccountId(1L);
        transaction.setTransactionId(1L);

        // Act and Assert
        assertThrows(TransactionException.class, () -> transactionService.save(transaction));
        verify(accountRepository, atLeast(1)).findById(eq(1L));
    }

    /**
     * Method under test: {@link TransactionService#save(Transaction)}
     */
    @Test
    void testSave4() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Transaction transaction = new Transaction();
        transaction.setAccountId(1L);
        transaction.setAmount(10.0f);
        transaction.setDate(LocalDate.of(1970, 1, 1));
        transaction.setDbCrIndicator("Db Cr Indicator");
        transaction.setToFromAccountId(1L);
        transaction.setTransactionId(1L);

        // Act and Assert
        assertThrows(TransactionException.class, () -> transactionService.save(transaction));
        verify(accountRepository, atLeast(1)).findById(eq(1L));
    }
}
