package com.example.servingwebcontent.Test.TestPerson;

import com.example.servingwebcontent.model.PersonManagement.Gender;

import java.time.LocalDateTime;
import java.util.*;


public class TestPerson {

    private final Map<Long, SimplePerson> store = new LinkedHashMap<>();
    private long idSeq = 1L;

    public SimplePerson addPerson(String name, Gender gender, String phone, String address) {
        SimplePerson p = new SimplePerson();
        p.setId(idSeq++);
        p.setName(name);
        if (gender != null) p.setGender(gender);
        p.setPhone(phone);
        p.setAddress(address);
        p.setCreatedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());
        store.put(p.getId(), p);
        return p;
    }

    public Optional<SimplePerson> editPerson(Long id, String name, Gender gender, String phone, String address) {
        SimplePerson p = store.get(id);
        if (p == null) return Optional.empty();
        if (name != null) p.setName(name);
        if (gender != null) p.setGender(gender);
        if (phone != null) p.setPhone(phone);
        if (address != null) p.setAddress(address);
        p.setUpdatedAt(LocalDateTime.now());
        return Optional.of(p);
    }

    public boolean deletePersonByName(String name) {
        Optional<SimplePerson> match = store.values().stream()
                .filter(pp -> pp.getName() != null && pp.getName().equalsIgnoreCase(name))
                .findFirst();
        match.ifPresent(pp -> store.remove(pp.getId()));
        return match.isPresent();
    }

    public List<SimplePerson> listPersons() {
        return new ArrayList<>(store.values());
    }

    private static void print(SimplePerson p) {
        System.out.printf("- id=%d, name=%s, gender=%s, phone=%s, address=%s, alive=%s, updatedAt=%s%n",
                p.getId(), p.getName(), p.getGender(), p.getPhone(), p.getAddress(), p.isAlive(), p.getUpdatedAt());
    }

    public static void demo() {
        TestPerson tp = new TestPerson();
        System.out.println("=== DEMO: Person (pure Java) ===");
        SimplePerson a = tp.addPerson("Nguyen Van A", Gender.MALE, "0901", "Ha Noi");
        SimplePerson b = tp.addPerson("Tran Thi B", Gender.FEMALE, "0902", "Ho Chi Minh");
        System.out.println("After add:");
        tp.listPersons().forEach(TestPerson::print);

        tp.editPerson(a.getId(), "Nguyen Van A (updated)", null, null, "Da Nang");
        System.out.println("\nAfter edit A:");
        tp.listPersons().forEach(TestPerson::print);

        tp.deletePersonByName("Tran Thi B");
        System.out.println("\nAfter delete (name=Tran Thi B):");
        tp.listPersons().forEach(TestPerson::print);
    }

    public static void cli() {
        TestPerson tp = new TestPerson();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== QUAN LY THANH VIEN (Java thuan) ===");
        while (true) {
            try {
                System.out.println("\nChon chuc nang:");
                System.out.println("1. Them thanh vien");
                System.out.println("2. Sua thanh vien (theo ID)");
                System.out.println("3. Xoa thanh vien (nhap ten)");
                System.out.println("4. Hien thi danh sach");
                System.out.println("0. Thoat");
                System.out.print("Lua chon: ");
                if (!sc.hasNextLine()) { System.out.println("EOF. Thoat."); return; }
                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1": {
                        System.out.print("Ten: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String name = sc.nextLine();
                        System.out.print("Gioi tinh (MALE/FEMALE): "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String gStr = sc.nextLine();
                        Gender gender;
                        try { gender = Gender.valueOf(gStr.trim().toUpperCase()); } catch (Exception ex) { System.out.println("Gioi tinh khong hop le."); break; }
                        System.out.print("Dien thoai: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String phone = sc.nextLine();
                        System.out.print("Dia chi: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String address = sc.nextLine();
                        SimplePerson p = tp.addPerson(name, gender, phone, address);
                        System.out.println("Da them: "); print(p);
                        break;
                    }
                    case "2": {
                        System.out.print("Nhap ID thanh vien can sua: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String idStr = sc.nextLine();
                        Long id; try { id = Long.parseLong(idStr.trim()); } catch (Exception ex) { System.out.println("ID khong hop le."); break; }
                        System.out.print("Ten moi (trong neu giu): "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String newName = sc.nextLine(); if (newName.isBlank()) newName = null;
                        System.out.print("Gioi tinh moi (MALE/FEMALE, trong neu giu): "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String newGStr = sc.nextLine();
                        Gender newGender = null; if (!newGStr.isBlank()) { try { newGender = Gender.valueOf(newGStr.trim().toUpperCase()); } catch (Exception ignored) {} }
                        System.out.print("Dien thoai moi (trong neu giu): "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String newPhone = sc.nextLine(); if (newPhone.isBlank()) newPhone = null;
                        System.out.print("Dia chi moi (trong neu giu): "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String newAddress = sc.nextLine(); if (newAddress.isBlank()) newAddress = null;
                        Optional<SimplePerson> updated = tp.editPerson(id, newName, newGender, newPhone, newAddress);
                        System.out.println(updated.isPresent() ? "Da cap nhat: " : "Khong tim thay thanh vien.");
                        updated.ifPresent(TestPerson::print);
                        break;
                    }
                    case "3": {
                        System.out.print("Nhap ten thanh vien can xoa: "); if (!sc.hasNextLine()) { System.out.println("Huy."); break; }
                        String name = sc.nextLine();
                        boolean ok = tp.deletePersonByName(name);
                        System.out.println(ok ? "Da xoa '" + name + "'" : "Khong tim thay thanh vien '" + name + "'");
                        break;
                    }
                    case "4": {
                        System.out.println("Danh sach thanh vien:");
                        List<SimplePerson> list = tp.listPersons();
                        if (list.isEmpty()) System.out.println("(Trong)"); else list.forEach(TestPerson::print);
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
