package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/events")
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class.getName());

    private final BookingFacade bookingFacade;

    @Autowired
    public EventController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping()
    public String getEvents() {
        LOGGER.info("GET Events Page");
        return "/events/index";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("event", bookingFacade.getEventById(id));
        LOGGER.info("GET Event by id: {}", id);
        return "/events/view";
    }

    @GetMapping("/search")
    public String getEventsByTitle(Model model, @RequestParam(name = "title") String title,
                                 @RequestParam("pageSize") int pageSize,
                                 @RequestParam("pageNum") int pageNum) {
        model.addAttribute("events", bookingFacade.getEventsByTitle(title, pageSize, pageNum));
        LOGGER.info("GET Events by title: {}", title);
        return "/events/index";
    }

    @GetMapping("/search/date")
    public String getEventsForDay(Model model, @RequestParam(name = "day")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate day,
                                  @RequestParam("pageSize") int pageSize,
                                  @RequestParam("pageNum") int pageNum) {
        model.addAttribute("events", bookingFacade.getEventsForDay(day, pageSize, pageNum));
        LOGGER.info("GET Events by day: {}", day);
        return "/events/index";
    }

    @GetMapping("/add")
    public String addEvent() {
        return "events/add";
    }

    @PostMapping()
    public String createEvent(@RequestParam("title") String title,
                              @RequestParam("date")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              @RequestParam("ticketPrice") BigDecimal ticketPrice) {
        Event event = new Event();
        event.setTitle(title);
        event.setDate(date);
        event.setTicketPrice(ticketPrice);
        Event newEvent = bookingFacade.createEvent(event);
        LOGGER.info("CREATE Event: " + newEvent);
        return "redirect:/events/";
    }

    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", bookingFacade.getEventById(id));
        return "events/edit";
    }

    @PatchMapping("/{id}")
    public String updateEvent(@ModelAttribute("event") Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "events/edit";
        }
        LOGGER.info("UPDATE Event: {}", event);
        bookingFacade.updateEvent(event);
        return "redirect:/events/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        bookingFacade.deleteEvent(id);
        LOGGER.info("DELETE Event with id: {}", id);
        return "redirect:/events";
    }
}
