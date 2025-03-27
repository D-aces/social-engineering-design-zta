package com.otu2.identity_service.controller;

import com.otu2.identity_service.model.Credential;
import com.otu2.identity_service.model.LoginRequest;
import com.otu2.identity_service.model.LoginResponse;
import com.otu2.identity_service.service.CredentialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// REST controller for credential-related operations
@RestController
@RequestMapping("/api/credentials")
public class CredentialController {

    // Business logic service
    @Autowired
    private CredentialService credentialService;

    // Handle login requests and validate token
    @PostMapping("/validate")
    public ResponseEntity<LoginResponse> validate(@RequestBody LoginRequest request) {
        LoginResponse response = credentialService.validateToken(request.getId(), request.getToken());
        return ResponseEntity.status(response.isValid() ? 200 : 401).body(response);
    }

    // Create a new user credential (admin/tester use)
    @PostMapping("/users")
    public ResponseEntity<Credential> create(@RequestBody Credential credential) {
        Credential saved = credentialService.createUser(credential);
        return ResponseEntity.ok(saved);
    }
}
