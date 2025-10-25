package com.example.servingwebcontent.Model.FinanceManagement;

public enum AccountType {
    CASH("CASH", "Tiền mặt", "Tài khoản tiền mặt"),
    BANK("BANK", "Ngân hàng", "Tài khoản ngân hàng"),
    CREDIT_CARD("CREDIT_CARD", "Thẻ tín dụng", "Thẻ tín dụng"),
    INVESTMENT("INVESTMENT", "Đầu tư", "Tài khoản đầu tư"),
    SAVINGS("SAVINGS", "Tiết kiệm", "Tài khoản tiết kiệm");
    
    private final String code;
    private final String displayName;
    private final String description;
    
    AccountType(String code, String displayName, String description) {
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
    
    public static AccountType fromCode(String code) {
        for (AccountType type : AccountType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return CASH; // Default
    }
}
