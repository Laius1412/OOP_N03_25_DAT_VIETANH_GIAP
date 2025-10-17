package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", financeService.findAll());
        model.addAttribute("balance", financeService.getBalance());
        return "finance/list";
    }

    @PostMapping
    public String add(@RequestParam FinanceItem.Type type,
                      @RequestParam BigDecimal amount,
                      @RequestParam String description,
                      @RequestParam String date) {
        financeService.add(type, amount, description, LocalDate.parse(date));
        return "redirect:/finance";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        financeService.deleteById(id);
        return "redirect:/finance";
    }
}


