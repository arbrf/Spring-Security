package com.email.validation.service;


import com.email.validation.entity.UserAuthentication;
import com.email.validation.exception.CustomAuthenticationFailureHandler;
import com.email.validation.exception.UserNotVerifiedException;
import com.email.validation.repo.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAuthenticationRepository userRepo;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuthentication user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        boolean isVerfied=user.getVerified();

        if (!isVerfied) {
            // Throw a custom exception if the user is not verified

            throw new UserNotVerifiedException("Please register or verify your email.");
        }
        System.out.println("CustomUserDetailsService----->");
        System.out.println(isVerfied);
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}

