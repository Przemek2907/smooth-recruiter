package com.app.usersservice.model;

public enum BusinessRole {
    RECRUITER("ROLE_RECRUITER"),
    EXTERNAL_RECRUITER("ROLE_EXTERNAL_RECRUITER"),
    MANAGER("ROLE_MANAGER"),
    CANDIDATE("ROLE_CANDIDATE"),
    ADMIN("ROLE_ADMIN"),
    AUDITOR("ROLE_AUDITOR");

    private String fullName;

    BusinessRole(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
