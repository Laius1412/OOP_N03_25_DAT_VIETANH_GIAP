package Model;

import Controller.interfaces.Crud;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class User implements Crud<User, String> {
    private static final List<User> STORE = new ArrayList<>();

    private String username;
    private String password;
    private String role;

    // Construcor default
    public User() {}

    // Convenience constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Cac ham set cho cac thuoc tinh
    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(String role){
        this.role = role;
    }
    
    // Cac ham get
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }

    // CRUD implementation (su dung username lam ID)
    @Override
    public User create(User entity) {
        // if (entity == null || entity.getUsername() == null) {
        //     return null;
        // }
        // // Khong them trung username
        // if (readById(entity.getUsername()).isPresent()) {
        //     return null;
        // }
        String username;
        String password1;
        String password2;
        String role;
        String r;
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap username: ");
        username = sc.nextLine();
        entity.setUsername(username);
        System.out.print("Nhap mat khau: ");
        password1 = sc.next();
        System.out.print("Xac nhan lai mat khau: ");
        password2 = sc.next();
        while(!password1.equals(password2)){
            System.out.println("Mat khau khong trung khop");
            System.out.print("Xac nhan lai mat khau: ");
            password2 = sc.next();
        }
        entity.setPassword(password2);
        System.out.println("Chon chuc vu: ");
        System.out.println("1. Quan ly thanh vien");
        System.out.println("2. Quan ly tai chinh");
        System.out.println("3. Quan ly su kien");
        r = sc.next();
        switch (r) {
            case "1":
                role = "qltv";
                entity.setRole(role);
                break;
            case "2":
                role = "qltc";
                entity.setRole(role);
                break;
            case "3":
                role = "qlsk";
                entity.setRole(role);
                break;
            default:
                break;
        }
        System.out.println("Tao tai khoan thanh cong");
        return entity;
    }

    @Override
    public Optional<User> readById(String id) {
        if (id == null) return Optional.empty();
        for (User u : STORE) {
            if (id.equals(u.getUsername())) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> readAll() {
        return Collections.unmodifiableList(new ArrayList<>(STORE));
    }

    @Override
    public User update(User entity) {
        if (entity == null || entity.getUsername() == null) {
            return null;
        }
        for (int i = 0; i < STORE.size(); i++) {
            if (entity.getUsername().equals(STORE.get(i).getUsername())) {
                STORE.set(i, entity);
                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) return false;
        return STORE.removeIf(u -> id.equals(u.getUsername()));
    }

    @Override
    public boolean delete(User entity) {
        if (entity == null || entity.getUsername() == null) return false;
        return deleteById(entity.getUsername());
    }
}