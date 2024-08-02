package com.example.application.account;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Account} entity.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an account by its username.
     *
     * @param username the username to search for
     * @return an Optional containing the account if found, otherwise empty
     */
    Optional<Account> findByUsername(String username);
    @Query(value="SELECT * FROM banking.accounts  WHERE active = true ", nativeQuery = true)
    Page<Account> findAllActive(Pageable pageable);
}
