package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.Model.User;

@Controller
public class GreetingController {

	

	@GetMapping("/greeting")
	public String User(Model model) {
		model.addAttribute("User", new User());

		return "useradd";
	}

	@GetMapping("/test")
	public String test(@RequestParam(name = "test", required = false, defaultValue = "test") String test, Model model) {

		model.addAttribute("test", test);

		System.out.println("t:" + test);
		return "test";
	}

}
