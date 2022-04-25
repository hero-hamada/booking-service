package com.epam.bookingservice.data;

import com.epam.bookingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("Select u from User u where u.name like %:name%")
    List<User> findByNameContaining(@Param("name") String name);
}
