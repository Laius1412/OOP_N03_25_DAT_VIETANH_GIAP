package com.example.servingwebcontent.Model.FinanceManagement;

public enum PaymentMethod {
    CASH("CASH", "Tiền mặt", "Thanh toán bằng tiền mặt"),
    BANK_TRANSFER("BANK_TRANSFER", "Chuyển khoản", "Chuyển khoản ngân hàng"),
    CREDIT_CARD("CREDIT_CARD", "Thẻ tín dụng", "Thanh toán bằng thẻ tín dụng"),
    CHECK("CHECK", "Séc", "Thanh toán bằng séc"),
    OTHER("OTHER", "Khác", "Phương thức khác");
    
    private final String code;
    private final String displayName;
    private final String description;
    
    PaymentMethod(String code, String displayName, String description) {
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
    
    public static PaymentMethod fromCode(String code) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        return CASH; // Default
    }
}
