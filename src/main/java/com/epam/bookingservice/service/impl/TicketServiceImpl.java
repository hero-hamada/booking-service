package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.TicketDAO;
import com.epam.bookingservice.model.Event;
import com.epam.bookingservice.model.Ticket;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.model.UserAccount;
import com.epam.bookingservice.service.EventService;
import com.epam.bookingservice.service.TicketService;
import com.epam.bookingservice.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketDAO ticketDAO;
    private UserAccountService userAccountService;
    private EventService eventService;

    @Autowired
    public TicketServiceImpl(TicketDAO ticketDAO,
                             UserAccountService userAccountService,
                             EventService eventService) {
        this.ticketDAO = ticketDAO;
        this.userAccountService = userAccountService;
        this.eventService = eventService;
    }

    @Override
    public Ticket createTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket ticket = new Ticket();
        ticket.setEventId(eventId);
        ticket.setUserId(userId);
        ticket.setCategory(category);
        ticket.setPlace(place);

        withdrawMoneyFromUserAccount(eventId, userId);

        return ticketDAO.save(ticket);
    }

    private void withdrawMoneyFromUserAccount(long eventId, long userId) {
        BigDecimal ticketPrice = eventService.getEventById(eventId).getTicketPrice();
        UserAccount account = userAccountService.getUserAccountByUserId(userId);
        BigDecimal moneyInUserAccount = account.getMoney();

        checkAccountBalance(ticketPrice, moneyInUserAccount);

        BigDecimal moneyLeftAfterBuying = moneyInUserAccount.subtract(ticketPrice);
        account.setMoney(moneyLeftAfterBuying);
        userAccountService.updateUserAccount(account);
    }

    private void checkAccountBalance(BigDecimal ticketPrice, BigDecimal moneyInUserAccount) {
        if (moneyInUserAccount.compareTo(ticketPrice) < 0) {
            throw new IllegalStateException(String.format("Not enough money to buy ticket with price: %s", ticketPrice));
        }
    }


    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> allTicketsByUserId = ticketDAO.findAllByUserId(user.getId());
        return allTicketsByUserId.stream()
                .limit(pageSize * pageNum)
                .toList();
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> allTicketsByEventId = ticketDAO.findAllByEventId(event.getId());
        return allTicketsByEventId.stream()
                .limit(pageSize * pageNum)
                .toList();
    }

    @Override
    public boolean deleteTicket(long ticketId) {
        ticketDAO.deleteById(ticketId);
        return !ticketDAO.existsById(ticketId);
    }
}

