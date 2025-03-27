package com.otu2.identity_service.model;

// DTO for login response output
public class LoginResponse {

    // Whether the login was successful
    private boolean valid;

    // User's assigned role
    private String role;

    // JSON string of allowed access locations
    private String locations;

    // Reason if login failed (optional)
    private String reason;

    // Constructor for successful login
    public LoginResponse(boolean valid, String role, String locations) {
        this.valid = valid;
        this.role = role;
        this.locations = locations;
        this.reason = null;
    }

    // Constructor for failed login
    public LoginResponse(boolean valid, String reason) {
        this.valid = valid;
        this.role = null;
        this.locations = null;
        this.reason = reason;
    }

    // Getters
    public boolean isValid() {
        return valid;
    }

    public String getRole() {
        return role;
    }

    public String getLocations() {
        return locations;
    }

    public String getReason() {
        return reason;
    }
}
