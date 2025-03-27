package com.otu2.identity_service.model;

// DTO for login request input
public class LoginRequest {

    // User identifier
    private String id;

    // Raw token or password
    private String token;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
