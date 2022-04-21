package com.epam.bookingservice.dao;

import com.epam.bookingservice.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountDAO extends CrudRepository<UserAccount, Long> {
    UserAccount findFirstByUserId(Long userId);
}
