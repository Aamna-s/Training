package com.example.application.transaction;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query(value = "SELECT b.* FROM banking.transaction b " +
            "WHERE b.account_Id = :accountId ",
            nativeQuery = true)
    List<Transaction> getThroughId(@Param("accountId") Long id);


}