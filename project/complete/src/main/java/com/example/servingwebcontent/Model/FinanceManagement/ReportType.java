package com.example.servingwebcontent.model.FinanceManagement;

public enum ReportType {
    MONTHLY("MONTHLY", "Báo cáo tháng", "Báo cáo tài chính theo tháng"),
    QUARTERLY("QUARTERLY", "Báo cáo quý", "Báo cáo tài chính theo quý"),
    YEARLY("YEARLY", "Báo cáo năm", "Báo cáo tài chính theo năm"),
    CUSTOM("CUSTOM", "Báo cáo tùy chỉnh", "Báo cáo tài chính theo khoảng thời gian tùy chỉnh"),
    WEEKLY("WEEKLY", "Báo cáo tuần", "Báo cáo tài chính theo tuần"),
    DAILY("DAILY", "Báo cáo ngày", "Báo cáo tài chính theo ngày");
    
    private final String code;
    private final String displayName;
    private final String description;
    
    ReportType(String code, String displayName, String description) {
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
    
    public static ReportType fromCode(String code) {
        for (ReportType type : ReportType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return CUSTOM; // Default
    }
}
