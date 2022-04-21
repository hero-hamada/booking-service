package com.epam.bookingservice.dao.storage;

import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
import com.epam.bookingservice.model.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.epam.bookingservice.model.Ticket.Category.BAR;
import static com.epam.bookingservice.model.Ticket.Category.PREMIUM;
import static com.epam.bookingservice.model.Ticket.Category.STANDARD;
import static java.util.Calendar.APRIL;
import static java.util.Calendar.MAY;

@Getter
@Component
public class DataStorage {

    private List<User> users;
    private List<Event> events;
    private List<Ticket> tickets;

    @PostConstruct
    private void initUsers() {
        users = new ArrayList<>();
//        users.add(new User(1L, "Rem", "r@gmail.com"));
//        users.add(new User(2L, "Emilia", "e@gmail.com"));
//        users.add(new User(3L, "Subaru", "s@gmail.com"));
    }

    @PostConstruct
    private void initEvents() {
        events = new ArrayList<>();
//        events.add(new Event(1L, "Event", LocalDate.of(2022, MAY, 15)));
//        events.add(new Event(2L, "Event1", LocalDate.of(2022, APRIL, 12)));
//        events.add(new Event(3L, "Event42", LocalDate.of(2022, APRIL, 13)));
    }

    @PostConstruct
    private void initTickets() {
        tickets = new ArrayList<>();
//        tickets.add(new Ticket(1L, 1L, 1L, BAR, 45));
//        tickets.add(new Ticket(2L, 2L, 3L, STANDARD, 1));
//        tickets.add(new Ticket(3L, 3L, 2L, PREMIUM, 143));
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void setUser(Integer indexOfOldUser, User newUser) {
        users.set(indexOfOldUser, newUser);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void setEvent(Integer indexOfEventToBeSet, Event event) {
        events.set(indexOfEventToBeSet, event);
    }
}