package com.example.servingwebcontent.Model.FinanceManagement;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "budgets")
public class Budget {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(name = "budget_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal budgetAmount;
    
    @Column(name = "spent_amount", precision = 15, scale = 2)
    private BigDecimal spentAmount = BigDecimal.ZERO;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private FinanceCategory category;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "alert_threshold", precision = 5, scale = 2)
    private BigDecimal alertThreshold; // Phần trăm cảnh báo (ví dụ: 80%)
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BudgetStatus status = BudgetStatus.ACTIVE;
    
    @Column(name = "created_by_id", nullable = false)
    private Long createdById; // tham chiếu User.id
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(name = "notes", length = 500)
    private String notes;
    
    // Constructors
    public Budget() {}
    
    public Budget(String name, String description, BigDecimal budgetAmount,
                 FinanceCategory category, LocalDate startDate, LocalDate endDate,
                 BigDecimal alertThreshold, Long createdById, String notes) {
        this.name = name;
        this.description = description;
        this.budgetAmount = budgetAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.alertThreshold = alertThreshold;
        this.createdById = createdById;
        this.notes = notes;
        this.spentAmount = BigDecimal.ZERO;
        this.status = BudgetStatus.ACTIVE;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }
    
    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
    
    public BigDecimal getSpentAmount() {
        return spentAmount;
    }
    
    public void setSpentAmount(BigDecimal spentAmount) {
        this.spentAmount = spentAmount;
    }
    
    public FinanceCategory getCategory() {
        return category;
    }
    
    public void setCategory(FinanceCategory category) {
        this.category = category;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public BigDecimal getAlertThreshold() {
        return alertThreshold;
    }
    
    public void setAlertThreshold(BigDecimal alertThreshold) {
        this.alertThreshold = alertThreshold;
    }
    
    public BudgetStatus getStatus() {
        return status;
    }
    
    public void setStatus(BudgetStatus status) {
        this.status = status;
    }
    
    public Long getCreatedById() {
        return createdById;
    }
    
    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    // Utility methods
    public BigDecimal getRemainingAmount() {
        return budgetAmount.subtract(spentAmount);
    }
    
    public BigDecimal getSpentPercentage() {
        if (budgetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return spentAmount.divide(budgetAmount, 4, RoundingMode.HALF_UP)
                          .multiply(BigDecimal.valueOf(100));
    }
    
    public boolean isOverBudget() {
        return spentAmount.compareTo(budgetAmount) > 0;
    }
    
    public boolean isNearAlertThreshold() {
        if (alertThreshold == null) {
            return false;
        }
        return getSpentPercentage().compareTo(alertThreshold) >= 0;
    }
    
    public boolean isActive() {
        return status == BudgetStatus.ACTIVE;
    }
    
    public boolean isExpired() {
        return LocalDate.now().isAfter(endDate);
    }
    
    public boolean isCurrent() {
        LocalDate now = LocalDate.now();
        return !now.isBefore(startDate) && !now.isAfter(endDate);
    }
    
    public void addSpentAmount(BigDecimal amount) {
        this.spentAmount = this.spentAmount.add(amount);
    }
    
    public void subtractSpentAmount(BigDecimal amount) {
        this.spentAmount = this.spentAmount.subtract(amount);
    }
    
    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budgetAmount=" + budgetAmount +
                ", spentAmount=" + spentAmount +
                ", status=" + status +
                '}';
    }
}
