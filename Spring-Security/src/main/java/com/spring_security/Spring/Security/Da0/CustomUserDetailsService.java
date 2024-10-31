package com.spring_security.Spring.Security.Da0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

   private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = jdbcTemplate.queryForObject(
                "SELECT username, password, enabled FROM users WHERE username = ?",
                new Object[]{username},
                (rs, rowNum) -> User.withUsername(rs.getString("username"))
                        .password(rs.getString("password"))        // bcrypt-encoded password
                        .disabled(!rs.getBoolean("enabled"))
                        .authorities(getUserAuthorities(username))
                        .build()
        );

        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return userDetails;
    }


    private List<GrantedAuthority> getUserAuthorities(String username) {
        // Query for authorities/roles
        return jdbcTemplate.query(
                "SELECT authority FROM authorities WHERE username = ?",
                new Object[]{username},
                (rs, rowNum) -> new SimpleGrantedAuthority(rs.getString("authority"))
        ).stream().collect(Collectors.toList());
    }


}
