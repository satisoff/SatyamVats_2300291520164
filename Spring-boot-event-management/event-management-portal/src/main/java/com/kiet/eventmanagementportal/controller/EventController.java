package com.kiet.eventmanagementportal.controller;

import com.kiet.eventmanagementportal.model.Event;
import com.kiet.eventmanagementportal.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling Event-related HTTP requests.
 */
@Controller
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Get all events - Web view
     */
    @GetMapping("/events")
    public String getAllEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "events-list";
    }

    /**
     * Get upcoming events - Web view
     */
    @GetMapping("/events/upcoming")
    public String getUpcomingEvents(Model model) {
        List<Event> upcomingEvents = eventService.getUpcomingEvents();
        model.addAttribute("events", upcomingEvents);
        model.addAttribute("upcomingOnly", true);
        return "events-list";
    }

    /**
     * Get event by ID - Web view
     */
    @GetMapping("/events/{id}")
    public String getEventById(@PathVariable Long id, Model model) {
        eventService.getEventById(id)
                .ifPresent(event -> model.addAttribute("event", event));
        return "event-details";
    }

    /**
     * Show form for creating a new event
     */
    @GetMapping("/event/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("isNew", true);
        return "event-form";
    }

    /**
     * Show form for editing an existing event
     */
    @GetMapping("/event/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        eventService.getEventById(id)
                .ifPresent(event -> {
                    model.addAttribute("event", event);
                    model.addAttribute("isNew", false);
                });
        return "event-form";
    }

    /**
     * Create a new event - Form submission
     */
    @PostMapping("/event")
    public String createEvent(@ModelAttribute Event event) {
        eventService.saveEvent(event);
        return "redirect:/events";
    }

    /**
     * Update an existing event - Form submission
     */
    @PutMapping("/event/{id}")
    public String updateEvent(@PathVariable Long id, @ModelAttribute Event event) {
        eventService.updateEvent(id, event);
        return "redirect:/events/" + id;
    }

    /**
     * Delete an event
     */
    @DeleteMapping("/event/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

    // REST API endpoints for programmatic access

    /**
     * Get all events - REST API
     */
    @GetMapping(value = "/api/events", produces = "application/json")
    @ResponseBody
    public List<Event> getAllEventsApi() {
        return eventService.getAllEvents();
    }

    /**
     * Get upcoming events - REST API
     */
    @GetMapping(value = "/api/events/upcoming", produces = "application/json")
    @ResponseBody
    public List<Event> getUpcomingEventsApi() {
        return eventService.getUpcomingEvents();
    }

    /**
     * Get event by ID - REST API
     */
    @GetMapping(value = "/api/events/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Event> getEventByIdApi(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new event - REST API
     */
    @PostMapping("/api/event")
    @ResponseBody
    public ResponseEntity<Event> createEventApi(@RequestBody Event event) {
        Event savedEvent = eventService.saveEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    /**
     * Update an existing event - REST API
     */
    @PutMapping("/api/event/{id}")
    @ResponseBody
    public ResponseEntity<Event> updateEventApi(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete an event - REST API
     */
    @DeleteMapping("/api/event/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteEventApi(@PathVariable Long id) {
        boolean deleted = eventService.deleteEvent(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
