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
    private  Account account;
    private String token;
    private Long expiresIn;



    public Account getAccount() {
        return account != null ? new Account(account) : null; // Assuming Account has a copy constructor
    }
    public void setAccount(Account account) {
        this.account = account != null ? new Account(account) : null; // Assuming Account has a copy constructor
    }
}

