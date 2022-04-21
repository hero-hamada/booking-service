package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.UserAccountDAO;
import com.epam.bookingservice.model.UserAccount;
import com.epam.bookingservice.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDAO userAccountDAO;

    @Autowired
    public UserAccountServiceImpl(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public UserAccount getUserAccountById(Long userAccountId) {
        return userAccountDAO.findById(userAccountId).get();
    }

    @Override
    public UserAccount getUserAccountByUserId(Long userId) {
        return userAccountDAO.findFirstByUserId(userId);
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return userAccountDAO.save(userAccount);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        if (getUserAccountById(userAccount.getId()) == null) {
            throw new IllegalStateException(String.format("UserAccount with id=%s does not exist", userAccount.getId()));
        }
        return userAccountDAO.save(userAccount);
    }

    @Override
    public boolean deleteUserAccount(Long userAccountId) {
        if (getUserAccountById(userAccountId) == null) {
            throw new IllegalStateException(String.format("UserAccount with id=%s does not exist", userAccountId));
        }
        userAccountDAO.deleteById(userAccountId);
        return !userAccountDAO.existsById(userAccountId);
    }
}
