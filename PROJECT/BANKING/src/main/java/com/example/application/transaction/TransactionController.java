package com.example.application.transaction;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }

    @PreAuthorize("hasAnyAuthority('user')")
    @PostMapping("")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction request) {
        if (request.getToFromAccountId().equals(request.getAccountId())) {
            return ResponseEntity.badRequest().body("To account and from account cannot be the same");
        }
        Transaction success = transactionService.save(request);
        return ResponseEntity.ok(success);
    }
    @PreAuthorize("hasAnyAuthority('admin','user')")
    @GetMapping("/Id/{id}")
    public ResponseEntity<List<Transaction>> GetTransactionById(@PathVariable("id") Long id) {
       List<Transaction> list=transactionService.getThroughId(id);
        return ResponseEntity.ok(list);
    }
}
