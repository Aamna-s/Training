package com.example.application.transaction;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }
    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<List<Transaction>>> getTransactionById() {
        Optional<List<Transaction>> transactions = transactionService.findByAccount();
        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transactions);
    }
    @PreAuthorize("hasAnyAuthority('user')")
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction request) {
        if (request.getToFromAccountId().equals(request.getAccountId())) {
            return ResponseEntity.badRequest().body("To account and from account cannot be the same");
        }
        Transaction success = transactionService.save(request);
        return ResponseEntity.ok(success);
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("get/{id}")
    public ResponseEntity<List<Transaction>> GetTransactionById(@PathVariable("id") Long id) {
       List<Transaction> list=transactionService.getThroughId(id);
        return ResponseEntity.ok(list);
    }
}
