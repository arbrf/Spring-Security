package com.email.validation.service;
import com.email.validation.entity.UserAuthentication;
import com.email.validation.repo.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserAuthenticationRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserAuthentication registerUser(UserAuthentication user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(false); // Automatically set as verified after registration
        return userRepo.save(user);
    }
}
