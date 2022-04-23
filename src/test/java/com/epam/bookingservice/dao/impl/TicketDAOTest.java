package com.epam.bookingservice.dao.impl;

import com.epam.bookingservice.dao.EventDAO;
import com.epam.bookingservice.dao.TicketDAO;
import com.epam.bookingservice.dao.UserDAO;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.model.UserAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.epam.bookingservice.model.Ticket.Category.STANDARD;
import static java.util.Calendar.MAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SpringConfig.class})
//@WebAppConfiguration()
@ExtendWith(MockitoExtension.class)
class TicketDAOTest {

    @Autowired
    private TicketDAO underTest;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EventDAO eventDAO;
    private List<Ticket> expectedTickets;
    private User savedUser;
    private Event savedEvent;

    @BeforeEach
    public void setUp() {
        underTest.deleteAll();
        userDAO.deleteAll();
        eventDAO.deleteAll();

        final User user = new User();
        user.setName("User");
        user.setEmail("user@mail.com");
        UserAccount userAccount = new UserAccount();
        userAccount.setMoney(BigDecimal.valueOf(5000));
        user.setAccount(userAccount);
        savedUser = userDAO.save(user);

        final Event event = new Event();
        event.setTitle("Event");
        event.setDate(LocalDate.of(2022, MAY, 30));
        event.setTicketPrice(BigDecimal.valueOf(1000));
        savedEvent = eventDAO.save(event);

        expectedTickets = (List<Ticket>) underTest.saveAll(List.of(
                new Ticket(savedEvent.getId(), savedUser.getId(), 1, STANDARD),
                new Ticket(savedEvent.getId(), savedUser.getId(), 5, STANDARD)
        ));
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void canFindAllTicketsByUserId() {
        //given
        final Long userId = savedUser.getId();
        //when
        List<Ticket> actualTicketsByUserId = underTest.findAllByUserId(userId);
        //then
        assertThat(actualTicketsByUserId).isEqualTo(expectedTickets);
    }

    @Test
    void canFindAllTicketsByEventId() {
        //given
        final Long eventId = savedEvent.getId();
        //when
        List<Ticket> actualTicketsByUserId = underTest.findAllByEventId(eventId);
        //then
        assertThat(actualTicketsByUserId).isEqualTo(expectedTickets);
    }
}