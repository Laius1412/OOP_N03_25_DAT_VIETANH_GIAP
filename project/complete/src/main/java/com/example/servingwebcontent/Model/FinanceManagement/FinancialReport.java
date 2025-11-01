package com.example.servingwebcontent.model.FinanceManagement;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_reports")
public class FinancialReport extends AbstractFinanceEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "report_name", nullable = false, length = 200)
    private String reportName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "total_income", precision = 15, scale = 2)
    private BigDecimal totalIncome = BigDecimal.ZERO;
    
    @Column(name = "total_expense", precision = 15, scale = 2)
    private BigDecimal totalExpense = BigDecimal.ZERO;
    
    @Column(name = "net_balance", precision = 15, scale = 2)
    private BigDecimal netBalance = BigDecimal.ZERO;
    
    @Column(name = "transaction_count")
    private Integer transactionCount = 0;
    
    @Column(name = "generated_by_id", nullable = false)
    private Long generatedById; // tham chiếu User.id
    
    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt = LocalDateTime.now();
    
    @Column(name = "report_data", columnDefinition = "TEXT")
    private String reportData; // JSON data for detailed report
    
    // Constructors
    public FinancialReport() {}
    
    public FinancialReport(String reportName, ReportType reportType,
                         LocalDate startDate, LocalDate endDate,
                         BigDecimal totalIncome, BigDecimal totalExpense,
                         Integer transactionCount, Long generatedById, String notes) {
        this.reportName = reportName;
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netBalance = totalIncome.subtract(totalExpense);
        this.transactionCount = transactionCount;
        this.generatedById = generatedById;
        this.setNotes(notes);
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getReportName() {
        return reportName;
    }
    
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    
    public ReportType getReportType() {
        return reportType;
    }
    
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
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
    
    public BigDecimal getTotalIncome() {
        return totalIncome;
    }
    
    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
        calculateNetBalance();
    }
    
    public BigDecimal getTotalExpense() {
        return totalExpense;
    }
    
    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
        calculateNetBalance();
    }
    
    public BigDecimal getNetBalance() {
        return netBalance;
    }
    
    public void setNetBalance(BigDecimal netBalance) {
        this.netBalance = netBalance;
    }
    
    public Integer getTransactionCount() {
        return transactionCount;
    }
    
    public void setTransactionCount(Integer transactionCount) {
        this.transactionCount = transactionCount;
    }
    
    public Long getGeneratedById() {
        return generatedById;
    }
    
    public void setGeneratedById(Long generatedById) {
        this.generatedById = generatedById;
    }
    
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
    
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
    
    public String getReportData() {
        return reportData;
    }
    
    public void setReportData(String reportData) {
        this.reportData = reportData;
    }
    
    // Utility methods
    private void calculateNetBalance() {
        this.netBalance = totalIncome.subtract(totalExpense);
    }
    
    public boolean isProfit() {
        return netBalance.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean isLoss() {
        return netBalance.compareTo(BigDecimal.ZERO) < 0;
    }
    
    public boolean isBreakEven() {
        return netBalance.compareTo(BigDecimal.ZERO) == 0;
    }
    
    public BigDecimal getProfitMargin() {
        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return netBalance.divide(totalIncome, 4, RoundingMode.HALF_UP)
                         .multiply(BigDecimal.valueOf(100));
    }
    
    public BigDecimal getExpenseRatio() {
        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return totalExpense.divide(totalIncome, 4, RoundingMode.HALF_UP)
                           .multiply(BigDecimal.valueOf(100));
    }
    
    public long getDaysInPeriod() {
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }
    
    public BigDecimal getAverageDailyIncome() {
        if (getDaysInPeriod() == 0) {
            return BigDecimal.ZERO;
        }
        return totalIncome.divide(BigDecimal.valueOf(getDaysInPeriod()), 2, RoundingMode.HALF_UP);
    }
    
    public BigDecimal getAverageDailyExpense() {
        if (getDaysInPeriod() == 0) {
            return BigDecimal.ZERO;
        }
        return totalExpense.divide(BigDecimal.valueOf(getDaysInPeriod()), 2, RoundingMode.HALF_UP);
    }
    
    @Override
    public String toString() {
        return "FinancialReport{" +
                "id=" + id +
                ", reportName='" + reportName + '\'' +
                ", reportType=" + reportType +
                ", totalIncome=" + totalIncome +
                ", totalExpense=" + totalExpense +
                ", netBalance=" + netBalance +
                ", generatedAt=" + generatedAt +
                '}';
    }
}
