package com.example.application.transaction;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

import java.time.LocalDate;

    @Setter
    @Getter
    @Entity
    public class Transaction {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        private Long transactionId;

        private Long accountId;
        private Long toFromAccountId;

        private LocalDate date;
        private float amount;
        private String dbCrIndicator;
}
