package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bookingservice/v1/tickets")
public class TicketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class.getName());

    private final BookingFacade bookingFacade;

    @Autowired
    public TicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping(params = {"userId", "pageSize", "pageNum"})
    public List<Ticket> getBookedTicketsByUserId(@RequestParam("userId") Long userId,
                                                 @RequestParam("pageSize") int pageSize,
                                                 @RequestParam("pageNum") int pageNum) {
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(
                bookingFacade.getUserById(userId),
                pageSize,
                pageNum);
        LOGGER.info("GET Tickets by user: {}", bookingFacade.getUserById(userId));
        return bookedTickets;
    }

    @GetMapping(params = {"eventId", "pageSize", "pageNum"})
    public List<Ticket> getBookedTicketsByEventId(@RequestParam("eventId") Long eventId,
                                                  @RequestParam("pageSize") int pageSize,
                                                  @RequestParam("pageNum") int pageNum) {
        List<Ticket> bookedTickets = bookingFacade.getBookedTickets(
                bookingFacade.getEventById(eventId),
                pageSize,
                pageNum);
        LOGGER.info("GET Tickets by eventID: {}", bookingFacade.getUserById(eventId));
        return bookedTickets;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket createTicket(@RequestBody Ticket ticket) {
        Ticket newTicket = bookingFacade.bookTicket(ticket.getUserId(),
                ticket.getEventId(),
                ticket.getPlace(),
                ticket.getCategory());
        LOGGER.info("CREATE Ticket: " + newTicket);
        return newTicket;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        boolean isDeletedSuccess = bookingFacade.cancelTicket(id);
        LOGGER.info("DELETE Ticket with id: {}", id);
    }
}
