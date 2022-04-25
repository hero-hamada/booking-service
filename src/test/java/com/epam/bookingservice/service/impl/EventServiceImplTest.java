package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.data.EventRepository;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.Month.AUGUST;
import static java.util.Calendar.MAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;
    private EventService underTest;

    @BeforeEach
    public void setUp() {
        underTest = new EventServiceImpl(eventRepository);
    }

    @Test
    void getEventById() {
        // given
        final Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));
        // when
        underTest.getEventById(eventId);
        // then
        verify(eventRepository).findById(eq(eventId));
    }

    @Test
    void getEventsByTitle() {
        // given
        final String title = "Event";
        final int pageSize = 2;
        final int pageNum = 3;
        // when
        List<Event> eventsByTitle = underTest.getEventsByTitle(title, pageSize, pageNum);
        // then
        verify(eventRepository).findByTitleContaining(eq(title));
    }

    @Test
    void getEventsForDay() {
        // given
        final LocalDate day = LocalDate.of(2022, AUGUST, 23);
        final int pageSize = 1;
        final int pageNum = 1;
        // when
        List<Event> eventsForDay = underTest.getEventsForDay(day, pageSize, pageNum);
        // then
        verify(eventRepository).findByDay(eq(day));
    }

    @Test
    void createEvent() {
        // given
        Event expectedEvent = new Event();
        expectedEvent.setTitle("Event");
        expectedEvent.setDate(LocalDate.of(2050, MAY, 10));
        expectedEvent.setTicketPrice(BigDecimal.valueOf(100));
        // when
        underTest.createEvent(expectedEvent);
        // then
        verify(eventRepository).save(eq(expectedEvent));
    }

    @Test
    void updateEvent() {
        // given
        Event expectedEvent = new Event();
        expectedEvent.setId(1L);
        expectedEvent.setTitle("Event");
        expectedEvent.setDate(LocalDate.of(2050, MAY, 20));
        expectedEvent.setTicketPrice(BigDecimal.valueOf(100));

        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(expectedEvent));
        // when
        underTest.updateEvent(expectedEvent);
        // then
        verify(eventRepository).save(eq(expectedEvent));
    }

    @Test
    void updateEventThrowWhenIdNotExists() {
        // given
        Event expectedEvent = new Event();
        expectedEvent.setTitle("Event");
        expectedEvent.setDate(LocalDate.of(2050, MAY, 10));
        expectedEvent.setTicketPrice(BigDecimal.valueOf(100));

        when(eventRepository.findById(expectedEvent.getId())).thenReturn(null);
        // when
        assertThatThrownBy(() -> underTest.updateEvent(expectedEvent))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("Event with id=%s does not exist", expectedEvent.getId()));
        // then
        verify(eventRepository, never()).save(eq(expectedEvent));
    }

    @Test
    void deleteEvent() {
        // given
        final Long eventId = 1L;
        // when
        underTest.deleteEvent(eventId);
        // then
        verify(eventRepository).deleteById(eq(eventId));
    }
}