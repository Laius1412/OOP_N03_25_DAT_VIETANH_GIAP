package com.example.servingwebcontent.controller.EventControllers;

import com.example.servingwebcontent.model.EventManagement.Event;
import com.example.servingwebcontent.model.EventManagement.EventCategory;
import com.example.servingwebcontent.model.EventManagement.EventStatus;
import com.example.servingwebcontent.model.EventManagement.EventRecurrenceType;
import com.example.servingwebcontent.model.Role;
import com.example.servingwebcontent.service.MessageService;
import com.example.servingwebcontent.repository.EventManagement.EventCategoryRepository;
import com.example.servingwebcontent.service.EventManagement.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final EventCategoryRepository categoryRepository;
    private final MessageService messageService;

    public EventController(EventService eventService,
                           EventCategoryRepository categoryRepository,
                           MessageService messageService) {
        this.eventService = eventService;
        this.categoryRepository = categoryRepository;
        this.messageService = messageService;
    }

    @GetMapping
    public String index(@RequestParam(name = "q", required = false) String q,
                        @RequestParam(name = "date", required = false) String date,
                        @RequestParam(name = "year", required = false) Integer year,
                        @RequestParam(name = "month", required = false) Integer month,
                        Model model,
                        HttpSession session) {
        List<Event> events;
        if (date != null && !date.isBlank()) {
            LocalDate d = LocalDate.parse(date);
            events = eventService.findByDate(d);
        } else if (q != null && !q.isBlank()) {
            events = eventService.searchByTitle(q);
        } else {
            events = eventService.upcomingEvents();
        }

        List<Event> ongoing = eventService.ongoingEvents();
        List<Event> upcoming = eventService.upcomingEvents();
        List<Event> past = eventService.pastEvents();

        int currentYear = (year != null) ? year : LocalDate.now().getYear();
        int currentMonth = (month != null && month >=1 && month <=12) ? month : LocalDate.now().getMonthValue();
        List<Event> all = eventService.searchByTitle("");
        List<Event> monthEvents = eventService.findByMonth(currentYear, currentMonth);
        Set<Long> included = new HashSet<>();
        List<Map<String,Object>> eventsData = new ArrayList<>();
        for (Event e : monthEvents) {
            included.add(e.getId());
            eventsData.add(toData(e.getId(), e.getTitle(), e.getStartTime(), e.getEndTime()));
        }
        // Add recurrences
        for (Event e : all) {
            if (e.getRecurrence() == null || e.getRecurrence() == EventRecurrenceType.NONE) continue;
            // monthly: same day-of-month each month
            if (e.getRecurrence() == EventRecurrenceType.MONTHLY) {
                int day = e.getStartTime().getDayOfMonth();
                java.time.LocalDate base = java.time.LocalDate.of(currentYear, currentMonth, 1);
                int lastDay = base.lengthOfMonth();
                int d = Math.min(day, lastDay);
                java.time.LocalDateTime st = java.time.LocalDateTime.of(currentYear, currentMonth, d,
                        e.getStartTime().getHour(), e.getStartTime().getMinute());
                java.time.Duration dur = java.time.Duration.between(e.getStartTime(), e.getEndTime());
                java.time.LocalDateTime en = st.plus(dur);
                eventsData.add(toData(e.getId(), e.getTitle(), st, en));
            }
            // yearly: same day+month of each year; include only if month matches
            if (e.getRecurrence() == EventRecurrenceType.YEARLY && e.getStartTime().getMonthValue() == currentMonth) {
                int day = e.getStartTime().getDayOfMonth();
                java.time.LocalDate base = java.time.LocalDate.of(currentYear, currentMonth, 1);
                int lastDay = base.lengthOfMonth();
                int d = Math.min(day, lastDay);
                java.time.LocalDateTime st = java.time.LocalDateTime.of(currentYear, currentMonth, d,
                        e.getStartTime().getHour(), e.getStartTime().getMinute());
                java.time.Duration dur = java.time.Duration.between(e.getStartTime(), e.getEndTime());
                java.time.LocalDateTime en = st.plus(dur);
                eventsData.add(toData(e.getId(), e.getTitle(), st, en));
            }
        }

        model.addAttribute("q", q == null ? "" : q);
        if (q != null && !q.isBlank()) {
            model.addAttribute("searchResults", eventService.searchByTitle(q));
        }
        model.addAttribute("selectedDate", date);
        model.addAttribute("events", events);
        model.addAttribute("ongoing", ongoing);
        model.addAttribute("upcoming", upcoming);
        model.addAttribute("past", past);
        // categories not shown in UI now
        // model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("year", currentYear);
        model.addAttribute("month", currentMonth);
        model.addAttribute("eventsData", eventsData);
        model.addAttribute("canManage", hasManagePermission(session));
        return "EventManagement/index";
    }

    private Map<String,Object> toData(Long id, String title, LocalDateTime start, LocalDateTime end){
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("title", title);
        m.put("startTime", start.toString());
        m.put("endTime", end.toString());
        return m;
    }

    @GetMapping("/detail/{id}")
    @ResponseBody
    public ResponseEntity<?> detail(@PathVariable Long id) {
        return eventService.findById(id)
                .<ResponseEntity<?>>map(e -> ResponseEntity.ok(toMap(e)))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of(
                        "message", messageService.getMessage("error.event.notfound")
                )));
    }

    @PostMapping("/create")
    public String create(@RequestParam String title,
                         @RequestParam(required = false) String description,
                         @RequestParam String location,
                         @RequestParam("startTime") String startTimeStr,
                         @RequestParam("endTime") String endTimeStr,
                         @RequestParam(required = false) Integer maxParticipants,
                         @RequestParam(name = "currentParticipants", required = false) Integer currentParticipants,
                         @RequestParam(name = "categoryId", required = false) Long categoryId,
                         @RequestParam(name = "recurrence", required = false) String recurrence,
                         HttpSession session,
                         RedirectAttributes ra) {
        if (!hasManagePermission(session)) {
            ra.addFlashAttribute("error", "No permission");
            return "redirect:/events";
        }
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime start = LocalDateTime.parse(startTimeStr, fmt);
            LocalDateTime end = LocalDateTime.parse(endTimeStr, fmt);
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setLocation(location);
            event.setStartTime(start);
            event.setEndTime(end);
            event.setMaxParticipants(maxParticipants);
            if (currentParticipants != null) {
                event.setCurrentParticipants(Math.max(0, currentParticipants));
            }
            Long createdById = Optional.ofNullable(session.getAttribute("user"))
                    .map(u -> (com.example.servingwebcontent.model.User) u)
                    .map(com.example.servingwebcontent.model.User::getId)
                    .orElse(null);
            if (createdById != null) event.setCreatedById(createdById);
            if (categoryId != null) {
                Optional<EventCategory> cat = categoryRepository.findById(categoryId);
                cat.ifPresent(event::setCategory);
            }
            if (recurrence != null && !recurrence.isBlank()) {
                try { event.setRecurrence(EventRecurrenceType.valueOf(recurrence)); } catch (IllegalArgumentException ignore) {}
            }
            eventService.updateStatusBasedOnTime(event);
            eventService.save(event);
            ra.addFlashAttribute("success", messageService.getMessage("success.event.created"));
        } catch (Exception ex) {
            ra.addFlashAttribute("error", messageService.getMessage("error.event.failed"));
        }
        return "redirect:/events";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam(required = false) String description,
                         @RequestParam String location,
                         @RequestParam("startTime") String startTimeStr,
                         @RequestParam("endTime") String endTimeStr,
                         @RequestParam(required = false) Integer maxParticipants,
                         @RequestParam(name = "currentParticipants", required = false) Integer currentParticipants,
                         @RequestParam(name = "categoryId", required = false) Long categoryId,
                         @RequestParam(name = "status", required = false) String status,
                         @RequestParam(name = "recurrence", required = false) String recurrence,
                         HttpSession session,
                         RedirectAttributes ra) {
        if (!hasManagePermission(session)) {
            ra.addFlashAttribute("error", "No permission");
            return "redirect:/events";
        }
        return eventService.findById(id).map(e -> {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                e.setTitle(title);
                e.setDescription(description);
                e.setLocation(location);
                e.setStartTime(LocalDateTime.parse(startTimeStr, fmt));
                e.setEndTime(LocalDateTime.parse(endTimeStr, fmt));
                e.setMaxParticipants(maxParticipants);
                if (currentParticipants != null) {
                    int cur = Math.max(0, currentParticipants);
                    if (e.getMaxParticipants() != null) cur = Math.min(cur, e.getMaxParticipants());
                    e.setCurrentParticipants(cur);
                }
                if (categoryId != null) {
                    categoryRepository.findById(categoryId).ifPresent(e::setCategory);
                } else {
                    e.setCategory(null);
                }
                if (status != null && !status.isBlank()) {
                    try {
                        e.setStatus(EventStatus.valueOf(status));
                    } catch (IllegalArgumentException ignore) { /* ignore invalid */ }
                } else {
                    // If not manually set, auto-update based on time
                    eventService.updateStatusBasedOnTime(e);
                }
                if (recurrence != null && !recurrence.isBlank()) {
                    try { e.setRecurrence(EventRecurrenceType.valueOf(recurrence)); } catch (IllegalArgumentException ignore) {}
                }
                e.setUpdatedAt(LocalDateTime.now());
                eventService.save(e);
                ra.addFlashAttribute("success", messageService.getMessage("success.event.updated"));
            } catch (Exception ex) {
                ra.addFlashAttribute("error", messageService.getMessage("error.event.failed"));
            }
            return "redirect:/events";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", messageService.getMessage("error.event.notfound"));
            return "redirect:/events";
        });
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         HttpSession session,
                         RedirectAttributes ra) {
        if (!hasManagePermission(session)) {
            ra.addFlashAttribute("error", "No permission");
            return "redirect:/events";
        }
        return eventService.findById(id).map(e -> {
            eventService.delete(id);
            ra.addFlashAttribute("success", messageService.getMessage("success.event.deleted"));
            return "redirect:/events";
        }).orElseGet(() -> {
            ra.addFlashAttribute("error", messageService.getMessage("error.event.notfound"));
            return "redirect:/events";
        });
    }

    private boolean hasManagePermission(HttpSession session) {
        Object roleObj = session.getAttribute("role");
        if (roleObj instanceof Role) {
            Role role = (Role) roleObj;
            return role == Role.ADMIN || role == Role.EVENT_MANAGER;
        }
        return false;
    }

    private Map<String, Object> toMap(Event e) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", e.getId());
        m.put("title", e.getTitle());
        m.put("description", e.getDescription());
        m.put("location", e.getLocation());
        m.put("startTime", e.getStartTime().toString());
        m.put("endTime", e.getEndTime().toString());
        m.put("status", e.getStatus().name());
        // category omitted from client detail per request
        m.put("recurrence", e.getRecurrence().name());
        m.put("maxParticipants", e.getMaxParticipants());
        m.put("currentParticipants", e.getCurrentParticipants());
        return m;
    }
}
