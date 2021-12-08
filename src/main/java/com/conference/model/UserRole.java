package com.conference.model;

public enum UserRole {
    ADMIN("admin", 1), SPEAKER("speaker", 2), USER("user", 3);

    private String value;
    private Integer role;

    UserRole(String value, Integer role) {
        this.value = value;
        this.role = role;
    }

    public String getValue() {
        return value;
    }

    public Integer getRoleId() {
        return role;
    }

    public static UserRole fromString(String value) {
        if (value != null) {
            for (UserRole userRole : UserRole.values()) {
                if (value.equalsIgnoreCase(userRole.value)) {
                    return userRole;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }

}
