package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public Event getById(@PathVariable("id") Long id) {
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
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event event) {
        Event newEvent = bookingFacade.createEvent(event);
        LOGGER.info("CREATE Event: " + newEvent);
        return newEvent;
    }

    @PutMapping("/{id}")
    public Event updateEvent(@RequestBody Event event) {
        LOGGER.info("UPDATE Event: {}", event);
        return bookingFacade.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        bookingFacade.deleteEvent(id);
        LOGGER.info("DELETE Event with id: {}", id);
    }
}
