package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.data.EventRepository;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).get();
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> eventsByTitle = eventRepository.findByTitleContaining(title);
        return eventsByTitle.stream()
                .limit(pageSize * pageNum)
                .toList();
    }

    @Override
    public List<Event> getEventsForDay(LocalDate day, int pageSize, int pageNum) {
        List<Event> eventsForDay = eventRepository.findByDay(day);
        return eventsForDay.stream()
                .limit(pageSize * pageNum)
                .toList();
    }

    @Override
    public Event createEvent(Event event) {
        if (isOutdatedEvent(event.getDate())) {
            throw new IllegalStateException(String.format("Event date %s incorrect", event.getDate()));
        }
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        if (eventRepository.findById(event.getId()) == null) {
            throw new IllegalStateException(String.format("Event with id=%s does not exist", event.getId()));
        }
        return eventRepository.save(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        eventRepository.deleteById(eventId);
        return !eventRepository.existsById(eventId);
    }

    public boolean isOutdatedEvent(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}
