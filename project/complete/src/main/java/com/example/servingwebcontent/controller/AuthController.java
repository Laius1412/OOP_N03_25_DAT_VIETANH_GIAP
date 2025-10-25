package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.Model.User;
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
        return "login";
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
            return "login";
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
    
    // Trang chủ mặc định
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
}
