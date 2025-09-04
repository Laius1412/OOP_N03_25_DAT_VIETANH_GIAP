import java.time.LocalDate;

public class testPerson {
        public static void runTest() {
                // Tạo đối tượng cha mẹ
                Person father = new Person("Nguyen Van A", "Nam",
                                LocalDate.of(1970, 5, 12), null, 123456789, "Hà Nội");
                Person mother = new Person("Tran Thi B", "Nữ",
                                LocalDate.of(1972, 8, 20), null, 987654321, "Hà Nội");

                // Tạo đối tượng con
                Person child = new Person("Nguyen Van C", "Nam",
                                LocalDate.of(2000, 1, 1), null, 111222333, "Hà Nội");
                child.setParents(father, mother);

                // Tạo vợ/chồng
                Person spouse = new Person("Le Thi D", "Nữ",
                                LocalDate.of(2001, 3, 15), null, 444555666, "Hải Phòng");
                child.setSpouse(spouse);

                // Hiển thị thông tin
                System.out.println("=== Thông tin Cha ===");
                father.display();
                System.out.println("\n=== Thông tin Mẹ ===");
                mother.display();
                System.out.println("\n=== Thông tin Con ===");
                child.display();
                System.out.println("\n=== Thông tin Vợ/Chồng của Con ===");
                spouse.display();
        }
}
