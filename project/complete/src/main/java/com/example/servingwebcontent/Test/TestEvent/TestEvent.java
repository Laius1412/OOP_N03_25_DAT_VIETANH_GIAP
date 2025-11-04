package com.example.servingwebcontent.Test.TestEvent;

import com.example.servingwebcontent.model.EventManagement.EventRecurrenceType;
import com.example.servingwebcontent.model.EventManagement.EventStatus;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Pure Java demo (no Spring, no DB) to test add/edit/delete for Event.
 */
public class TestEvent {

    private final Map<Long, SimpleEvent> store = new LinkedHashMap<>();
    private long idSeq = 1L;

    public SimpleEvent addEvent(String title, String description, String location,
                                LocalDateTime start, LocalDateTime end,
                                EventStatus status, EventRecurrenceType recurrence) {
        SimpleEvent e = new SimpleEvent();
        e.setId(idSeq++);
        e.setTitle(title);
        e.setDescription(description);
        e.setLocation(location);
        e.setStartTime(start);
        e.setEndTime(end);
        if (status != null) e.setStatus(status);
        if (recurrence != null) e.setRecurrence(recurrence);
        e.setCreatedAt(LocalDateTime.now());
        e.setUpdatedAt(LocalDateTime.now());
        store.put(e.getId(), e);
        return e;
    }

    public Optional<SimpleEvent> editEvent(Long id, String title, String description, String location,
                                           LocalDateTime start, LocalDateTime end,
                                           EventStatus status, EventRecurrenceType recurrence) {
        SimpleEvent e = store.get(id);
        if (e == null) return Optional.empty();
        if (title != null) e.setTitle(title);
        if (description != null) e.setDescription(description);
        if (location != null) e.setLocation(location);
        if (start != null) e.setStartTime(start);
        if (end != null) e.setEndTime(end);
        if (status != null) e.setStatus(status);
        if (recurrence != null) e.setRecurrence(recurrence);
        e.setUpdatedAt(LocalDateTime.now());
        return Optional.of(e);
    }

    public boolean deleteEventByTitle(String title) {
        Optional<SimpleEvent> match = store.values().stream()
                .filter(ev -> ev.getTitle() != null && ev.getTitle().equalsIgnoreCase(title))
                .findFirst();
        match.ifPresent(ev -> store.remove(ev.getId()));
        return match.isPresent();
    }

    public List<SimpleEvent> listEvents() {
        return new ArrayList<>(store.values());
    }

    public static void demo() {
        TestEvent te = new TestEvent();
        System.out.println("=== DEMO: Event (pure Java) ===");
        SimpleEvent e1 = te.addEvent("Le hoi", "Su kien cong dong", "Nha van hoa",
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(3),
                EventStatus.UPCOMING, EventRecurrenceType.NONE);
        SimpleEvent e2 = te.addEvent("Hop mat", "Gap go gia dinh", "Nha ong A",
                LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(7).plusHours(2),
                EventStatus.UPCOMING, EventRecurrenceType.NONE);
        System.out.println("After add:");
        te.listEvents().forEach(TestEvent::print);

        te.editEvent(e1.getId(), "Le hoi mua xuan", null, null, null, null, null, null);
        System.out.println("\nAfter edit (rename e1):");
        te.listEvents().forEach(TestEvent::print);

        te.deleteEventByTitle("Hop mat");
        System.out.println("\nAfter delete (title=Hop mat):");
        te.listEvents().forEach(TestEvent::print);
    }

    private static void print(SimpleEvent e) {
        System.out.printf("- id=%d, title=%s, at=%s, %s->%s, status=%s, recurrence=%s%n",
                e.getId(), e.getTitle(), e.getLocation(), e.getStartTime(), e.getEndTime(), e.getStatus(), e.getRecurrence());
    }

    public static void cli() {
        TestEvent te = new TestEvent();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== QUAN LY SU KIEN (Java thuan) ===");
        while (true) {
            try {
                System.out.println("\nChon chuc nang:");
                System.out.println("1. Them su kien");
                System.out.println("2. Sua su kien (theo ID)");
                System.out.println("3. Xoa su kien (nhap tieu de)");
                System.out.println("4. Hien thi danh sach");
                System.out.println("0. Thoat");
                System.out.print("Lua chon: ");
                if (!sc.hasNextLine()) { System.out.println("EOF. Thoat."); return; }
                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1": {
                        System.out.print("Tieu de: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String title = sc.nextLine();
                        System.out.print("Mo ta: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String desc = sc.nextLine();
                        System.out.print("Dia diem: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String loc = sc.nextLine();
                        LocalDateTime start = LocalDateTime.now().plusDays(1);
                        LocalDateTime end = start.plusHours(2);
                        SimpleEvent e = te.addEvent(title, desc, loc, start, end, EventStatus.UPCOMING, EventRecurrenceType.NONE);
                        System.out.println("Da them: "); print(e);
                        break;
                    }
                    case "2": {
                        System.out.print("Nhap ID su kien can sua: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String idStr = sc.nextLine();
                        Long id;
                        try { id = Long.parseLong(idStr.trim()); } catch (Exception ex) { System.out.println("ID khong hop le."); break; }
                        System.out.print("Tieu de moi (trong neu giu): "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String newTitle = sc.nextLine(); if (newTitle.isBlank()) newTitle = null;
                        System.out.print("Mo ta moi (trong neu giu): "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String newDesc = sc.nextLine(); if (newDesc.isBlank()) newDesc = null;
                        System.out.print("Dia diem moi (trong neu giu): "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String newLoc = sc.nextLine(); if (newLoc.isBlank()) newLoc = null;
                        Optional<SimpleEvent> updated = te.editEvent(id, newTitle, newDesc, newLoc, null, null, null, null);
                        System.out.println(updated.isPresent() ? "Da cap nhat: " : "Khong tim thay su kien.");
                        updated.ifPresent(TestEvent::print);
                        break;
                    }
                    case "3": {
                        System.out.print("Nhap tieu de su kien can xoa: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String title = sc.nextLine();
                        boolean ok = te.deleteEventByTitle(title);
                        System.out.println(ok ? "Da xoa '" + title + "'" : "Khong tim thay su kien '" + title + "'");
                        break;
                    }
                    case "4": {
                        System.out.println("Danh sach su kien:");
                        List<SimpleEvent> list = te.listEvents();
                        if (list.isEmpty()) System.out.println("(Trong)"); else list.forEach(TestEvent::print);
                        break;
                    }
                    case "0": System.out.println("Thoat."); return;
                    default: System.out.println("Lua chon khong hop le.");
                }
            } catch (NoSuchElementException eof) {
                System.out.println("EOF. Thoat.");
                return;
            }
        }
    }
}
