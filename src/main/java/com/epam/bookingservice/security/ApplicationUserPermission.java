package com.epam.bookingservice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationUserPermission {
    USER_READ("user:read"),
    TICKET_READ("ticket:read"),
    EVENT_READ("event:read"),

    TICKET_WRITE("ticket:write"),
    EVENT_WRITE("event:write"),
    USER_WRITE("user:write");

    private final String permission;
}
