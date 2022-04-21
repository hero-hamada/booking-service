package com.epam.bookingservice.controller;

import com.epam.bookingservice.facade.BookingFacade;
import com.epam.bookingservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());

    private final BookingFacade bookingFacade;

    @Autowired
    public UserController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping()
    public String getUsers() {
        LOGGER.info("GET Users");
        return "/users/index";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable("id") Long id) {
        User userById = bookingFacade.getUserById(id);
        model.addAttribute("user", userById);
        LOGGER.info("GET User by id: {}");
        return "/users/view";
    }

    @GetMapping("/search/items")
    public String getByEmail(Model model, @RequestParam(name = "email") String email) {
        model.addAttribute("user", bookingFacade.getUserByEmail(email));
        LOGGER.info("GET User by email: {}", email);
        return "/users/view";
    }

    @GetMapping("/search")
    public String getUsersByName(Model model, @RequestParam(name = "name") String name,
                                      @RequestParam("pageSize") int pageSize,
                                      @RequestParam("pageNum") int pageNum) {
        model.addAttribute("users", bookingFacade.getUsersByName(name, pageSize, pageNum));
        LOGGER.info("GET Users by name: {}", name);
        return "/users/index";
    }

    @GetMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
    return "users/add";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "users/add";
        }
        User newUser = bookingFacade.createUser(user);
        LOGGER.info("CREATE User: " + newUser);
        return "redirect:/users/";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", bookingFacade.getUserById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        bookingFacade.updateUser(user);
        LOGGER.info("UPDATE User : {}", user);
        return "redirect:/users/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        boolean isDeletedSuccess = bookingFacade.deleteUser(id);
        if (isDeletedSuccess) {
            LOGGER.info("DELETE User with id: {}", id);
        }
        return "redirect:/users";
    }
}
