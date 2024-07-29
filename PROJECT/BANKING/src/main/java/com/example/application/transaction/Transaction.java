package com.example.application.transaction;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
