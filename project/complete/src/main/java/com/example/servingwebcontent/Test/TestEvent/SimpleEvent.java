package com.example.servingwebcontent.Test.TestEvent;

import com.example.servingwebcontent.model.EventManagement.EventRecurrenceType;
import com.example.servingwebcontent.model.EventManagement.EventStatus;

import java.time.LocalDateTime;

// Plain Java POJO (no JPA) for standalone Event testing
public class SimpleEvent {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventStatus status = EventStatus.UPCOMING;
    private EventRecurrenceType recurrence = EventRecurrenceType.NONE;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public EventRecurrenceType getRecurrence() { return recurrence; }
    public void setRecurrence(EventRecurrenceType recurrence) { this.recurrence = recurrence; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

