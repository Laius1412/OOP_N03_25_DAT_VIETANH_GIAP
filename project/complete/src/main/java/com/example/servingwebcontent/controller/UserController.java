package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.Model.User;
import com.example.servingwebcontent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    // Trang chủ - hiển thị danh sách user và form thêm user
    @GetMapping("/")
    public String home(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", new User());
        model.addAttribute("totalUsers", userRepository.count());
        return "home";
    }
    
    // Thêm user mới
    @PostMapping("/users")
    public String addUser(@ModelAttribute User user, Model model) {
        try {
            // Kiểm tra email đã tồn tại chưa
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                model.addAttribute("error", "Email đã tồn tại!");
                model.addAttribute("users", userRepository.findAll());
                model.addAttribute("totalUsers", userRepository.count());
                return "home";
            }
            
            userRepository.save(user);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm user: " + e.getMessage());
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("totalUsers", userRepository.count());
            return "home";
        }
    }
    
    // Xóa user
    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }
    
    // API endpoint để test kết nối database
    @GetMapping("/api/test-connection")
    @ResponseBody
    public String testConnection() {
        try {
            long userCount = userRepository.count();
            return "Kết nối database thành công! Số lượng user: " + userCount;
        } catch (Exception e) {
            return "Lỗi kết nối database: " + e.getMessage();
        }
    }
    
    // API endpoint để lấy danh sách user dưới dạng JSON
    @GetMapping("/api/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
