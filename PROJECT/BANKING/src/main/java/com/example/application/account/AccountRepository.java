package com.example.application.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

}
