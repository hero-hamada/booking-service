package com.epam.bookingservice.facade;

import com.epam.bookingservice.controller.UserController;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.model.UserAccount;
import com.epam.bookingservice.service.EventService;
import com.epam.bookingservice.service.TicketService;
import com.epam.bookingservice.service.UserAccountService;
import com.epam.bookingservice.service.UserService;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.epam.bookingservice.util.JAXBXMLHandler.unmarshall;

@Service
public class BookingFacadeImpl implements BookingFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());
    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;
    private UserAccountService userAccountService;

    @Autowired
    public BookingFacadeImpl(UserService userService, TicketService ticketService, EventService eventService, UserAccountService userAccountService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.eventService = eventService;
        this.userAccountService = userAccountService;
    }

    @Override
    public Event getEventById(long eventId) {
        return eventService.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(LocalDate day, int pageSize, int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventService.updateEvent(event);
    }


    @Override
    public boolean deleteEvent(long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userService.deleteUser(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketService.createTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketService.deleteTicket(ticketId);
    }

    @Override
    @Transactional
    public void preloadTickets() throws JAXBException {
        List<Ticket> tickets = unmarshall().getTickets();
        tickets.stream().forEach((ticket) ->
                ticketService.createTicket(ticket.getUserId(),
                        ticket.getEventId(),
                        ticket.getPlace(),
                        ticket.getCategory()));

    }

    @Override
    public UserAccount getUserAccountById(Long userAccountId) {
        return userAccountService.getUserAccountById(userAccountId);
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return userAccountService.createUserAccount(userAccount);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        return userAccountService.updateUserAccount(userAccount);
    }

    @Override
    public boolean deleteUserAccount(Long userAccountId) {
        return userAccountService.deleteUserAccount(userAccountId);
    }

    @Override
    public UserAccount getUserAccountByUserId(Long userId) {
        return userAccountService.getUserAccountByUserId(userId);
    }
}
