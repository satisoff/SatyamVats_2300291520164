package com.kiet.eventmanagementportal.service;

import com.kiet.eventmanagementportal.model.Event;
import com.kiet.eventmanagementportal.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling Event-related business logic.
 */
@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Get all events
     * @return list of all events
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Get event by ID
     * @param id the event ID
     * @return the event if found, otherwise empty
     */
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    /**
     * Get all upcoming events (events with future dates)
     * @return list of upcoming events
     */
    public List<Event> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now());
    }

    /**
     * Save a new event
     * @param event the event to save
     * @return the saved event
     */
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    /**
     * Update an existing event
     * @param id the ID of the event to update
     * @param eventDetails the updated event details
     * @return the updated event if found, otherwise empty
     */
    public Optional<Event> updateEvent(Long id, Event eventDetails) {
        return eventRepository.findById(id)
                .map(existingEvent -> {
                    existingEvent.setName(eventDetails.getName());
                    existingEvent.setDate(eventDetails.getDate());
                    existingEvent.setVenue(eventDetails.getVenue());
                    existingEvent.setDescription(eventDetails.getDescription());
                    return eventRepository.save(existingEvent);
                });
    }

    /**
     * Delete an event by ID
     * @param id the ID of the event to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteEvent(Long id) {
        return eventRepository.findById(id)
                .map(event -> {
                    eventRepository.delete(event);
                    return true;
                })
                .orElse(false);
    }
}