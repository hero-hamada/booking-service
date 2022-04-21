package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.dao.UserDAO;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserById(Long userId) {
        return userDAO.findById(userId).get();
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userDAO.findByNameContaining(name).stream().limit(pageSize * pageNum).toList();
    }

    @Override
    public User createUser(User user) {
        if (getUserByEmail(user.getEmail()) != null) {
            throw new IllegalStateException(String.format("Email %s already exists", user.getEmail()));
        }
        return userDAO.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (userDAO.findById(user.getId()) == null) {
            throw new IllegalStateException(String.format("User with id=%s does not exist", user.getId()));
        }
        return userDAO.save(user);
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userDAO.findById(userId) == null) {
            throw new IllegalStateException(String.format("User with id=%s does not exist", userId));
        }
        userDAO.deleteById(userId);
        return !userDAO.existsById(userId);
    }
}
