package com.kiet.eventmanagementportal.repository;

import com.kiet.eventmanagementportal.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Event entity providing CRUD operations and custom queries.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    /**
     * Find all events that are scheduled in the future
     * @param currentDateTime the current date and time
     * @return list of upcoming events
     */
    @Query("SELECT e FROM Event e WHERE e.date > ?1 ORDER BY e.date ASC")
    List<Event> findUpcomingEvents(LocalDateTime currentDateTime);
}