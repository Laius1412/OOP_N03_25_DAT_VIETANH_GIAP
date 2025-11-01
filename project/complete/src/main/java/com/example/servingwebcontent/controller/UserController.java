package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.model.Role;
import com.example.servingwebcontent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    // Trang quản lý user cho admin - hiển thị danh sách user
    @GetMapping("/admin/users")
    public String adminUsers(Model model, HttpSession session) {
        // Kiểm tra quyền admin
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return "redirect:/login";
        }
        
        List<User> users = userRepository.findAll();
        long totalUsers = userRepository.count();
        long adminCount = userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.ADMIN)
                .count();
        long managerCount = userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.MEMBER_MANAGER || 
                               user.getRole() == Role.EVENT_MANAGER || 
                               user.getRole() == Role.FINANCE_MANAGER)
                .count();
        long userCount = userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.USER)
                .count();
        
        model.addAttribute("users", users);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("managerCount", managerCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("user", currentUser);
        
        return "admin/users";
    }
    
    // Thêm user mới (Admin)
    @PostMapping("/admin/users")
    public String addUser(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String phone,
                         @RequestParam String role,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        
        // Kiểm tra quyền admin
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return "redirect:/login";
        }
        
        try {
            // Kiểm tra username đã tồn tại chưa
            if (userRepository.findByUsername(username).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Username đã tồn tại!");
                return "redirect:/admin/users";
            }
            
            // Kiểm tra email đã tồn tại chưa
            if (userRepository.findByEmail(email).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Email đã tồn tại!");
                return "redirect:/admin/users";
            }
            
            // Tạo user mới
            User newUser = new User(username, password, Role.fromCode(role), name, email, phone);
            userRepository.save(newUser);
            
            redirectAttributes.addFlashAttribute("success", "Thêm user thành công!");
            return "redirect:/admin/users";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm user: " + e.getMessage());
            return "redirect:/admin/users";
        }
    }
    
    // Lấy thông tin user để edit (API)
    @GetMapping("/admin/users/{id}")
    @ResponseBody
    public User getUserById(@PathVariable Long id, HttpSession session) {
        // Kiểm tra quyền admin
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return null;
        }
        
        return userRepository.findById(id).orElse(null);
    }
    
    // Cập nhật user
    @PostMapping("/admin/users/update")
    public String updateUser(@RequestParam Long id,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String role,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        
        // Kiểm tra quyền admin
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return "redirect:/login";
        }
        
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "User không tồn tại!");
                return "redirect:/admin/users";
            }
            
            User user = userOptional.get();
            
            // Kiểm tra username đã tồn tại chưa (trừ user hiện tại)
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                redirectAttributes.addFlashAttribute("error", "Username đã tồn tại!");
                return "redirect:/admin/users";
            }
            
            // Kiểm tra email đã tồn tại chưa (trừ user hiện tại)
            existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                redirectAttributes.addFlashAttribute("error", "Email đã tồn tại!");
                return "redirect:/admin/users";
            }
            
            // Cập nhật thông tin
            user.setUsername(username);
            if (!password.isEmpty()) {
                user.setPassword(password);
            }
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setRole(Role.fromCode(role));
            
            userRepository.save(user);
            
            redirectAttributes.addFlashAttribute("success", "Cập nhật user thành công!");
            return "redirect:/admin/users";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật user: " + e.getMessage());
            return "redirect:/admin/users";
        }
    }
    
    // Xóa user
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        // Kiểm tra quyền admin
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            return "redirect:/login";
        }
        
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "User không tồn tại!");
                return "redirect:/admin/users";
            }
            
            User user = userOptional.get();
            
            // Không cho phép xóa admin cuối cùng
            if (user.getRole() == Role.ADMIN) {
                long adminCount = userRepository.findAll().stream()
                        .filter(u -> u.getRole() == Role.ADMIN)
                        .count();
                if (adminCount <= 1) {
                    redirectAttributes.addFlashAttribute("error", "Không thể xóa admin cuối cùng!");
                    return "redirect:/admin/users";
                }
            }
            
            userRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xóa user thành công!");
            return "redirect:/admin/users";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa user: " + e.getMessage());
            return "redirect:/admin/users";
        }
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
