package com.epam.bookingservice.dao;

import com.epam.bookingservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventDAO extends JpaRepository<Event, Long> {
    @Query("Select e from Event e where e.title like %:title%")
    List<Event> findByTitleContaining(@Param("title") String title);
    @Query("Select e from Event e where e.date=:day")
    List<Event> findByDay(@Param("day") LocalDate day);
}
