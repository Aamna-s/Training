package com.example.application.transaction;


import com.example.application.account.Account;
import com.example.application.account.AccountRepository;
import com.example.application.exceptionhandling.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Optional<List<Transaction>> findByAccount() {
        Optional<List<Transaction>> transactionList = Optional.empty();
        Optional<Account> optionalAccount = accountRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            transactionList = transactionRepository.findHistory(account.getAccountId());

        }
        return transactionList;
    }

    public Transaction save(Transaction transaction) {
        Long fromAccountId = transaction.getAccountId();
        Long toAccountId = transaction.getToFromAccountId();

        Optional<Account> optionalFromAccount = accountRepository.findById(fromAccountId);
        Optional<Account> optionalToAccount = accountRepository.findById(toAccountId);

        if (optionalFromAccount.isEmpty()) {
            throw new TransactionException("From account with ID " + fromAccountId + " not found.");
        }

        if (optionalToAccount.isEmpty()) {
            throw new TransactionException("To account with ID " + toAccountId + " not found.");
        }

        Account fromAccount = optionalFromAccount.get();
        Account toAccount = optionalToAccount.get();
        float amount = transaction.getAmount();

        if ("admin".equals(toAccount.getRoles())) {
            throw new TransactionException("Unauthorized Transaction");
        }


        // Debit from fromAccount
        fromAccount.setBankBalance(fromAccount.getBankBalance() - amount);
        accountRepository.save(fromAccount);

        // Create debit transaction
        transaction.setDate(LocalDate.now());
        transaction.setDbCrIndicator("DEBIT");
        transactionRepository.save(transaction);

        // Credit to toAccount
        toAccount.setBankBalance(toAccount.getBankBalance() + amount);
        accountRepository.save(toAccount);

        // Create credit transaction
        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccountId(toAccount.getAccountId());
        creditTransaction.setDate(LocalDate.now());
        creditTransaction.setDbCrIndicator("CREDIT");
        creditTransaction.setToFromAccountId(fromAccount.getAccountId());
        creditTransaction.setAmount(amount);
        transactionRepository.save(creditTransaction);

        return transaction;
    }
     public List<Transaction> getThroughId(Long id){
        List<Transaction> list= transactionRepository.getThroughId(id);
           return list;

     }
}