package com.example.servingwebcontent.Test.TestFinance;

import com.example.servingwebcontent.model.FinanceManagement.TransactionType;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Pure Java demo (no Spring, no DB) to test add/edit/delete for FinanceCategory.
 */
public class TestFiance {

    private final Map<Long, SimpleCategory> store = new LinkedHashMap<>();
    private long idSeq = 1L;

    public SimpleCategory addCategory(String name, String description, TransactionType type) {
        SimpleCategory c = new SimpleCategory();
        c.setId(idSeq++);
        c.setName(name);
        c.setDescription(description);
        c.setType(type);
        c.setIsActive(true);
        c.setCreatedAt(LocalDateTime.now());
        c.setUpdatedAt(LocalDateTime.now());
        store.put(c.getId(), c);
        return c;
    }

    public Optional<SimpleCategory> editCategory(Long id, String newName, String newDescription, TransactionType newType) {
        SimpleCategory c = store.get(id);
        if (c == null) return Optional.empty();
        if (newName != null) c.setName(newName);
        if (newDescription != null) c.setDescription(newDescription);
        if (newType != null) c.setType(newType);
        c.setUpdatedAt(LocalDateTime.now());
        return Optional.of(c);
    }

    public boolean deleteCategory(Long id) {
        return store.remove(id) != null;
    }

    public boolean deleteCategoryByName(String name) {
        Optional<SimpleCategory> match = store.values().stream()
                .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(name))
                .findFirst();
        match.ifPresent(c -> store.remove(c.getId()));
        return match.isPresent();
    }

    public List<SimpleCategory> listCategories() {
        return new ArrayList<>(store.values());
    }

    public static void demo() {
        TestFiance tf = new TestFiance();
        System.out.println("=== DEMO: FinanceCategory (pure Java) ===");

        // Add
        SimpleCategory income = tf.addCategory("Quyen gop", "Tien quyen gop", TransactionType.INCOME);
        SimpleCategory expense = tf.addCategory("Chi Le", "Chi phi to chuc le", TransactionType.EXPENSE);
        System.out.println("After add:");
        tf.listCategories().forEach(TestFiance::print);

        // Edit
        tf.editCategory(income.getId(), "Dong gop", "Dong gop tu nguyen", TransactionType.INCOME);
        System.out.println("\nAfter edit (income -> Dong gop):");
        tf.listCategories().forEach(TestFiance::print);

        // Delete
        tf.deleteCategory(expense.getId());
        System.out.println("\nAfter delete (remove Chi Le):");
        tf.listCategories().forEach(TestFiance::print);
    }

    private static void print(SimpleCategory c) {
        System.out.printf("- id=%d, name=%s, type=%s, active=%s, updatedAt=%s%n",
                c.getId(), c.getName(), c.getType(), c.getIsActive(), c.getUpdatedAt());
    }

    public static void cli() {
        TestFiance tf = new TestFiance();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== QUAN LY DANH MUC (Java thuan) ===");
        while (true) {
            try {
                System.out.println("\nChon chuc nang:");
                System.out.println("1. Them danh muc");
                System.out.println("2. Sua danh muc (theo ID)");
                System.out.println("3. Xoa danh muc (nhap ten)");
                System.out.println("4. Hien thi danh sach");
                System.out.println("0. Thoat");
                System.out.print("Lua chon: ");

                if (!sc.hasNextLine()) {
                    System.out.println("Khong co input (EOF). Thoat.");
                    return;
                }
                String choice = sc.nextLine().trim();
                switch (choice) {
                case "1": {
                    System.out.print("Ten: ");
                    if (!sc.hasNextLine()) { System.out.println("Huy thao tac."); break; }
                    String name = sc.nextLine();
                    System.out.print("Mo ta: ");
                    if (!sc.hasNextLine()) { System.out.println("Huy thao tac."); break; }
                    String desc = sc.nextLine();
                    System.out.print("Loai (INCOME/EXPENSE): ");
                    if (!sc.hasNextLine()) { System.out.println("Huy thao tac."); break; }
                    String typeStr = sc.nextLine();
                    TransactionType type;
                    try {
                        type = TransactionType.valueOf(typeStr.trim().toUpperCase());
                    } catch (Exception e) {
                        System.out.println("Loai khong hop le. Bo qua.");
                        break;
                    }
                    SimpleCategory c = tf.addCategory(name, desc, type);
                    System.out.println("Da them: ");
                    print(c);
                    break;
                }
                case "2": {
                    System.out.print("Nhap ID danh muc can sua: ");
                    if (!sc.hasNextLine()) { System.out.println("Huy thao tac."); break; }
                    String idStr = sc.nextLine();
                    Long id;
                    try {
                        id = Long.parseLong(idStr.trim());
                    } catch (NumberFormatException ex) {
                        System.out.println("ID khong hop le.");
                        break;
                    }
                    System.out.print("Ten moi (de trong neu giu nguyen): ");
                    if (!sc.hasNextLine()) { System.out.println("Huy thao tac."); break; }
                    String newName = sc.nextLine();
                    if (newName.isBlank()) newName = null;
                    System.out.print("Mo ta moi (de trong neu giu nguyen): ");
                    if (!sc.hasNextLine()) { System.out.println("Huy thao tac."); break; }
                    String newDesc = sc.nextLine();
                    if (newDesc.isBlank()) newDesc = null;
                    System.out.print("Loai moi (INCOME/EXPENSE, de trong neu giu nguyen): ");
                    if (!sc.hasNextLine()) { System.out.println("Huy thao tac."); break; }
                    String newTypeStr = sc.nextLine();
                    TransactionType newType = null;
                    if (!newTypeStr.isBlank()) {
                        try {
                            newType = TransactionType.valueOf(newTypeStr.trim().toUpperCase());
                        } catch (Exception e) {
                            System.out.println("Loai khong hop le, giu nguyen.");
                        }
                    }
                    Optional<SimpleCategory> updated = tf.editCategory(id, newName, newDesc, newType);
                    if (updated.isPresent()) {
                        System.out.println("Da cap nhat: ");
                        print(updated.get());
                    } else {
                        System.out.println("Khong tim thay danh muc ID=" + id);
                    }
                    break;
                }
                case "3": {
                    System.out.print("Nhap ten danh muc can xoa: ");
                    if (!sc.hasNextLine()) { System.out.println("Huy thao tac."); break; }
                    String name = sc.nextLine();
                    boolean ok = tf.deleteCategoryByName(name);
                    System.out.println(ok ? "Da xoa danh muc '" + name + "'" : "Khong tim thay danh muc ten '" + name + "'");
                    break;
                }
                case "4": {
                    System.out.println("Danh sach danh muc:");
                    List<SimpleCategory> list = tf.listCategories();
                    if (list.isEmpty()) {
                        System.out.println("(Trong)");
                    } else {
                        list.forEach(TestFiance::print);
                    }
                    break;
                }
                case "0":
                    System.out.println("Thoat.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
                }
            } catch (NoSuchElementException eof) {
                System.out.println("Khong co input (EOF). Thoat.");
                return;
            }
        }
    }
}
