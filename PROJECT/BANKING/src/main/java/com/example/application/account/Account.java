package com.example.application.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents an account entity in the system.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity(name = "accounts")
public class Account implements UserDetails {
    @Id
    @Column
    private Long accountId;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String roles;

    @Email
    @Column
    private String useremail;

    @Column
    private String address;

    private float bankBalance;
    private boolean active;

    public Account(Account other) {
        this.accountId = other.accountId;
        this.username = other.username;
        this.name = other.name;
        this.useremail = other.useremail;
        this.address = other.address;
        this.password = other.password;
        this.roles = other.roles;
        this.active=other.active;
        this.bankBalance=other.bankBalance;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
