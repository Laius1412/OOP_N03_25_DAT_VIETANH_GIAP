package Test;

import Model.Person;
import java.time.LocalDate;

public class testPerson {
        public static void runTest() {
                Person father = new Person("Nguyen Van A", "Nam",
                                LocalDate.of(1970, 5, 12), null, 123456789, "Ha Noi");
                Person mother = new Person("Tran Thi B", "Nữ",
                                LocalDate.of(1972, 8, 20), null, 987654321, "Ha Noi");

                Person child = new Person("Nguyen Van C", "Nam",
                                LocalDate.of(2000, 1, 1), null, 111222333, "Ha Noi");
                child.setParents(father, mother);

                Person spouse = new Person("Le Thi D", "Nu",
                                LocalDate.of(2001, 3, 15), null, 444555666, "Hai Phong");
                child.setSpouse(spouse);

                System.out.println("=== Thong tin Cha ===");
                father.display();
                System.out.println("\n=== Thong tin Me ===");
                mother.display();
                System.out.println("\n=== Thong tin Con ===");
                child.display();
                System.out.println("\n=== Thong tin Vo/Chong cua Con ===");
                spouse.display();
        }
}
