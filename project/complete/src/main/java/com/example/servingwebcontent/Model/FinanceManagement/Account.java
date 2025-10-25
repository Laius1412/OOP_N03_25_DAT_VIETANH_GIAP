package com.example.servingwebcontent.Model.FinanceManagement;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;
    
    @Column(name = "bank_name", length = 100)
    private String bankName;
    
    @Column(name = "account_number", length = 50)
    private String accountNumber;
    
    @Column(name = "initial_balance", precision = 15, scale = 2)
    private BigDecimal initialBalance = BigDecimal.ZERO;
    
    @Column(name = "current_balance", precision = 15, scale = 2)
    private BigDecimal currentBalance = BigDecimal.ZERO;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_by_id", nullable = false)
    private Long createdById; // tham chiếu User.id
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(name = "description", length = 500)
    private String description;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountTransaction> accountTransactions;
    
    // Constructors
    public Account() {}
    
    public Account(String name, AccountType accountType, String bankName, 
                 String accountNumber, BigDecimal initialBalance, 
                 Long createdById, String description) {
        this.name = name;
        this.accountType = accountType;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.initialBalance = initialBalance;
        this.currentBalance = initialBalance;
        this.createdById = createdById;
        this.description = description;
        this.isActive = true;
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
    
    public AccountType getAccountType() {
        return accountType;
    }
    
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public BigDecimal getInitialBalance() {
        return initialBalance;
    }
    
    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
    
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
    
    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public List<AccountTransaction> getAccountTransactions() {
        return accountTransactions;
    }
    
    public void setAccountTransactions(List<AccountTransaction> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }
    
    // Utility methods
    public void addToBalance(BigDecimal amount) {
        this.currentBalance = this.currentBalance.add(amount);
    }
    
    public void subtractFromBalance(BigDecimal amount) {
        this.currentBalance = this.currentBalance.subtract(amount);
    }
    
    public BigDecimal getBalanceDifference() {
        return this.currentBalance.subtract(this.initialBalance);
    }
    
    public boolean hasSufficientFunds(BigDecimal amount) {
        return this.currentBalance.compareTo(amount) >= 0;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountType=" + accountType +
                ", currentBalance=" + currentBalance +
                ", isActive=" + isActive +
                '}';
    }
}
