package com.epam.bookingservice.dao;

import com.epam.bookingservice.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountDAO extends JpaRepository<UserAccount, Long> {
    UserAccount findFirstByUserId(Long userId);
}
