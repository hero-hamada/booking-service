package com.epam.bookingservice.data;

import com.epam.bookingservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByUserId(Long id);
    List<Ticket> findAllByEventId(Long id);
}
