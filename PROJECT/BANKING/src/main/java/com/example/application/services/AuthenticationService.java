package com.example.application.services;

import com.example.application.Login.Login;
import com.example.application.account.Account;
import com.example.application.account.AccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for handling authentication and user signup.
 */
@Service
public class AuthenticationService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(
            AccountRepository accountRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Signs up a new user.
     *
     * @param input the account details for signup
     * @return the saved account
     */
    public Account signup(Account input) {
        Account account = new Account();
        account.setUsername(input.getUsername());
        account.setPassword(passwordEncoder.encode(input.getPassword()));
        account.setAddress(input.getAddress());
        account.setRoles(input.getRoles());
        return accountRepository.save(account);
    }

    /**
     * Authenticates a user.
     *
     * @param input the login credentials
     * @return the authenticated account
     */
    public Account authenticate(Login input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return accountRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + input.getUsername()));
    }
}
