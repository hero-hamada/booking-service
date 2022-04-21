package com.epam.bookingservice.service;

import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
import com.epam.bookingservice.model.User;

import java.util.List;

public interface TicketService {

    Ticket createTicket(long userId, long eventId, int place, Ticket.Category category);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    boolean deleteTicket(long ticketId);
}
