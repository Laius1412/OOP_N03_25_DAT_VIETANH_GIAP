package com.example.servingwebcontent.service;

import com.example.servingwebcontent.Model.User;
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
            User admin = new User("admin", "admin123", "ADMIN", "Quản trị viên", "admin@family.com", "0123456789");
            userRepository.save(admin);
            
            // Tạo user thường
            User user = new User("user", "user123", "USER", "Người dùng", "user@family.com", "0987654321");
            userRepository.save(user);
            
            System.out.println("Đã tạo user mặc định:");
            System.out.println("Admin: admin/admin123");
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
