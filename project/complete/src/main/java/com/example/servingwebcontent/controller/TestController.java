package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.Model.User;
import com.example.servingwebcontent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private UserRepository userRepository;
    
    // Test endpoint để thêm user
    @PostMapping("/add-user")
    public String addTestUser() {
        try {
            User user = new User("Test User", "test@example.com", "0123456789");
            userRepository.save(user);
            return "User added successfully! ID: " + user.getId();
        } catch (Exception e) {
            return "Error adding user: " + e.getMessage();
        }
    }
    
    // Test endpoint để đếm user
    @GetMapping("/count")
    public String getUserCount() {
        try {
            long count = userRepository.count();
            return "Total users in database: " + count;
        } catch (Exception e) {
            return "Error counting users: " + e.getMessage();
        }
    }
    
    // Test endpoint để lấy tất cả user
    @GetMapping("/all-users")
    public String getAllUsers() {
        try {
            return userRepository.findAll().toString();
        } catch (Exception e) {
            return "Error getting users: " + e.getMessage();
        }
    }
}
