package com.epam.bookingservice.facade;

import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.service.EventService;
import com.epam.bookingservice.service.TicketService;
import com.epam.bookingservice.service.UserAccountService;
import com.epam.bookingservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.epam.bookingservice.model.Ticket.Category.STANDARD;
import static java.time.Month.AUGUST;
import static java.util.Calendar.MAY;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookingFacadeImplTest {

    @Mock
    private EventService eventService;
    @Mock
    private UserService userService;
    @Mock
    private TicketService ticketService;
    @Mock
    private UserAccountService userAccountService;
    private BookingFacade underTest;

    @BeforeEach
    public void setUp() {
        underTest = new BookingFacadeImpl(userService, ticketService, eventService, userAccountService);
    }

    @Test
    void canGetEventById() {
        // given
        final Long eventId = 1L;
        // when
        underTest.getEventById(eventId);
        // then
        verify(eventService).getEventById(eq(eventId));
    }

    @Test
    void canGetEventsByTitle() {
        // given
        final String title = "Event";
        final int pageSize = 1;
        final int pageNum = 1;
        // when
        List<Event> eventsByTitle = underTest.getEventsByTitle(title, pageSize, pageNum);
        // then
        verify(eventService).getEventsByTitle(eq(title), eq(pageSize), eq(pageNum));
    }

    @Test
    void canGetEventsForDay() {
        // given
        final LocalDate day = LocalDate.of(2022, AUGUST, 23);
        final int pageSize = 1;
        final int pageNum = 1;
        // when
        underTest.getEventsForDay(day, pageSize, pageNum);
        // then
        verify(eventService).getEventsForDay(eq(day), eq(pageSize), eq(pageNum));
    }

    @Test
    void canCreateEvent() {
        // given
        Event expectedEvent = new Event();
        expectedEvent.setTitle("Event");
        expectedEvent.setDate(LocalDate.of(2022, MAY, 10));
        expectedEvent.setTicketPrice(BigDecimal.valueOf(100));
        // when
        underTest.createEvent(expectedEvent);
        // then
        verify(eventService).createEvent(eq(expectedEvent));
    }

    @Test
    void canUpdateEvent() {
        // given
        Event expectedEvent = new Event();
        expectedEvent.setTitle("Event");
        expectedEvent.setDate(LocalDate.of(2022, MAY, 10));
        expectedEvent.setTicketPrice(BigDecimal.valueOf(100));
        // when
        underTest.updateEvent(expectedEvent);
        // then
        verify(eventService).updateEvent(eq(expectedEvent));
    }

    @Test
    void canDeleteEvent() {
        // given
        final Long eventId = 1L;
        // when
        underTest.deleteEvent(eventId);
        // then
        verify(eventService).deleteEvent(eq(eventId));
    }

    @Test
    void canGetUserById() {
        // given
        final Long userId = 1L;
        // when
        underTest.getUserById(userId);
        // then
        verify(userService).getUserById(eq(userId));
    }

    @Test
    void canGetUserByEmail() {
        // given
        final String email = "s@gmail.com";
        // when
        underTest.getUserByEmail(email);
        // then
        verify(userService).getUserByEmail(eq(email));
    }

    @Test
    void canGetUsersByName() {
        // given
        final String name = "a";
        final int pageSize = 4;
        final int pageNum = 6;
        // when
        underTest.getUsersByName(name, pageSize, pageNum);
        // then
        verify(userService).getUsersByName(eq(name), eq(pageSize), eq(pageNum));
    }

    @Test
    void canCreateUser() {
        // given
        User expectedUser = new User();
        expectedUser.setName("Rem");
        expectedUser.setEmail("rem@gmail.com");
        // when
        underTest.createUser(expectedUser);
        // then
        verify(userService).createUser(expectedUser);
    }

    @Test
    void canUpdateUser() {
        // given
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("Rem");
        expectedUser.setEmail("rem@gmail.com");
        // when
        underTest.updateUser(expectedUser);
        // then
        verify(userService).updateUser(expectedUser);
    }

    @Test
    void canDeleteUser() {
        // given
        final Long userId = 1L;
        // when
        underTest.deleteUser(userId);
        // then
        verify(userService).deleteUser(userId);
    }

    @Test
    void canBookTicket() {
        // given
        final long userId = 1L;
        final long eventId = 2L;
        final int place = 48;
        final Ticket.Category category = STANDARD;

        // when
        underTest.bookTicket(userId, eventId, place, category);

        // then
        verify(ticketService).createTicket(eq(userId), eq(eventId), eq(place), eq(category));
    }

    @Test
    void canGetBookedTicketsByEvent() {
        // given
        User expectedUser = new User();
        expectedUser.setName("Rem");
        expectedUser.setEmail("rem@gmail.com");
        final int pageSize = 3;
        final int pageNum = 2;
        // when
        underTest.getBookedTickets(expectedUser, pageSize, pageNum);
        // then
        verify(ticketService).getBookedTickets(eq(expectedUser), eq(pageSize), eq(pageNum));
    }

    @Test
    void canGetBookedTicketsByUser() {
        // given
        Event event = new Event();
        event.setTitle("Event");
        event.setDate(LocalDate.of(2022, MAY, 10));
        event.setTicketPrice(BigDecimal.valueOf(100));
        final int pageSize = 3;
        final int pageNum = 2;
        // when
        underTest.getBookedTickets(event, pageSize, pageNum);
        // then
        verify(ticketService).getBookedTickets(eq(event), eq(pageSize), eq(pageNum));
    }

    @Test
    void canCancelTicket() {
        // given
        final Long id = 1L;
        // when
        underTest.cancelTicket(id);
        // then
        verify(ticketService).deleteTicket(id);
    }


}