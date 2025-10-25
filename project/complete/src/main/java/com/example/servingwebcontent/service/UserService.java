package com.example.servingwebcontent.service;

import com.example.servingwebcontent.Model.User;
import com.example.servingwebcontent.Model.Role;
import com.example.servingwebcontent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Tạo user mặc định nếu chưa có
    public void createDefaultUsers() {
        // Kiểm tra xem đã có user nào chưa
        if (userRepository.count() == 0) {
            // Tạo admin user
            User admin = new User("admin", "admin123", Role.ADMIN, "Quản trị viên", "admin@family.com", "0123456789");
            userRepository.save(admin);
            
            // Tạo quản lý thành viên
            User memberManager = new User("member_manager", "member123", Role.MEMBER_MANAGER, "Quản lý thành viên", "member@family.com", "0123456788");
            userRepository.save(memberManager);
            
            // Tạo quản lý sự kiện
            User eventManager = new User("event_manager", "event123", Role.EVENT_MANAGER, "Quản lý sự kiện", "event@family.com", "0123456787");
            userRepository.save(eventManager);
            
            // Tạo quản lý tài chính
            User financeManager = new User("finance_manager", "finance123", Role.FINANCE_MANAGER, "Quản lý tài chính", "finance@family.com", "0123456786");
            userRepository.save(financeManager);
            
            // Tạo user thường
            User user = new User("user", "user123", Role.USER, "Người dùng", "user@family.com", "0987654321");
            userRepository.save(user);
            
            System.out.println("Đã tạo user mặc định:");
            System.out.println("Admin: admin/admin123");
            System.out.println("Quản lý thành viên: member_manager/member123");
            System.out.println("Quản lý sự kiện: event_manager/event123");
            System.out.println("Quản lý tài chính: finance_manager/finance123");
            System.out.println("User: user/user123");
        }
    }
    
    // Lấy tất cả user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Tìm user theo username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // Tìm user theo username và password
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
    
    // Lưu user mới
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    // Xóa user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
