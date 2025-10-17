package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("people", memberService.findAll());
        return "members/list";
    }

    @GetMapping("/new")
    public String createForm() {
        return "members/form";
    }

    @PostMapping
    public String create(@RequestParam String name,
                         @RequestParam String gender,
                         @RequestParam String dob,
                         @RequestParam String address,
                         @RequestParam String phone) {
        memberService.add(new Member(name, gender, LocalDate.parse(dob), address, phone));
        return "redirect:/members";
    }

    @GetMapping("/{name}/edit")
    public String editForm(@PathVariable String name, Model model) {
        Member member = memberService.findByName(name).orElse(null);
        if (member == null) {
            return "redirect:/members";
        }
        model.addAttribute("p", member);
        return "members/edit";
    }

    @PostMapping("/{name}")
    public String update(@PathVariable String name,
                         @RequestParam String newName,
                         @RequestParam String gender,
                         @RequestParam String dob,
                         @RequestParam String address,
                         @RequestParam String phone) {
        memberService.update(name, new Member(newName, gender, LocalDate.parse(dob), address, phone));
        return "redirect:/members";
    }

    @PostMapping("/{name}/delete")
    public String delete(@PathVariable String name) {
        memberService.deleteByName(name);
        return "redirect:/members";
    }
}


