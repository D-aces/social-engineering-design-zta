package com.otu2.identity_service.service;

import com.otu2.identity_service.model.Credential;
import com.otu2.identity_service.model.LoginResponse;
import com.otu2.identity_service.repository.CredentialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

// Business logic for credential operations
@Service
public class CredentialService {

    // Inject credential repository
    @Autowired
    private CredentialRepository credentialRepository;

    // Validate login request using hashed token
    public LoginResponse validateToken(String userId, String submittedToken) {

        // Find active credential by ID
        Optional<Credential> result = credentialRepository.findByUserIdAndIsActiveTrue(userId);

        // Return failed response if user not found
        if (result.isEmpty()) {
            return new LoginResponse(false, "User not found or inactive");
        }

        // Compare submitted token to stored hash
        Credential cred = result.get();
        boolean valid = BCrypt.checkpw(submittedToken, cred.getHashedToken());

        // Return success or failure based on comparison
        if (valid) {
            return new LoginResponse(true, cred.getRole(), cred.getAllowedLocations());
        } else {
            return new LoginResponse(false, "Invalid token");
        }
    }

    // Create or update a user with a hashed token
    public Credential createUser(Credential rawCred) {

        // Hash the provided token
        String hashed = BCrypt.hashpw(rawCred.getHashedToken(), BCrypt.gensalt());

        // Set the hashed token and timestamp
        rawCred.setHashedToken(hashed);
        rawCred.setLastUpdated(LocalDateTime.now());

        // Save to database
        return credentialRepository.save(rawCred);
    }
}
