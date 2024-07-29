package com.example.application.Login;

import com.example.application.account.Account;
import lombok.Getter;
import lombok.Setter;

/**
 * Response object for login authentication.
 */
@Getter
@Setter
public class LoginResponse {

    /**
     * JWT token for authenticated user.
     */
    private String token;

    /**
     * Expiration time of the JWT token in seconds.
     */
    private Long expiresIn;

    /**
     * The account details of the authenticated user.
     */
    private Account account;

}
