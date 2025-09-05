package Test;

import java.time.LocalDate;

import Model.Person;
import Model.Recursion;

public class TestRecursion {
    public static void runTest() {
        Person[] people = new Person[] {
            new Person("An", "Nam", LocalDate.of(1980, 1, 1), null, 0, ""),
            new Person("Binh", "Nam", LocalDate.of(2000, 5, 20), null, 0, ""),
            new Person("Chau", "Nu", LocalDate.of(1995, 3, 15), null, 0, ""),
            new Person("Dung", "Nu", LocalDate.of(1975, 7, 7), null, 0, "")
        };

        System.out.println("=== Danh sach goc (nam sinh) ===");
        for (Person p : people) {
            System.out.println(p.getName() + " - " + (p.getDob() != null ? p.getDob().getYear() : "null"));
        }

        Recursion.sortPersonsByAgeAsc(people);
        System.out.println("\n=== Sap xep tang dan theo tuoi (tre -> gia) ===");
        for (Person p : people) {
            System.out.println(p.getName() + " - " + (p.getDob() != null ? p.getDob().getYear() : "null"));
        }

        Recursion.sortPersonsByAgeDesc(people);
        System.out.println("\n=== Sap xep giam dan theo tuoi (gia -> tre) ===");
        for (Person p : people) {
            System.out.println(p.getName() + " - " + (p.getDob() != null ? p.getDob().getYear() : "null"));
        }
    }
}
