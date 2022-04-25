package com.epam.bookingservice.service.impl;

import com.epam.bookingservice.data.UserRepository;
import com.epam.bookingservice.model.User;
import com.epam.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserServiceImpl() {
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userRepository.findByNameContaining(name).stream().limit(pageSize * pageNum).toList();
    }

    @Override
    public User createUser(User user) {
        if (getUserByEmail(user.getEmail()) != null) {
            throw new IllegalStateException(String.format("Email %s already exists", user.getEmail()));
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (userRepository.findById(user.getId()) == null) {
            throw new IllegalStateException(String.format("User with id=%s does not exist", user.getId()));
        }
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId) == null) {
            throw new IllegalStateException(String.format("User with id=%s does not exist", userId));
        }
        userRepository.deleteById(userId);
        return !userRepository.existsById(userId);
    }
}
