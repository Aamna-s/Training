package com.example.application.transaction;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
//    Optional<List<Balance>> findByAccountId(Long accountId);

//    @Query(value = "SELECT b.* FROM Transaction b JOIN accounts a ON a.account_Id = b.account_Id WHERE b.account_Id = ?1", nativeQuery = true)
//    Optional<List<Transaction>> findHistory(Long accountId);
    @Query(value = "SELECT b.* FROM banking.transaction b " +
            "WHERE b.account_Id = :accountId ",
            nativeQuery = true)
    List<Transaction> getThroughId(@Param("accountId") Long id);


}