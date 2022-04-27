package com.epam.bookingservice.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.bookingservice.security.ApplicationUserPermission.EVENT_READ;
import static com.epam.bookingservice.security.ApplicationUserPermission.EVENT_WRITE;
import static com.epam.bookingservice.security.ApplicationUserPermission.TICKET_READ;
import static com.epam.bookingservice.security.ApplicationUserPermission.TICKET_WRITE;
import static com.epam.bookingservice.security.ApplicationUserPermission.USER_READ;
import static com.epam.bookingservice.security.ApplicationUserPermission.USER_WRITE;

@Getter
public enum ApplicationUserRole {
    USER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            USER_READ,
            TICKET_READ,
            EVENT_READ,
            TICKET_WRITE,
            EVENT_WRITE,
            USER_WRITE
            )),
    ADMINTRAINEE(Sets.newHashSet(
            USER_READ,
            TICKET_READ,
            EVENT_READ
            ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
