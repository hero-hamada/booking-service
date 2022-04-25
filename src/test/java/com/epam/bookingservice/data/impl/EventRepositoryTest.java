package com.epam.bookingservice.data.impl;

import com.epam.bookingservice.data.EventRepository;
import com.epam.bookingservice.model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.Calendar.MAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class EventRepositoryTest {

    @Autowired
    private EventRepository underTest;
    private List<Event> expectedEvents;

    @BeforeEach
    public void setUp() {
        underTest.deleteAll();
        Event event = new Event();
        event.setTitle("Event");
        event.setDate(LocalDate.of(2022, MAY, 10));
        event.setTicketPrice(BigDecimal.valueOf(100));
        Event event1 = new Event();
        event1.setTitle("EventBest");
        event1.setDate(LocalDate.of(2022, MAY, 10));
        event1.setTicketPrice(BigDecimal.valueOf(100));
        expectedEvents = List.of(event, event1);
        underTest.saveAll(expectedEvents);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void canFindEventsByTitle() {
        // given
        final String title = "Event";

        // when
        List<Event> actualEvents = underTest.findByTitleContaining(title);

        // then
        assertThat(actualEvents).isEqualTo(expectedEvents);
    }

    @Test
    void canFindEventsForDay() {
        // given
        final LocalDate day = LocalDate.of(2022, MAY, 10);

        // when
        List<Event> actualEvents = underTest.findByDay(day);

        // then
        assertThat(actualEvents).isEqualTo(expectedEvents);
    }
}