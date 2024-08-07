package com.example.application.account;

import com.example.application.Login.Login;
import com.example.application.Login.LoginResponse;
import com.example.application.services.AuthenticationService;
import com.example.application.services.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.http.HttpHeaders;



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

    @Autowired
    public AccountController(AccountService accountService, JwtService jwtService,
                             AuthenticationService authenticationService, AccountRepository accountRepository) {
        this.accountService = Objects.requireNonNull(accountService, "Account service cannot be null");
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.accountRepository = accountRepository;
    }

    @PreAuthorize("hasAnyAuthority('admin','user')")
    @GetMapping("/me/{id}")
    public ResponseEntity<Account> findAccount(@PathVariable Long id) {
        Optional<Account> getAccount = accountService.findById(id);
        return getAccount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<Account>> get(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                             @RequestParam(name = "size", defaultValue = "1000") Integer size) {
        return ResponseEntity.ok(accountService.findAll(page, size));
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account newAccount = accountService.createAccount(account);
            return ResponseEntity.ok(newAccount);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. Please try again later.");
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PatchMapping
    public ResponseEntity<?> updateAccount(@RequestBody Account account) {
        try {
            Account updatedAccount = accountService.updateAccount(account);
            return ResponseEntity.ok(updatedAccount);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
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
    public ResponseEntity<?> authenticate (@RequestBody Login loginAccountDto) {
        try {
            // Attempt to authenticate the user
            Account authenticatedUser = authenticationService.authenticate(loginAccountDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);

            Optional<Account> optionalAccount = accountRepository.findByUsername(loginAccountDto.getUsername());

            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                if (!account.isActive()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is not active");
                }
                loginResponse.setAccount(account);
                loginResponse.setExpiresIn(jwtService.getExpirationTime());
            } else {
                loginResponse.setExpiresIn(jwtService.getExpirationTime());
            }
          //  HttpHeaders headers = new HttpHeaders();

            return ResponseEntity.ok(loginResponse);
        } catch (UsernameNotFoundException e) {
            // Handle invalid username or password
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/{username}")
    public ResponseEntity<Account> findByUsername(@PathVariable String username) {
        Account account = accountService.findByUsername(username);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
