package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class.getName());

    private final BookingFacade bookingFacade;

    @Autowired
    public TicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping()
    public String getTickets() {
        LOGGER.info("GET Tickets Page");
        return "/tickets/index";
    }

    @GetMapping("/add")
    public String addTicket(Model model) {
        Ticket.Category[] categories = Ticket.Category.values();
        model.addAttribute("categories", categories);
        return "tickets/add";
    }

    @PostMapping()
    public String createTicket(@RequestParam("userId") Long userId,
                               @RequestParam("eventId") Long eventId,
                               @RequestParam("place") Integer place,
                               @RequestParam("category") Ticket.Category category) {
        Ticket newTicket = bookingFacade.bookTicket(userId, eventId, place, category);
        LOGGER.info("CREATE Ticket: " + newTicket);
        return "redirect:/tickets/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        boolean isDeletedSuccess = bookingFacade.cancelTicket(id);
        if (isDeletedSuccess) {
            LOGGER.info("DELETE Ticket with id: {}", id);
        }
        return "redirect:/tickets";
    }

    @GetMapping("/byUser/{userId}/params")
    public String getBookedTicketsByUserId(Model model, @PathVariable("userId") Long userId,
                                   @RequestParam("pageSize") int pageSize,
                                   @RequestParam("pageNum") int pageNum) {
        model.addAttribute("tickets", bookingFacade.getBookedTickets(
                bookingFacade.getUserById(userId),
                pageSize,
                pageNum));
        LOGGER.info("GET Tickets by user: {}", bookingFacade.getUserById(userId));
        return "/tickets/index";
    }

    @GetMapping("/byEvent/{eventId}/params")
    public String getBookedTicketsByEventId(Model model, @PathVariable("eventId") Long eventId,
                                   @RequestParam("pageSize") int pageSize,
                                   @RequestParam("pageNum") int pageNum) {
        model.addAttribute("tickets", bookingFacade.getBookedTickets(
                bookingFacade.getEventById(eventId),
                pageSize,
                pageNum));
        LOGGER.info("GET Tickets by eventID: {}", bookingFacade.getUserById(eventId));
        return "/tickets/index";
    }
}
