package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.Model.FinanceManagement.*;
import com.example.servingwebcontent.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/finance")
public class FinanceController {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private BudgetRepository budgetRepository;
    
    @Autowired
    private FinancialReportRepository financialReportRepository;
    
    @Autowired
    private FinanceCategoryRepository financeCategoryRepository;
    
    // Dashboard tài chính dòng họ
    @GetMapping("")
    public String financeDashboard(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        
        // Lấy thống kê tổng quan
        List<Transaction> recentTransactions = transactionRepository.findTop10ByCreatedByIdOrderByTransactionDateDesc(userId);
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();
        
        // Tính thu nhập và chi tiêu trong tháng
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        
        List<Transaction> monthlyTransactions = transactionRepository.findByCreatedByIdAndTransactionDateBetween(
            userId, startOfMonth, endOfMonth);
        
        BigDecimal monthlyIncome = monthlyTransactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.INCOME)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal monthlyExpense = monthlyTransactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.EXPENSE)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        model.addAttribute("recentTransactions", recentTransactions);
        model.addAttribute("categories", categories);
        model.addAttribute("monthlyIncome", monthlyIncome);
        model.addAttribute("monthlyExpense", monthlyExpense);
        model.addAttribute("netIncome", monthlyIncome.subtract(monthlyExpense));
        
        return "FinanceManagement/dashboard";
    }
    
    // Quản lý danh mục
    @GetMapping("/categories")
    public String categoriesList(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();
        
        model.addAttribute("categories", categories);
        model.addAttribute("transactionTypes", TransactionType.values());
        
        return "FinanceManagement/categories";
    }
    
    @GetMapping("/categories/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new FinanceCategory());
        model.addAttribute("transactionTypes", TransactionType.values());
        return "FinanceManagement/category-form";
    }
    
    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute FinanceCategory category, RedirectAttributes redirectAttributes) {
        financeCategoryRepository.save(category);
        redirectAttributes.addFlashAttribute("success", "Danh mục đã được thêm thành công!");
        return "redirect:/finance/categories";
    }
    
    @GetMapping("/categories/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            model.addAttribute("category", categoryOpt.get());
            model.addAttribute("transactionTypes", TransactionType.values());
            return "FinanceManagement/category-form";
        }
        return "redirect:/finance/categories";
    }
    
    @PostMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute FinanceCategory category, RedirectAttributes redirectAttributes) {
        Optional<FinanceCategory> existingCategoryOpt = financeCategoryRepository.findById(id);
        if (existingCategoryOpt.isPresent()) {
            FinanceCategory existingCategory = existingCategoryOpt.get();
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());
            existingCategory.setType(category.getType());
            existingCategory.setUpdatedAt(LocalDateTime.now());
            
            financeCategoryRepository.save(existingCategory);
            redirectAttributes.addFlashAttribute("success", "Danh mục đã được cập nhật thành công!");
        }
        return "redirect:/finance/categories";
    }
    
    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            FinanceCategory category = categoryOpt.get();
            category.setIsActive(false);
            category.setUpdatedAt(LocalDateTime.now());
            financeCategoryRepository.save(category);
            redirectAttributes.addFlashAttribute("success", "Danh mục đã được xóa thành công!");
        }
        return "redirect:/finance/categories";
    }
    
    // Quản lý giao dịch
    @GetMapping("/transactions")
    public String transactionsList(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        List<Transaction> transactions = transactionRepository.findByCreatedByIdOrderByTransactionDateDesc(userId);
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();
        
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        
        return "FinanceManagement/transactions";
    }
    
    @GetMapping("/transactions/add")
    public String addTransactionForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();
        
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("categories", categories);
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        
        return "FinanceManagement/transaction-form";
    }
    
    @PostMapping("/transactions/add")
    public String addTransaction(@ModelAttribute Transaction transaction, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        transaction.setCreatedById(userId);
        
        transactionRepository.save(transaction);
        redirectAttributes.addFlashAttribute("success", "Giao dịch đã được thêm thành công!");
        
        return "redirect:/finance/transactions";
    }
    
    // Quản lý ngân sách
    @GetMapping("/budgets")
    public String budgetsList(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        List<Budget> budgets = budgetRepository.findByCreatedByIdOrderByCreatedAtDesc(userId);
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();
        
        model.addAttribute("budgets", budgets);
        model.addAttribute("categories", categories);
        model.addAttribute("budgetStatuses", BudgetStatus.values());
        
        return "FinanceManagement/budgets";
    }
    
    @GetMapping("/budgets/add")
    public String addBudgetForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();
        
        model.addAttribute("budget", new Budget());
        model.addAttribute("categories", categories);
        
        return "FinanceManagement/budget-form";
    }
    
    @PostMapping("/budgets/add")
    public String addBudget(@ModelAttribute Budget budget, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        budget.setCreatedById(userId);
        
        budgetRepository.save(budget);
        redirectAttributes.addFlashAttribute("success", "Ngân sách đã được thêm thành công!");
        
        return "redirect:/finance/budgets";
    }
    
    // Báo cáo tài chính
    @GetMapping("/reports")
    public String reportsList(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        List<FinancialReport> reports = financialReportRepository.findByGeneratedByIdOrderByGeneratedAtDesc(userId);
        
        model.addAttribute("reports", reports);
        model.addAttribute("reportTypes", ReportType.values());
        
        return "FinanceManagement/reports";
    }
    
    @GetMapping("/reports/generate")
    public String generateReportForm(Model model) {
        model.addAttribute("reportTypes", ReportType.values());
        return "FinanceManagement/report-form";
    }
    
    @PostMapping("/reports/generate")
    public String generateReport(@RequestParam String reportName,
                               @RequestParam ReportType reportType,
                               @RequestParam LocalDate startDate,
                               @RequestParam LocalDate endDate,
                               HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        Long userId = ((com.example.servingwebcontent.Model.User) session.getAttribute("user")).getId();
        
        // Tính toán dữ liệu báo cáo
        List<Transaction> transactions = transactionRepository.findByCreatedByIdAndTransactionDateBetween(
            userId, startDate, endDate);
        
        BigDecimal totalIncome = transactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.INCOME)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalExpense = transactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.EXPENSE)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        FinancialReport report = new FinancialReport(reportName, reportType, startDate, endDate,
            totalIncome, totalExpense, transactions.size(), userId, "");
        
        financialReportRepository.save(report);
        redirectAttributes.addFlashAttribute("success", "Báo cáo đã được tạo thành công!");
        
        return "redirect:/finance/reports";
    }
}
