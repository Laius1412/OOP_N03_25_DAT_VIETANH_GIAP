package com.example.servingwebcontent.Model.FinanceManagement;

import com.example.servingwebcontent.Model.PersonManagement.Person;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "category_id")
    private FinanceCategory category;
    
    @Column(name = "created_by_id", nullable = false)
    private Long createdById; // tham chiếu User.id
    
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;
    
    @Column(name = "receipt_number", length = 50)
    private String receiptNumber;
    
    @Column(name = "notes", length = 500)
    private String notes;
    
    // Thông tin thành viên đóng góp (tạm thời dùng text, sau sẽ liên kết với Member)
    @Column(name = "contributor_name", length = 100)
    private String contributorName;
    
    @Column(name = "contributor_phone", length = 20)
    private String contributorPhone;
    
    @Column(name = "contributor_relationship", length = 100)
    private String contributorRelationship; // Mối quan hệ với dòng họ

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contributor_id")
    private Person contributor; // Liên kết tới Person người nộp
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Đã loại bỏ liên kết Account/AccountTransaction không sử dụng
    
    // Constructors
    public Transaction() {}
    
    public Transaction(String title, String description, BigDecimal amount, 
                     TransactionType transactionType, FinanceCategory category,
                     Long createdById, LocalDate transactionDate, 
                     PaymentMethod paymentMethod, String receiptNumber, String notes,
                     String contributorName, String contributorPhone, String contributorRelationship) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.transactionType = transactionType;
        this.category = category;
        this.createdById = createdById;
        this.transactionDate = transactionDate;
        this.paymentMethod = paymentMethod;
        this.receiptNumber = receiptNumber;
        this.notes = notes;
        this.contributorName = contributorName;
        this.contributorPhone = contributorPhone;
        this.contributorRelationship = contributorRelationship;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public TransactionType getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    
    public FinanceCategory getCategory() {
        return category;
    }
    
    public void setCategory(FinanceCategory category) {
        this.category = category;
    }
    
    public Long getCreatedById() {
        return createdById;
    }
    
    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }
    
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getReceiptNumber() {
        return receiptNumber;
    }
    
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
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
    
    public String getContributorName() {
        return contributorName;
    }
    
    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }
    
    public String getContributorPhone() {
        return contributorPhone;
    }
    
    public void setContributorPhone(String contributorPhone) {
        this.contributorPhone = contributorPhone;
    }
    
    public String getContributorRelationship() {
        return contributorRelationship;
    }
    
    public void setContributorRelationship(String contributorRelationship) {
        this.contributorRelationship = contributorRelationship;
    }

    public Person getContributor() {
        return contributor;
    }

    public void setContributor(Person contributor) {
        this.contributor = contributor;
    }
    
    // Utility methods
    public boolean isIncome() {
        return this.transactionType == TransactionType.INCOME;
    }
    
    public boolean isExpense() {
        return this.transactionType == TransactionType.EXPENSE;
    }
    
    public BigDecimal getSignedAmount() {
        return isIncome() ? amount : amount.negate();
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                ", createdById=" + createdById +
                '}';
    }
}
