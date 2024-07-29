package com.example.application.account;

import com.example.application.exceptionhandling.TransactionException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(name);
        if (account.isEmpty()) {
            log.warn("Invalid user: {}", name.replace('\n', ' '));
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
        return accountRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    /**
     * Creates a new account.
     *
     * @param account the account to create
     * @return the created account
     */
    public Account createAccount(Account account) {
        Optional<Account> checkAccount=accountRepository.findByUsername(account.getUsername());
        if(checkAccount.isPresent())
        {
            throw new TransactionException("Username Already exsists." );
        }
        account.setBankBalance(100);
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
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            existingAccount.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        if (account.getUseremail() != null && !account.getUseremail().isEmpty()) {
            existingAccount.setUseremail(account.getUseremail());
        }
        if (account.getAddress() != null && !account.getAddress().isEmpty()) {
            existingAccount.setAddress(account.getAddress());
        }

        return accountRepository.save(existingAccount);
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
        accountRepository.deleteById(id);
    }
}
