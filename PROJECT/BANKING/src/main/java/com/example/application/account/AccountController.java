package com.example.application.account;

import com.example.application.Login.Login;
import com.example.application.Login.LoginResponse;
import com.example.application.services.AuthenticationService;
import com.example.application.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing account-related operations.
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    public AccountController(AccountService accountService, JwtService jwtService, AuthenticationService authenticationService, AccountRepository accountRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @PreAuthorize("hasAnyAuthority('admin','user')")
    @GetMapping("/{id}")
    public ResponseEntity<Account> findAccount(@PathVariable Long id) {
        Optional<Account> getAccount = accountService.findById(id);

        if (getAccount.isPresent()) {
            Account account = getAccount.get();
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<Account>> get(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "1000") Integer size) {
        return ResponseEntity.ok(accountService.findAll(page, size));
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {


        try {
            Account newAccount = accountService.createAccount(account);
            return ResponseEntity.ok(newAccount);
        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }


    @PreAuthorize("hasAnyAuthority('admin')")
    @PatchMapping
    public ResponseEntity<?> updateAccount( @RequestBody Account account) {
        try {

            Account updatedAccount = accountService.updateAccount(account);
            return ResponseEntity.ok(updatedAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Login loginAccountDto) throws Exception {
        Account authenticatedUser = authenticationService.authenticate(loginAccountDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        Optional<Account> optionalAccount = accountRepository.findByUsername(loginAccountDto.getUsername());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            loginResponse.setAccount(account);
            loginResponse.setExpiresIn(Long.valueOf(5));
        }

        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
