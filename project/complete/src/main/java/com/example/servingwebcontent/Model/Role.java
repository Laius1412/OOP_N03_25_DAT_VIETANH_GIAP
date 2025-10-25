package com.example.servingwebcontent.Model;

public enum Role {
    ADMIN("ADMIN", "Quản trị viên", "Toàn quyền quản lý hệ thống"),
    MEMBER_MANAGER("MEMBER_MANAGER", "Quản lý thành viên", "Quản lý thông tin thành viên gia đình"),
    EVENT_MANAGER("EVENT_MANAGER", "Quản lý sự kiện", "Quản lý các sự kiện gia đình"),
    FINANCE_MANAGER("FINANCE_MANAGER", "Quản lý tài chính", "Quản lý thu chi gia đình"),
    USER("USER", "Người dùng", "Chỉ xem thông tin");
    
    private final String code;
    private final String displayName;
    private final String description;
    
    Role(String code, String displayName, String description) {
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
    
    public static Role fromCode(String code) {
        for (Role role : Role.values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return USER; // Default role
    }
    
    public boolean hasPermission(String permission) {
        switch (this) {
            case ADMIN:
                return true; // Admin có tất cả quyền
            case MEMBER_MANAGER:
                return permission.startsWith("MEMBER_") || permission.equals("VIEW_");
            case EVENT_MANAGER:
                return permission.startsWith("EVENT_") || permission.equals("VIEW_");
            case FINANCE_MANAGER:
                return permission.startsWith("FINANCE_") || permission.equals("VIEW_");
            case USER:
                return permission.equals("VIEW_");
            default:
                return false;
        }
    }
}
