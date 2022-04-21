package com.epam.bookingservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class.getName());

    @GetMapping("/")
    public String getEvents() {
        LOGGER.info("GET / mapping");
        return "/index";
    }
}