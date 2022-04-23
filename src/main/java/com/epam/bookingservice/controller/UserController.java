package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bookingservice/v1//users")
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
    public User getByEmail(@RequestParam(name = "email") String email) {
        LOGGER.info("GET User by email: {}", email);
        return bookingFacade.getUserByEmail(email);
    }

    @GetMapping(params = {"name", "pageSize", "pageNum"})
    public List<User> getUsersByName(Model model, @RequestParam(name = "name") String name,
                                     @RequestParam("pageSize") int pageSize,
                                     @RequestParam("pageNum") int pageNum) {
        LOGGER.info("GET Users by name: {}", name);
        return bookingFacade.getUsersByName(name, pageSize, pageNum);
    }

    @PostMapping()
    public User createUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.info("BindingResult has errors");
            return new User();
        }
        User newUser = bookingFacade.createUser(user);
        LOGGER.info("CREATE User: " + newUser);
        return newUser;
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", bookingFacade.getUserById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public User updateUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new User();
        }
        bookingFacade.updateUser(user);
        LOGGER.info("UPDATE User : {}", user);
        return bookingFacade.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteById(@PathVariable("id") Long id) {
        boolean isDeletedSuccess = bookingFacade.deleteUser(id);
        if (isDeletedSuccess) {
            LOGGER.info("DELETE User with id: {}", id);
        }
        return isDeletedSuccess;
    }
}
