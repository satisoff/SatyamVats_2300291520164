package com.kiet.eventmanagementportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling home page requests.
 */
@Controller
public class HomeController {

    /**
     * Redirect root URL to events list
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/events";
    }
}