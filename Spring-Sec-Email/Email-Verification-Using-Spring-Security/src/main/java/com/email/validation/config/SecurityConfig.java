package com.email.validation.config;

import com.email.validation.controller.HttpsRedirectFilter;
import com.email.validation.exception.CustomAuthenticationFailureHandler;
import com.email.validation.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig  {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // Uncomment the following line the first time you run the application to auto-create the tokens table
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .requiresChannel(channel -> channel
                        .requestMatchers(new AntPathRequestMatcher("/accounts/**"))
                        .requiresSecure()  // Enforce HTTPS for /accounts/** paths
                )
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/forgot-password", "/register", "/login", "/logout", "/css/**", "/js/**", "/reset-password**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true) // Redirect to /home on success
                        .failureHandler(customAuthenticationFailureHandler)
                        .failureUrl("/login?error=true")  // Redirect to /login?error=true on failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutUrl("/logout") // Set the logout endpoint URL
                        .logoutSuccessUrl("/login?logout") // Redirect after successful logout
                        .deleteCookies("dummyCookie", "remember-me") // Delete cookies after logout
                        .clearAuthentication(true) // Clear the authentication
                        .invalidateHttpSession(true)
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("uniqueAndSecret") // Set a unique key for hashing tokens
                        .rememberMeParameter("remember-me") // Name of the checkbox in the login form
                        .tokenValiditySeconds(86400) // Validity period in seconds (e.g., 1 day)
                        .userDetailsService(userDetailsService) // Optional: provide a custom UserDetailsService
                        .tokenRepository(persistentTokenRepository())
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Allow only one session per user
                        .maxSessionsPreventsLogin(false) // Kick out the previous session when a new login occurs
                        .expiredUrl("/login?expired=true") // Redirect to this URL if the session expires
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/auth/**", "/logout") // Ignore CSRF for specific endpoints if needed
                );

        return http.build();
    }


}

