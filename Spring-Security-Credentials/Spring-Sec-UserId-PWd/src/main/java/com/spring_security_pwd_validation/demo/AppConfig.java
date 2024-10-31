package com.spring_security_pwd_validation.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity; enable if using CSRF tokens
                .authorizeRequests()
                .antMatchers("/register", "/registration-success").permitAll() // Allow access to /register and success page
                .anyRequest().authenticated() // Require authentication for other requests
                .and()
                .formLogin()
                .loginPage("/login") // Specify your custom login page if applicable
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }


}

