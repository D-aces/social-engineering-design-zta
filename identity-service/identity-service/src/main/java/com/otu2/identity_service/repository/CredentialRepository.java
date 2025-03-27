package com.otu2.identity_service.repository;

import com.otu2.identity_service.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, String> {

    // Find active credential by user ID
    Optional<Credential> findByUserIdAndIsActiveTrue(String userId);
}
