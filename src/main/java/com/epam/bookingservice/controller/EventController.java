package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("bookingservice/v1/events")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class.getName());

    private final BookingFacade bookingFacade;

    @Autowired
    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/{id}")
    public Event  getById(@PathVariable("id") Long id) {
        LOGGER.info("GET Event by id: {}", id);
        return bookingFacade.getEventById(id);
    }

    @GetMapping(params = {"title", "pageSize", "pageNum"})
    public List<Event> getEventsByTitle(@RequestParam(name = "title") String title,
                                        @RequestParam("pageSize") int pageSize,
                                        @RequestParam("pageNum") int pageNum) {
        LOGGER.info("GET Events by title: {}", title);
        return bookingFacade.getEventsByTitle(title, pageSize, pageNum);
    }

    @GetMapping(params = {"day", "pageSize", "pageNum"})
    public List<Event> getEventsForDay(@RequestParam(name = "day")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate day,
                                       @RequestParam("pageSize") int pageSize,
                                       @RequestParam("pageNum") int pageNum) {
        LOGGER.info("GET Events by day: {}", day);
        return bookingFacade.getEventsForDay(day, pageSize, pageNum);
    }

    @PostMapping()
    public Event createEvent(@RequestParam("title") String title,
                             @RequestParam("date")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @RequestParam("ticketPrice") BigDecimal ticketPrice) {
        Event event = new Event();
        event.setTitle(title);
        event.setDate(date);
        event.setTicketPrice(ticketPrice);
        Event newEvent = bookingFacade.createEvent(event);
        LOGGER.info("CREATE Event: " + newEvent);
        return newEvent;
    }

    @PutMapping("/{id}")
    public Event updateEvent(@ModelAttribute("event") Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.error("BindingResult has errors");
            return new Event();
        }
        LOGGER.info("UPDATE Event: {}", event);
        return bookingFacade.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable("id") Long id) {

        LOGGER.info("DELETE Event with id: {}", id);
        return bookingFacade.deleteEvent(id);
    }
}
