package com.example.servingwebcontent.model.PersonManagement;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Gender fromCode(String code) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(code)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("No gender with code " + code);
    }
}