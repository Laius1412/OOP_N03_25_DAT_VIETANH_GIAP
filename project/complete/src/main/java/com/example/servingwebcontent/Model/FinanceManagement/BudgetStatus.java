package com.example.servingwebcontent.Model.FinanceManagement;

public enum BudgetStatus {
    ACTIVE("ACTIVE", "Đang hoạt động", "Ngân sách đang được sử dụng"),
    COMPLETED("COMPLETED", "Hoàn thành", "Ngân sách đã hoàn thành"),
    CANCELLED("CANCELLED", "Hủy bỏ", "Ngân sách đã bị hủy"),
    EXPIRED("EXPIRED", "Hết hạn", "Ngân sách đã hết hạn");
    
    private final String code;
    private final String displayName;
    private final String description;
    
    BudgetStatus(String code, String displayName, String description) {
        this.code = code;
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static BudgetStatus fromCode(String code) {
        for (BudgetStatus status : BudgetStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return ACTIVE; // Default
    }
}
