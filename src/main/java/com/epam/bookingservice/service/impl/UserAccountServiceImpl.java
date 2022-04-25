package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.data.UserAccountRepository;
import com.epam.bookingservice.model.UserAccount;
import com.epam.bookingservice.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount getUserAccountById(Long userAccountId) {
        return userAccountRepository.findById(userAccountId).get();
    }

    @Override
    public UserAccount getUserAccountByUserId(Long userId) {
        return userAccountRepository.findFirstByUserId(userId);
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        if (getUserAccountById(userAccount.getId()) == null) {
            throw new IllegalStateException(String.format("UserAccount with id=%s does not exist", userAccount.getId()));
        }
        return userAccountRepository.save(userAccount);
    }

    @Override
    public boolean deleteUserAccount(Long userAccountId) {
        if (getUserAccountById(userAccountId) == null) {
            throw new IllegalStateException(String.format("UserAccount with id=%s does not exist", userAccountId));
        }
        userAccountRepository.deleteById(userAccountId);
        return !userAccountRepository.existsById(userAccountId);
    }
}
