package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.model.Role;
import com.example.servingwebcontent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    
    // Xử lý đăng nhập
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, password);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Lưu thông tin user vào session
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setAttribute("name", user.getName());
            
            redirectAttributes.addFlashAttribute("success", "Đăng nhập thành công!");
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return "auth/login";
        }
    }
    
    // Trang chủ
    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        // Kiểm tra đăng nhập
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "home";
    }
    
    // Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }
    
    // Xử lý đăng ký
    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String name,
                          @RequestParam String email,
                          @RequestParam String phone,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        
        try {
            // Kiểm tra username đã tồn tại chưa
            if (userRepository.findByUsername(username).isPresent()) {
                model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
                return "auth/register";
            }
            
            // Kiểm tra email đã tồn tại chưa
            if (userRepository.findByEmail(email).isPresent()) {
                model.addAttribute("error", "Email đã tồn tại!");
                return "auth/register";
            }
            
            // Tạo user mới với role USER
            User newUser = new User(username, password, Role.USER, name, email, phone);
            userRepository.save(newUser);
            
            redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.");
            return "redirect:/login";
            
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi đăng ký: " + e.getMessage());
            return "auth/register";
        }
    }
    
    // Trang chủ mặc định
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
}
