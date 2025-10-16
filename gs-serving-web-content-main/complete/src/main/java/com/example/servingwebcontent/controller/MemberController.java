package com.example.servingwebcontent.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.model.Person;
import com.example.servingwebcontent.service.PersonService;

@Controller
public class MemberController {
    private final PersonService personService;

    public MemberController(PersonService personService) { this.personService = personService; }

    @GetMapping("/members")
    public String list(Model model) {
        model.addAttribute("people", personService.findAll());
        return "members/list";
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/form";
    }

    @PostMapping("/members")
    public String create(@RequestParam String name, @RequestParam String gender,
                         @RequestParam String dob, @RequestParam(required=false) String address,
                         @RequestParam(required=false, defaultValue="0") int phone) {
        Person p = new Person();
        p.setName(name);
        p.setGender(gender);
        p.setDob(LocalDate.parse(dob));
        p.setAddress(address);
        p.setPhone(phone);
        personService.create(p);
        return "redirect:/members";
    }

    @GetMapping("/members/{name}/edit")
    public String editForm(@PathVariable String name, Model model) {
        model.addAttribute("p", personService.findById(name));
        return "members/edit";
    }

    @PostMapping("/members/{name}")
    public String update(@PathVariable String name,
                         @RequestParam(required=false) String newName,
                         @RequestParam(required=false) String gender,
                         @RequestParam(required=false) String dob,
                         @RequestParam(required=false) String address,
                         @RequestParam(required=false, defaultValue="0") int phone) {
        Person data = new Person();
        data.setName(newName);
        data.setGender(gender);
        if (dob != null && !dob.isBlank()) data.setDob(LocalDate.parse(dob));
        data.setAddress(address);
        data.setPhone(phone);
        personService.update(name, data);
        return "redirect:/members";
    }

    @PostMapping("/members/{name}/delete")
    public String delete(@PathVariable String name) {
        personService.delete(name);
        return "redirect:/members";
    }
}


