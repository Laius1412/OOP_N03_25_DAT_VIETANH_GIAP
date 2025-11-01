package com.example.servingwebcontent.repository.EventManagement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.servingwebcontent.model.EventManagement.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTitleContainingIgnoreCase(String keyword);

    @Query("SELECT e FROM Event e WHERE e.startTime BETWEEN :start AND :end")
    List<Event> findByDateRange(LocalDateTime start, LocalDateTime end);
}
