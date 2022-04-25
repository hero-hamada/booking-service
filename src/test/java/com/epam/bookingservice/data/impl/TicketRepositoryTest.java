package com.epam.bookingservice.data.impl;

import com.epam.bookingservice.data.EventRepository;
import com.epam.bookingservice.data.TicketRepository;
import com.epam.bookingservice.data.UserAccountRepository;
import com.epam.bookingservice.data.UserRepository;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.epam.bookingservice.model.Ticket.Category.STANDARD;
import static java.util.Calendar.MAY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class TicketRepositoryTest {

    @Autowired
    private TicketRepository underTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventDAO;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private List<Ticket> expectedTickets;
    private User savedUser;
    private Event savedEvent;

    @BeforeEach
    public void setUp() {
        underTest.deleteAll();
        userRepository.deleteAll();
        eventDAO.deleteAll();

        final User user = new User();
        user.setName("User");
        user.setEmail("user@mail.com");
        UserAccount userAccount = new UserAccount();
        userAccount.setMoney(BigDecimal.valueOf(5000));
        user.setAccount(userAccount);
        savedUser = userRepository.save(user);
        final Event event = new Event();
        event.setTitle("Event");
        event.setDate(LocalDate.of(2022, MAY, 30));
        event.setTicketPrice(BigDecimal.valueOf(1000));
        savedEvent = eventDAO.save(event);
        userAccountRepository.save(userAccount);

        expectedTickets = underTest.saveAll(List.of(
                new Ticket(savedEvent.getId(), savedUser.getId(), 1, STANDARD),
                new Ticket(savedEvent.getId(), savedUser.getId(), 5, STANDARD)
        ));
    }

    @AfterEach
    void tearDown() {
//        underTest.deleteAll();
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