package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bookingservice/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());

    private final BookingFacade bookingFacade;

    @Autowired
    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        User userById = bookingFacade.getUserById(id);
        LOGGER.info("GET User by id: {}");
        return userById;
    }

    @GetMapping(params = {"email"})
    public User getByEmail(@RequestParam("email") String email) {
        LOGGER.info("GET User by email: {}", email);
        return bookingFacade.getUserByEmail(email);
    }

    @GetMapping(params = {"name", "pageSize", "pageNum"})
    public List<User> getUsersByName(@RequestParam("name") String name,
                                     @RequestParam("pageSize") int pageSize,
                                     @RequestParam("pageNum") int pageNum) {
        LOGGER.info("GET Users by name: {}", name);
        return bookingFacade.getUsersByName(name, pageSize, pageNum);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        User newUser = bookingFacade.createUser(user);
        LOGGER.info("CREATE User: " + newUser);
        return newUser;
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user) {
        LOGGER.info("UPDATE User : {}", user);
        return bookingFacade.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        bookingFacade.deleteUser(id);
        LOGGER.info("DELETE User with id: {}", id);
    }
}
