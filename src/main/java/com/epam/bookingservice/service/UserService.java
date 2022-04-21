package com.epam.bookingservice.service;

import com.epam.bookingservice.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    User getUserByEmail(String email);

    List<User> getUsersByName(String name, int pageSize, int pageNum);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUser(Long userId);
}
