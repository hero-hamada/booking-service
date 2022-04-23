package com.epam.bookingservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bookingservice/v1")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class.getName());

    @GetMapping("/")
    public String getEvents() {
        LOGGER.info("GET / mapping");
        return "/index";
    }
}
