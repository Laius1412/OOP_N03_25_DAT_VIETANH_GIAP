package Controller;

import Model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserController {
    private List<User> users = new ArrayList<>();

    // CREATE
    @SuppressWarnings("resource")
    public User create(User entity) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap username: ");
        String username = sc.nextLine();

        // Kiểm tra trùng username
        if (readById(username).isPresent()) {
            System.out.println("Username da ton tai!");
            return null;
        }
        entity.setUsername(username);

        System.out.print("Nhap mat khau: ");
        String password1 = sc.next();
        System.out.print("Xac nhan lai mat khau: ");
        String password2 = sc.next();

        while (!password1.equals(password2)) {
            System.out.println("Mat khau khong trung khop!");
            System.out.print("Xac nhan lai mat khau: ");
            password2 = sc.next();
        }
        entity.setPassword(password2);

        System.out.println("Chon chuc vu: ");
        System.out.println("1. Quan ly thanh vien");
        System.out.println("2. Quan ly tai chinh");
        System.out.println("3. Quan ly su kien");
        String r = sc.next();

        switch (r) {
            case "1":
                entity.setRole("qltv");
                break;
            case "2":
                entity.setRole("qltc");
                break;
            case "3":
                entity.setRole("qlsk");
                break;
            default:
                entity.setRole("unknown");
                break;
        }

        users.add(entity);
        System.out.println("Tao tai khoan thanh cong!");
        return entity;
    }

    // READ
    public Optional<User> readById(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public List<User> readAll() {
        return users;
    }
    
    public void read(String username) {
        Optional<User> optUser = readById(username);
        if (optUser.isPresent()) {
            User u = optUser.get();
            System.out.println("Username: " + u.getUsername());
            System.out.println("Role: " + u.getRole());
        } else {
            System.out.println("Khong tim thay user: " + username);
        }
    }

    // UPDATE
    public boolean update(String username, String newPassword, String newRole) {
        Optional<User> optUser = readById(username);
        if (optUser.isPresent()) {
            User u = optUser.get();
            if (newPassword != null && !newPassword.isEmpty()) {
                u.setPassword(newPassword);
            }
            if (newRole != null && !newRole.isEmpty()) {
                u.setRole(newRole);
            }
            System.out.println("Cap nhat tai khoan thanh cong!");
            return true;
        }
        System.out.println("Khong tim thay user de cap nhat!");
        return false;
    }

    // DELETE
    public boolean delete(String username) {
        Optional<User> optUser = readById(username);
        if (optUser.isPresent()) {
            users.remove(optUser.get());
            System.out.println("Xoa tai khoan thanh cong!");
            return true;
        }
        System.out.println("Khong tim thay user de xoa!");
        return false;
    }
}
