package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.EventDAO;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private EventDAO eventDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventDAO.findById(eventId).get();
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> eventsByTitle = eventDAO.findByTitleContaining(title);
        return eventsByTitle.stream()
                .limit(pageSize * pageNum)
                .toList();
    }

    @Override
    public List<Event> getEventsForDay(LocalDate day, int pageSize, int pageNum) {
        List<Event> eventsForDay = eventDAO.findByDay(day);
        return eventsForDay.stream()
                .limit(pageSize * pageNum)
                .toList();
    }

    @Override
    public Event createEvent(Event event) {
        if (isOutdatedEvent(event.getDate())) {
            throw new IllegalStateException(String.format("Event date %s incorrect", event.getDate()));
        }
        return eventDAO.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        if (eventDAO.findById(event.getId()) == null) {
            throw new IllegalStateException(String.format("Event with id=%s does not exist", event.getId()));
        }
        return eventDAO.save(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        eventDAO.deleteById(eventId);
        return !eventDAO.existsById(eventId);
    }

    public boolean isOutdatedEvent(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}
