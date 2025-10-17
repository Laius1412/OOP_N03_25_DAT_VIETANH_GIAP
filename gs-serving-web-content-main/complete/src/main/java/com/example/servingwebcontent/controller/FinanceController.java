package com.example.servingwebcontent.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.servingwebcontent.model.Transaction;
import com.example.servingwebcontent.service.TransactionService;

@Controller
public class FinanceController {
    private final TransactionService transactionService;

    public FinanceController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/finance")
    public String list(Model model) {
        model.addAttribute("items", transactionService.findAll());
        model.addAttribute("balance", transactionService.getBalance());
        return "finance/list";
    }

    @PostMapping("/finance")
    public String create(@RequestParam String type,
                         @RequestParam String amount,
                         @RequestParam String description,
                         @RequestParam String eventName,
                         @RequestParam String payerName,
                         @RequestParam String date) {
        Transaction t = new Transaction();
        t.setType(Transaction.Type.valueOf(type));
        t.setAmount(new BigDecimal(amount));
        t.setDescription(description);
        t.setEventName(eventName);
        t.setPayerName(payerName);
        t.setDate(LocalDate.parse(date));
        transactionService.save(t);
        return "redirect:/finance";
    }

    @GetMapping("/finance/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("t", transactionService.findById(id));
        return "finance/edit";
    }

    @PostMapping("/finance/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String type,
                         @RequestParam String amount,
                         @RequestParam String description,
                         @RequestParam String eventName,
                         @RequestParam String payerName,
                         @RequestParam String date) {
        Transaction data = new Transaction();
        data.setType(Transaction.Type.valueOf(type));
        data.setAmount(new BigDecimal(amount));
        data.setDescription(description);
        data.setEventName(eventName);
        data.setPayerName(payerName);
        data.setDate(LocalDate.parse(date));
        transactionService.update(id, data);
        return "redirect:/finance";
    }

    @PostMapping("/finance/{id}/delete")
    public String delete(@PathVariable Long id) {
        transactionService.delete(id);
        return "redirect:/finance";
    }
}


