package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.FinanceManagement.*;
import com.example.servingwebcontent.repository.*;
import com.example.servingwebcontent.model.PersonManagement.Person;
import com.example.servingwebcontent.model.Role;
import com.example.servingwebcontent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.transaction.annotation.Transactional;

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
    private FinancialReportRepository financialReportRepository;
    
    @Autowired
    private FinanceCategoryRepository financeCategoryRepository;

    @Autowired
    private PersonRepository personRepository;
    
    // Dashboard tài chính dòng họ
    @GetMapping("")
    public String financeDashboard(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        // Lấy thống kê tổng quan (mọi user đều xem được toàn bộ)
        List<Transaction> recentTransactions = transactionRepository.findTop10ByOrderByTransactionDateDesc();
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();
        
        // Tính tổng thu/chi trên tất cả giao dịch
        List<Transaction> allTransactions = transactionRepository.findAll();
        BigDecimal monthlyIncome = allTransactions.stream()
            .filter(t -> t.getTransactionType() == TransactionType.INCOME)
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal monthlyExpense = allTransactions.stream()
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
        
        Long userId = ((com.example.servingwebcontent.model.User) session.getAttribute("user")).getId();
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();
        
        model.addAttribute("categories", categories);
        model.addAttribute("transactionTypes", TransactionType.values());
        
        return "FinanceManagement/categories";
    }

    // Chi tiết danh mục + danh sách giao dịch theo danh mục
    @GetMapping("/categories/{id}")
    public String categoryDetail(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            return "redirect:/finance/categories";
        }

        // Mọi user đều xem được toàn bộ giao dịch theo danh mục
        List<Transaction> transactions = transactionRepository.findByCategoryIdOrderByTransactionDateDesc(id);

        FinanceCategory category = categoryOpt.get();
        model.addAttribute("category", category);
        model.addAttribute("transactions", transactions);
        // tổng tiền của danh mục (thu hoặc chi)
        java.math.BigDecimal totalAmount = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("totalLabel", category.getType() == TransactionType.INCOME ?
                "Tổng số tiền đã nộp" : "Tổng khoản chi");
        return "FinanceManagement/category-detail";
    }
    
    @GetMapping("/categories/add")
    public String addCategoryForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            return "redirect:/finance/categories";
        }
        model.addAttribute("category", new FinanceCategory());
        model.addAttribute("transactionTypes", TransactionType.values());
        return "FinanceManagement/category-form";
    }
    
    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute FinanceCategory category, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền thêm danh mục.");
            return "redirect:/finance/categories";
        }
        try {
            if (category.getIsActive() == null) category.setIsActive(true);
            financeCategoryRepository.save(category);
            redirectAttributes.addFlashAttribute("success", "Danh mục đã được thêm thành công!");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Tên danh mục đã tồn tại hoặc dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
            return "redirect:/finance/categories/add";
        }
        return "redirect:/finance/categories";
    }
    
    @GetMapping("/categories/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            return "redirect:/finance/categories";
        }
        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            model.addAttribute("category", categoryOpt.get());
            model.addAttribute("transactionTypes", TransactionType.values());
            return "FinanceManagement/category-form";
        }
        return "redirect:/finance/categories";
    }
    
    @PostMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute FinanceCategory category, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền sửa danh mục.");
            return "redirect:/finance/categories";
        }
        Optional<FinanceCategory> existingCategoryOpt = financeCategoryRepository.findById(id);
        if (existingCategoryOpt.isPresent()) {
            FinanceCategory existingCategory = existingCategoryOpt.get();
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());
            existingCategory.setType(category.getType());
            existingCategory.setUpdatedAt(LocalDateTime.now());
            try {
                financeCategoryRepository.save(existingCategory);
                redirectAttributes.addFlashAttribute("success", "Danh mục đã được cập nhật thành công!");
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                redirectAttributes.addFlashAttribute("error", "Tên danh mục đã tồn tại hoặc dữ liệu không hợp lệ.");
                return "redirect:/finance/categories/edit/" + id;
            }
        }
        return "redirect:/finance/categories";
    }
    
    @GetMapping("/categories/delete/{id}")
    @Transactional
    public String deleteCategory(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xóa danh mục.");
            return "redirect:/finance/categories";
        }
        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            // Xóa tất cả giao dịch thuộc danh mục trước
            long deleted = transactionRepository.deleteByCategoryId(id);
            // Sau đó xóa hẳn danh mục
            financeCategoryRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Đã xóa danh mục và " + deleted + " giao dịch liên quan.");
        }
        return "redirect:/finance/categories";
    }

    // Quản lý giao dịch
    @GetMapping("/transactions")
    public String transactionsList(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        
        // Mọi user đều xem được toàn bộ giao dịch
        List<Transaction> transactions = transactionRepository.findAllByOrderByTransactionDateDesc();
        List<FinanceCategory> categories = financeCategoryRepository.findByIsActiveTrue();

        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());

        return "FinanceManagement/transactions";
    }

    // Danh sách giao dịch gần đây (đơn giản, từ mới đến cũ)
    @GetMapping("/transactions/recent")
    public String transactionsRecent(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        Long userId = currentUser.getId();
        boolean isAdminView = currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER;
        List<Transaction> transactions = isAdminView
                ? transactionRepository.findAllByOrderByTransactionDateDesc()
                : transactionRepository.findByCreatedByIdOrderByTransactionDateDesc(userId);
        model.addAttribute("transactions", transactions);
        return "FinanceManagement/transactions-recent";
    }

    // Chi tiết giao dịch
    @GetMapping("/transactions/{id}")
    public String transactionDetail(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        Long userId = ((com.example.servingwebcontent.model.User) session.getAttribute("user")).getId();
        Optional<Transaction> txOpt = transactionRepository.findById(id);
        if (txOpt.isEmpty()) {
            return "redirect:/finance/transactions";
        }
        Transaction tx = txOpt.get();
        // Chỉ cho phép xem giao dịch của chính user
        if (tx.getCreatedById() != null && !tx.getCreatedById().equals(userId)) {
            return "redirect:/finance/transactions";
        }
        model.addAttribute("transaction", tx);
        return "FinanceManagement/transaction-detail";
    }

    // Thêm giao dịch trong một danh mục cụ thể
    @GetMapping("/categories/{id}/transactions/add")
    public String addTransactionInCategoryForm(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        // Chỉ ADMIN hoặc FINANCE_MANAGER mới được thêm
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            return "redirect:/finance/categories/" + id;
        }

        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            return "redirect:/finance/categories";
        }

        Transaction tx = new Transaction();
        tx.setCategory(categoryOpt.get());
        tx.setTransactionDate(LocalDate.now());

        model.addAttribute("category", categoryOpt.get());
        model.addAttribute("transaction", tx);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("persons", personRepository.findAll());
        return "FinanceManagement/category-transaction-form";
    }

    @PostMapping("/categories/{id}/transactions/add")
    public String addTransactionInCategory(@PathVariable Long id,
                                           @ModelAttribute Transaction transaction,
                                           @RequestParam(name = "personId", required = false) Long personId,
                                           HttpSession session,
                                           RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền thêm giao dịch.");
            return "redirect:/finance/categories/" + id;
        }

        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            return "redirect:/finance/categories";
        }

        Long userId = ((com.example.servingwebcontent.model.User) session.getAttribute("user")).getId();
        transaction.setCreatedById(userId);
        transaction.setCategory(categoryOpt.get());
        transaction.setTransactionType(categoryOpt.get().getType());

        if (personId != null) {
            personRepository.findById(personId).ifPresent(p -> {
                transaction.setContributor(p);
                if (transaction.getContributorName() == null || transaction.getContributorName().isBlank()) {
                    transaction.setContributorName(p.getName());
                }
                if (transaction.getContributorPhone() == null || transaction.getContributorPhone().isBlank()) {
                    transaction.setContributorPhone(p.getPhone());
                }
            });
        }

        if (transaction.getTransactionDate() == null) {
            transaction.setTransactionDate(LocalDate.now());
        }
        transactionRepository.save(transaction);
        redirectAttributes.addFlashAttribute("success", "Đã thêm giao dịch vào danh mục!");
        return "redirect:/finance/categories/" + id;
    }

    // Chỉnh sửa giao dịch trong danh mục
    @GetMapping("/categories/{categoryId}/transactions/edit/{transactionId}")
    public String editTransactionInCategoryForm(@PathVariable Long categoryId,
                                                @PathVariable Long transactionId,
                                                HttpSession session,
                                                Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            return "redirect:/finance/categories/" + categoryId;
        }
        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(categoryId);
        Optional<Transaction> txOpt = transactionRepository.findById(transactionId);
        if (categoryOpt.isEmpty() || txOpt.isEmpty()) {
            return "redirect:/finance/categories";
        }
        Transaction tx = txOpt.get();
        model.addAttribute("category", categoryOpt.get());
        model.addAttribute("transaction", tx);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("persons", personRepository.findAll());
        return "FinanceManagement/category-transaction-form";
    }

    @PostMapping("/categories/{categoryId}/transactions/edit/{transactionId}")
    public String editTransactionInCategory(@PathVariable Long categoryId,
                                            @PathVariable Long transactionId,
                                            @ModelAttribute Transaction form,
                                            @RequestParam(name = "personId", required = false) Long personId,
                                            HttpSession session,
                                            RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền sửa giao dịch.");
            return "redirect:/finance/categories/" + categoryId;
        }
        Optional<Transaction> txOpt = transactionRepository.findById(transactionId);
        Optional<FinanceCategory> categoryOpt = financeCategoryRepository.findById(categoryId);
        if (txOpt.isEmpty() || categoryOpt.isEmpty()) {
            return "redirect:/finance/categories";
        }
        Transaction tx = txOpt.get();
        // Cập nhật các trường cho phép
        tx.setTitle(form.getTitle());
        tx.setDescription(form.getDescription());
        tx.setAmount(form.getAmount());
        tx.setPaymentMethod(form.getPaymentMethod());
        if (form.getTransactionDate() != null) {
            tx.setTransactionDate(form.getTransactionDate());
        }
        tx.setNotes(form.getNotes());
        tx.setReceiptNumber(form.getReceiptNumber());

        if (personId != null) {
            personRepository.findById(personId).ifPresent(p -> {
                tx.setContributor(p);
                // đồng bộ hiển thị tên/điện thoại nếu trống
                if (tx.getContributorName() == null || tx.getContributorName().isBlank()) {
                    tx.setContributorName(p.getName());
                }
                if (tx.getContributorPhone() == null || tx.getContributorPhone().isBlank()) {
                    tx.setContributorPhone(p.getPhone());
                }
            });
        } else {
            tx.setContributor(null);
        }

        transactionRepository.save(tx);
        redirectAttributes.addFlashAttribute("success", "Đã cập nhật giao dịch!");
        return "redirect:/finance/categories/" + categoryId;
    }

    // Xóa giao dịch khỏi danh mục
    @GetMapping("/categories/{categoryId}/transactions/delete/{transactionId}")
    public String deleteTransactionInCategory(@PathVariable Long categoryId,
                                              @PathVariable Long transactionId,
                                              HttpSession session,
                                              RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xóa giao dịch.");
            return "redirect:/finance/categories/" + categoryId;
        }
        transactionRepository.deleteById(transactionId);
        redirectAttributes.addFlashAttribute("success", "Đã xóa giao dịch!");
        return "redirect:/finance/categories/" + categoryId;
    }
    
    @GetMapping("/transactions/add")
    public String addTransactionForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        // Chỉ ADMIN hoặc FINANCE_MANAGER mới được thêm
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            return "redirect:/finance/transactions";
        }
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
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền thêm giao dịch.");
            return "redirect:/finance/transactions";
        }
        Long userId = currentUser.getId();
        transaction.setCreatedById(userId);
        
        transactionRepository.save(transaction);
        redirectAttributes.addFlashAttribute("success", "Giao dịch đã được thêm thành công!");
        
        return "redirect:/finance/transactions";
    }
    

    
    // Báo cáo tài chính
    @GetMapping("/reports")
    public String reportsList(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        // Mọi user đều xem được toàn bộ báo cáo
        List<FinancialReport> reports = financialReportRepository.findAllByOrderByGeneratedAtDesc();
        
        model.addAttribute("reports", reports);
        model.addAttribute("reportTypes", ReportType.values());
        
        return "FinanceManagement/reports";
    }

    // Compat: các link cũ từ home.html dùng /finance/report
    @GetMapping("/report")
    public String reportRedirect() {
        return "redirect:/finance/reports";
    }
    
    @GetMapping("/reports/generate")
    public String generateReportForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            return "redirect:/finance/reports";
        }
        model.addAttribute("reportTypes", ReportType.values());
        // Gợi ý mặc định để form hiển thị ngay
        model.addAttribute("today", LocalDate.now());
        return "FinanceManagement/report-form";
    }

    // Alias URL để tiện điều hướng nếu cần
    @GetMapping("/reports/new")
    public String generateReportFormAlias(HttpSession session, Model model) {
        return generateReportForm(session, model);
    }

    // Compat alias cho đường dẫn đơn
    @GetMapping("/report/generate")
    public String generateReportFormAlias2(HttpSession session, Model model) {
        return generateReportForm(session, model);
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
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền tạo báo cáo.");
            return "redirect:/finance/reports";
        }
        Long userId = currentUser.getId();
        
        // Tính toán dữ liệu báo cáo
        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);
        
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

    // Chi tiết báo cáo: xem danh sách thu/chi trong khoảng thời gian của báo cáo
    @GetMapping("/reports/{id}")
    public String reportDetail(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        Optional<FinancialReport> reportOpt = financialReportRepository.findById(id);
        if (reportOpt.isEmpty()) {
            return "redirect:/finance/reports";
        }
        FinancialReport report = reportOpt.get();
        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(report.getStartDate(), report.getEndDate());

        List<Transaction> incomeTransactions = transactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.INCOME)
                .sorted((a,b) -> b.getTransactionDate().compareTo(a.getTransactionDate()))
                .toList();
        List<Transaction> expenseTransactions = transactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.EXPENSE)
                .sorted((a,b) -> b.getTransactionDate().compareTo(a.getTransactionDate()))
                .toList();

        BigDecimal totalIncome = incomeTransactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpense = expenseTransactions.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("report", report);
        model.addAttribute("incomeTransactions", incomeTransactions);
        model.addAttribute("expenseTransactions", expenseTransactions);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("net", totalIncome.subtract(totalExpense));

        return "FinanceManagement/report-detail";
    }

    // Xóa báo cáo
    @GetMapping("/reports/delete/{id}")
    @Transactional
    public String deleteReport(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        User currentUser = (User) session.getAttribute("user");
        if (!(currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FINANCE_MANAGER)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xóa báo cáo.");
            return "redirect:/finance/reports";
        }
        financialReportRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Đã xóa báo cáo.");
        return "redirect:/finance/reports";
    }
}
