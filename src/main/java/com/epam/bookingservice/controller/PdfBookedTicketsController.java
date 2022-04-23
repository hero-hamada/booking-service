package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.util.BookedTicketsPdfBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bookingservice/v1/tickets")
public class PdfBookedTicketsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());
    private final BookingFacade bookingFacade;

    @Autowired
    public PdfBookedTicketsController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/accept=application/pdf")
    public void getBookedTickets(@RequestParam("userId") Long userId,
                                   @RequestParam("pageSize") int pageSize,
                                   @RequestParam("pageNum") int pageNum) {
        User user = bookingFacade.getUserById(userId);
        BookedTicketsPdfBuilder.build(bookingFacade.getBookedTickets(user, pageSize, pageNum));
        LOGGER.info("GET PDF Booked tickets by User: {}", user);
    }
}
