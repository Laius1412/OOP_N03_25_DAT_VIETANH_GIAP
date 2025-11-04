package com.example.servingwebcontent.service.EventManagement;

import com.example.servingwebcontent.model.EventManagement.Event;
import com.example.servingwebcontent.model.EventManagement.EventStatus;
import com.example.servingwebcontent.repository.EventManagement.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()) return eventRepository.findAll();
        return eventRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Event> findByDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return eventRepository.findByDateRange(start, end);
    }

    public List<Event> upcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findAll().stream()
                .filter(e -> e.getStartTime().isAfter(now))
                .sorted(Comparator.comparing(Event::getStartTime))
                .collect(Collectors.toList());
    }

    public List<Event> pastEvents() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findAll().stream()
                .filter(e -> e.getEndTime().isBefore(now))
                .sorted(Comparator.comparing(Event::getStartTime).reversed())
                .collect(Collectors.toList());
    }

    public List<Event> ongoingEvents() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findAll().stream()
                .filter(e -> !e.getStartTime().isAfter(now) && !e.getEndTime().isBefore(now))
                .sorted(Comparator.comparing(Event::getStartTime))
                .collect(Collectors.toList());
    }

    public Optional<Event> findById(Long id) { return eventRepository.findById(id); }

    public Event save(Event event) { return eventRepository.save(event); }

    public void delete(Long id) { eventRepository.deleteById(id); }

    public void updateStatusBasedOnTime(Event event) {
        LocalDateTime now = LocalDateTime.now();
        if (event.getStartTime().isAfter(now)) {
            event.setStatus(EventStatus.UPCOMING);
        } else if (!now.isBefore(event.getStartTime()) && !now.isAfter(event.getEndTime())) {
            event.setStatus(EventStatus.ONGOING);
        } else {
            event.setStatus(EventStatus.COMPLETED);
        }
    }

    public List<Event> findByMonth(int year, int month) {
        LocalDate first = LocalDate.of(year, month, 1);
        LocalDateTime start = first.atStartOfDay();
        LocalDateTime end = first.withDayOfMonth(first.lengthOfMonth()).atTime(LocalTime.MAX);
        return eventRepository.findByDateRange(start, end);
    }
}
