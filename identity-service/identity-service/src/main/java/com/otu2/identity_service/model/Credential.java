package com.otu2.identity_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "credentials")
public class Credential {

    // Unique user identifier
    @Id
    private String userId;

    // Hashed token or password
    @Column(nullable = false)
    private String hashedToken;

    // User role for access control
    @Column(nullable = false)
    private String role;

    // JSON list of allowed access locations
    @Column(columnDefinition = "json")
    private String allowedLocations;

    // Whether the credential is active
    @Column
    private boolean isActive = true;

    // Last time the credentials were updated
    @Column
    private LocalDateTime lastUpdated = LocalDateTime.now();

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashedToken() {
        return hashedToken;
    }

    public void setHashedToken(String hashedToken) {
        this.hashedToken = hashedToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAllowedLocations() {
        return allowedLocations;
    }

    public void setAllowedLocations(String allowedLocations) {
        this.allowedLocations = allowedLocations;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}