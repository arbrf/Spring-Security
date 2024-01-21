package com.in28minutes.learn.spring.security.basic.auth;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class BasicAuthConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetails(DataSource dataSource) {
        var user = org.springframework.security.core.userdetails.User
                .withUsername("in28minutes")
//                .password("{noop}dummy")
                .password("dummy")
                .passwordEncoder(str->passwordEncoder().encode(str))
                .roles("USER")
                .build();

        var admin = org.springframework.security.core.userdetails.User
                .withUsername("admin")
                .password("dummy")
                .passwordEncoder(str->passwordEncoder().encode(str))
                .roles("ADMIN","USER")
                .build();
       var jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
       jdbcUserDetailsManager.createUser(user);
       jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build(); // <-- Add this line to fix the type mismatch
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	
    	return new BCryptPasswordEncoder(10);
    }
}