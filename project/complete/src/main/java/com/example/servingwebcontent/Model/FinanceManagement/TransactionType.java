package com.example.servingwebcontent.Model.FinanceManagement;

public enum TransactionType {
    INCOME("INCOME", "Thu nhập", "Các khoản thu vào"),
    EXPENSE("EXPENSE", "Chi tiêu", "Các khoản chi ra");
    
    private final String code;
    private final String displayName;
    private final String description;
    
    TransactionType(String code, String displayName, String description) {
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
    
    public static TransactionType fromCode(String code) {
        for (TransactionType type : TransactionType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return EXPENSE; // Default
    }
}
