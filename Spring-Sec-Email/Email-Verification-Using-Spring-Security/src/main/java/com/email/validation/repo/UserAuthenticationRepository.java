package com.email.validation.repo;

import com.email.validation.entity.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthenticationRepository extends JpaRepository<UserAuthentication, Long> {
    Optional<UserAuthentication> findByEmail(String email);
}
