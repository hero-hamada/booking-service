package com.epam.bookingservice.service;

import com.epam.bookingservice.model.UserAccount;

import java.util.List;

public interface UserAccountService {

    UserAccount getUserAccountById(Long userAccountId);

    UserAccount createUserAccount(UserAccount userAccount);

    UserAccount updateUserAccount(UserAccount userAccount);

    boolean deleteUserAccount(Long userAccountId);

    UserAccount getUserAccountByUserId(Long userId);
}
