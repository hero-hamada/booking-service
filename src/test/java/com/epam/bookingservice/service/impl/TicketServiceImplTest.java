package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.EventDAO;
import com.epam.bookingservice.dao.TicketDAO;
import com.epam.bookingservice.dao.UserAccountDAO;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.model.UserAccount;
import com.epam.bookingservice.service.EventService;
import com.epam.bookingservice.service.TicketService;
import com.epam.bookingservice.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.bookingservice.model.Ticket.Category.STANDARD;
import static java.util.Calendar.MAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketDAO ticketDAO;
    @Mock
    private UserAccountDAO userAccountDAO;
    @Mock
    private EventDAO eventDAO;
    @Mock
    private UserAccountService userAccountService;
    @Mock
    private EventService eventService;
    private TicketService underTest;

    @BeforeEach
    public void setUp() {
        eventService = new EventServiceImpl(eventDAO);
        userAccountService = new UserAccountServiceImpl(userAccountDAO);
        underTest = new TicketServiceImpl(ticketDAO, userAccountService, eventService);
    }

    @Test
    void canCreateTicket() {
        // given
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        userAccount.setMoney(BigDecimal.valueOf(1000));

        User user = new User();
        user.setId(1L);
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        user.setAccount(userAccount);
        userAccount.setUser(user);

        Event event = new Event();
        event.setId(1L);
        event.setTitle("Event");
        event.setDate(LocalDate.of(2022, MAY, 10));
        event.setTicketPrice(BigDecimal.valueOf(100));

        final int place = 48;
        final Ticket.Category category = STANDARD;
        Ticket expectedTicket = new Ticket();
        expectedTicket.setUserId(user.getId());
        expectedTicket.setEventId(event.getId());
        expectedTicket.setCategory(category);
        expectedTicket.setPlace(place);

        given(eventDAO.findById(event.getId())).willReturn(Optional.of(event));
        given(userAccountDAO.findFirstByUserId(user.getId())).willReturn(userAccount);
        given(userAccountDAO.findById(userAccount.getId())).willReturn(Optional.of(userAccount));

        // when
        underTest.createTicket(user.getId(), event.getId(), place, category);

        // then
        userAccount.setMoney(userAccount.getMoney().subtract(event.getTicketPrice()));
        verify(userAccountDAO).save(userAccount);
        verify(ticketDAO).save(eq(expectedTicket));
    }

    @Test
    void createTicketThrowIfNotEnoughMoneyToBook() {
        // given
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1L);
        userAccount.setMoney(BigDecimal.valueOf(1000));

        User user = new User();
        user.setId(1L);
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        user.setAccount(userAccount);
        userAccount.setUser(user);

        Event event = new Event();
        event.setId(1L);
        event.setTitle("Event");
        event.setDate(LocalDate.of(2022, MAY, 10));
        event.setTicketPrice(BigDecimal.valueOf(100000));

        final int place = 48;
        final Ticket.Category category = STANDARD;
        Ticket expectedTicket = new Ticket();
        expectedTicket.setUserId(user.getId());
        expectedTicket.setEventId(event.getId());
        expectedTicket.setCategory(category);
        expectedTicket.setPlace(place);

        given(eventDAO.findById(event.getId())).willReturn(Optional.of(event));
        given(userAccountDAO.findFirstByUserId(user.getId())).willReturn(userAccount);

        // when then
        assertThatThrownBy(() -> underTest.createTicket(user.getId(), event.getId(), place, category))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(
                        String.format("Not enough money to buy ticket with price: %s", event.getTicketPrice())
                );
        verify(userAccountDAO, never()).save(userAccount);
        verify(ticketDAO, never()).save(any());
    }

    @Test
    void canGetBookedTicketsByUser() {
        // given
        User user = new User();
        user.setId(1L);
        user.setName("Rem");
        user.setEmail("rem@gmail.com");
        final int pageSize = 3;
        final int pageNum = 2;

        // when
        List<Ticket> actualBookedTickets = underTest.getBookedTickets(user, pageSize, pageNum);

        // then
        verify(ticketDAO).findAllByUserId(eq(user.getId()));
    }

    @Test
    void canGetBookedTicketsByEvent() {
        // given
        Event event = new Event();
        event.setTitle("Event");
        event.setDate(LocalDate.of(2050, MAY, 10));
        event.setTicketPrice(BigDecimal.valueOf(100));
        final int pageSize = 3;
        final int pageNum = 2;
        // when
        List<Ticket> actualBookedTickets = underTest.getBookedTickets(event, pageSize, pageNum);
        // then
        verify(ticketDAO).findAllByEventId(event.getId());
    }

    @Test
    void canDeleteTicket() {
        // given
        final Long id = 1L;
        // when
        underTest.deleteTicket(id);
        // then
        verify(ticketDAO).deleteById(id);
    }
}
