package com.epam.bookingservice.auth;

import com.epam.bookingservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

import static com.epam.bookingservice.security.ApplicationUserRole.ADMIN;
import static com.epam.bookingservice.security.ApplicationUserRole.USER;


public class ApplicationUserPrincipal implements UserDetails {

    private User user;

    private static final int ACCOUNT_EXPIRATION_IN_YEARS = 5;
    private static final int CREDENTIALS_EXPIRATION_IN_YEARS = 5;

    public ApplicationUserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole().equals(USER.name())? USER.getGrantedAuthorities() : ADMIN.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return LocalDate.now().isBefore(user.getRegisterDate().plusYears(ACCOUNT_EXPIRATION_IN_YEARS));
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return LocalDate.now().isBefore(user.getRegisterDate().plusYears(CREDENTIALS_EXPIRATION_IN_YEARS));
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
