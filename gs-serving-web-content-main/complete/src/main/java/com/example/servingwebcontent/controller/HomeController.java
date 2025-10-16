package com.example.servingwebcontent.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.servingwebcontent.model.Person;
import com.example.servingwebcontent.service.PersonService;

@Controller
public class HomeController {
    private final PersonService personService;

    public HomeController(PersonService personService) {
        this.personService = personService;
        if (personService.findAll().isEmpty()) {
            seedData();
        }
    }

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("people", personService.findAll());
        return "home";
    }

    private void seedData() {
        Person a = new Person("Đạt", "Nam", LocalDate.of(2000, 5, 10), null, 123456789, "Hà Nội");
        Person b = new Person("Việt Anh", "Nam", LocalDate.of(2001, 7, 20), null, 987654321, "Hải Phòng");
        Person c = new Person("Giáp", "Nam", LocalDate.of(2002, 3, 15), null, 555666777, "Nam Định");
        personService.create(a);
        personService.create(b);
        personService.create(c);
    }
}


