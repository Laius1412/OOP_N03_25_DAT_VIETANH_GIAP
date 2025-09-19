package Test;

import Controller.PersonService;
import Controller.interfaces.CrudPerson;
import Model.Person;
import java.util.ArrayList;

public class testPerson {
        private static final CrudPerson<Person, String> service = new PersonService();

        public static void runTest() {
                java.util.Scanner sc = new java.util.Scanner(System.in);
                while (true) {
                        System.out.println("\n===== Quan ly Person =====");
                        System.out.println("1. Them nguoi");
                        System.out.println("2. Danh sach tat ca");
                        System.out.println("3. Cap nhat theo ten");
                        System.out.println("4. Xoa theo ten");
                        System.out.println("5. Tim theo ten");
                        System.out.println("0. Thoat");
                        System.out.print("Chon: ");
                        String choice = sc.nextLine().trim();

                        switch (choice) {
                                case "1":
                                        Person p = inputPerson(sc);
                                        service.create(p);
                                        System.out.println("Da them!");
                                        break;
                                case "2":
                                        printPeople(service.findAll());
                                        break;
                                case "3":
                                        System.out.print("Nhap ten nguoi can cap nhat: ");
                                        String nameToUpdate = sc.nextLine().trim();
                                        Person updateData = inputUpdateData(sc);
                                        boolean ok = service.update(nameToUpdate, updateData);
                                        System.out.println(ok ? "Cap nhat thanh cong" : "Khong tim thay");
                                        break;
                                case "4":
                                        System.out.print("Nhap ten nguoi can xoa: ");
                                        String nameToDelete = sc.nextLine().trim();
                                        boolean del = service.delete(nameToDelete);
                                        System.out.println(del ? "Xoa thanh cong" : "Khong tim thay");
                                        break;
                                case "5":
                                        System.out.print("Nhap ten can tim: ");
                                        String nameToFind = sc.nextLine().trim();
                                        Person found = service.findById(nameToFind);
                                        if (found == null) {
                                                System.out.println("Khong tim thay");
                                        } else {
                                                found.display();
                                        }
                                        break;
                                case "0":
                                        System.out.println("Thoat chuong trinh quan ly.");
                                        return;
                                default:
                                        System.out.println("Lua chon khong hop le!");
                        }
                }
        }

        private static Person inputPerson(java.util.Scanner sc) {
                System.out.print("Ten: ");
                String name = sc.nextLine().trim();

                System.out.print("Gioi tinh: ");
                String gender = sc.nextLine().trim();

                System.out.print("Ngay sinh (yyyy-MM-dd): ");
                java.time.LocalDate dob = parseDate(sc.nextLine().trim());

                System.out.print("Ngay mat (bo trong neu chua mat): ");
                String dodStr = sc.nextLine().trim();
                java.time.LocalDate dod = dodStr.isEmpty() ? null : parseDate(dodStr);

                System.out.print("So dien thoai (so nguyen): ");
                int phone = parseInt(sc.nextLine().trim());

                System.out.print("Dia chi: ");
                String address = sc.nextLine().trim();

                Person p = new Person(name, gender, dob, dod, phone, address);

                System.out.print("Ten Cha (bo trong neu khong): ");
                String fatherName = sc.nextLine().trim();
                if (!fatherName.isEmpty()) {
                        Person father = service.findById(fatherName);
                        if (father != null) p.setFather(father);
                        else System.out.println("Khong tim thay ten cha: " + fatherName);
                }

                System.out.print("Ten Me (bo trong neu khong): ");
                String motherName = sc.nextLine().trim();
                if (!motherName.isEmpty()) {
                        Person mother = service.findById(motherName);
                        if (mother != null) p.setMother(mother);
                        else System.out.println("Khong tim thay ten me: " + motherName);
                }

                System.out.print("Ten Vo/Chong (bo trong neu khong): ");
                String spouseName = sc.nextLine().trim();
                if (!spouseName.isEmpty()) {
                        Person spouse = service.findById(spouseName);
                        if (spouse != null) p.setSpouse(spouse);
                        else System.out.println("Khong tim thay ten vo/chong: " + spouseName);
                }

                return p;
        }

        private static Person inputUpdateData(java.util.Scanner sc) {
                Person data = new Person();
                System.out.println("Bo trong truong nao ban khong muon thay doi.");

                System.out.print("Ten moi: ");
                String name = sc.nextLine().trim();
                if (!name.isEmpty()) data.setName(name);

                System.out.print("Gioi tinh moi: ");
                String gender = sc.nextLine().trim();
                if (!gender.isEmpty()) data.setGender(gender);

                System.out.print("Ngay sinh moi (yyyy-MM-dd): ");
                String dobStr = sc.nextLine().trim();
                if (!dobStr.isEmpty()) data.setDob(parseDate(dobStr));

                System.out.print("Ngay mat moi: ");
                String dodStr = sc.nextLine().trim();
                if (!dodStr.isEmpty()) data.setDod(parseDate(dodStr));

                System.out.print("So dien thoai moi: ");
                String phoneStr = sc.nextLine().trim();
                if (!phoneStr.isEmpty()) data.setPhone(parseInt(phoneStr));

                System.out.print("Dia chi moi: ");
                String address = sc.nextLine().trim();
                if (!address.isEmpty()) data.setAddress(address);

                return data;
        }

        private static java.time.LocalDate parseDate(String input) {
                try {
                        return java.time.LocalDate.parse(input);
                } catch (Exception e) {
                        System.out.println("Dinh dang ngay khong hop le, su dung ngay hom nay.");
                        return java.time.LocalDate.now();
                }
        }

        private static int parseInt(String input) {
                try {
                        return Integer.parseInt(input);
                } catch (Exception e) {
                        System.out.println("So khong hop le, dat ve 0.");
                        return 0;
                }
        }

        private static void printPeople(ArrayList<Person> people) {
                if (people.isEmpty()) {
                        System.out.println("Danh sach trong.");
                        return;
                }
                int index = 1;
                for (Person p : people) {
                        System.out.println("-- Nguoi thu " + index + " --");
                        p.display();
                        System.out.println();
                        index++;
                }
        }
}
