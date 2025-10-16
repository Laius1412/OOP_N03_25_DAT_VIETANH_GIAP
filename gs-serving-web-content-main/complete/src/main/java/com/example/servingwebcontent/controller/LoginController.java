package com.example.servingwebcontent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, Model model) {
        boolean ok = username != null && !username.isBlank() && password != null && !password.isBlank();
        if (!ok) {
            model.addAttribute("error", "Vui lòng nhập đủ thông tin đăng nhập.");
            return "login";
        }
        model.addAttribute("name", username);
        return "greeting";
    }
}


