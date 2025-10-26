package com.example.servingwebcontent.Model.EventManagement;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "current_participants")
    private Integer currentParticipants = 0;

    @Column(name = "created_by_id", nullable = false)
    private Long createdById; // tham chiếu User.id

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status = EventStatus.UPCOMING;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence", nullable = false)
    private EventRecurrenceType recurrence = EventRecurrenceType.NONE;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private EventCategory category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventParticipant> participants;

    // ===== Constructors =====
    public Event() {}

    public Event(String title, String description, String location,
                 LocalDateTime startTime, LocalDateTime endTime,
                 Integer maxParticipants, Long createdById,
                 EventCategory category) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxParticipants = maxParticipants;
        this.createdById = createdById;
        this.category = category;
        this.status = EventStatus.UPCOMING;
    }

    // ===== Getter & Setter =====
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

    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }

    public Integer getCurrentParticipants() { return currentParticipants; }
    public void setCurrentParticipants(Integer currentParticipants) { this.currentParticipants = currentParticipants; }

    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public EventRecurrenceType getRecurrence() { return recurrence; }
    public void setRecurrence(EventRecurrenceType recurrence) { this.recurrence = recurrence; }

    public EventCategory getCategory() { return category; }
    public void setCategory(EventCategory category) { this.category = category; }

    public List<EventParticipant> getParticipants() { return participants; }
    public void setParticipants(List<EventParticipant> participants) { this.participants = participants; }

    // ===== Utility methods =====
    public boolean isUpcoming() {
        return this.startTime.isAfter(LocalDateTime.now());
    }

    public boolean isOngoing() {
        return LocalDateTime.now().isAfter(this.startTime) && LocalDateTime.now().isBefore(this.endTime);
    }

    public long getDurationInHours() {
        return java.time.Duration.between(this.startTime, this.endTime).toHours();
    }
}
