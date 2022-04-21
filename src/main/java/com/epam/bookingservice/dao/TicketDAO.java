package com.epam.bookingservice.dao;

import com.epam.bookingservice.model.Ticket;
import com.epam.bookingservice.model.UserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketDAO extends CrudRepository<Ticket, Long> {
    List<Ticket> findAllByUserId(Long id);
    List<Ticket> findAllByEventId(Long id);
}
