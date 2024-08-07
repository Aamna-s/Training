package com.example.application.account;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link Account} entities.
 */
@Slf4j
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom = new SecureRandom(); // Use SecureRandom instead of Random

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) {
        Optional<Account> account = accountRepository.findByUsername(name);
        if (account.isEmpty()) {

            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(
                account.get().getUsername(),
                account.get().getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(account.get().getRoles())
        );
    }

    /**
     * Finds an account by its ID.
     *
     * @param accountId the ID of the account
     * @return an Optional containing the account if found
     */
    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    /**
     * Finds all accounts with pagination.
     *
     * @param page the page number
     * @param size the page size
     * @return a list of accounts
     */
    public List<Account> findAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 1000) {
            size = 1000;
        }
        Page<Account> resultPage = accountRepository.findAllActive(PageRequest.of(page, size));

        // Extract and return the list of accounts
        return resultPage.getContent();
    }

    public Account findByUsername(String username) {
        Optional<Account> checkAccount = accountRepository.findByUsername(username);
        return checkAccount.orElse(null);
    }

    /**
     * Creates a new account.
     *
     * @param account the account to create
     * @return the created account
     */
    public Account createAccount(Account account) {
        Optional<Account> checkAccount = accountRepository.findByUsername(account.getUsername());
        if (checkAccount.isPresent()) {
            throw new DataIntegrityViolationException("Username already exists");
        }
        account.setAccountId(generateUniqueAccountId());
        account.setBankBalance(100);
        account.setActive(true);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    /**
     * Updates an existing account.
     *
     * @param account the account with updated information
     * @return the updated account
     * @throws RuntimeException if the account is not found
     */
    public Account updateAccount(Account account) {
        if (!accountRepository.existsById(account.getAccountId())) {
            throw new RuntimeException("Account not found");
        }

        Account existingAccount = accountRepository.findById(account.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getUsername() != null && !account.getUsername().isEmpty()) {
            existingAccount.setUsername(account.getUsername());
        }
        if (account.getName() != null && !account.getName().isEmpty()) {
            existingAccount.setName(account.getName());
        }
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            existingAccount.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        if (account.getUseremail() != null && !account.getUseremail().isEmpty()) {
            existingAccount.setUseremail(account.getUseremail());
        }
        if (account.getAddress() != null && !account.getAddress().isEmpty()) {
            existingAccount.setAddress(account.getAddress());
        }
        accountRepository.save(existingAccount);

        return existingAccount;
    }

    /**
     * Deletes an account by its ID.
     *
     * @param id the ID of the account to delete
     * @throws RuntimeException if the account is not found
     */
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found");
        }
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setActive(false);
            accountRepository.save(account);
        }
    }

    public long generateUniqueAccountId() {
        long uniqueId;
        do {
            uniqueId = generateRandom10DigitNumber();
        } while (accountRepository.existsById(uniqueId));
        return uniqueId;
    }

    private long generateRandom10DigitNumber() {
        // Generate a random 10-digit number
        long min = 1000000000L; // Minimum 10-digit number
        long max = 9999999999L; // Maximum 10-digit number
        return min + (long) (secureRandom.nextDouble() * (max - min));
    }
}
