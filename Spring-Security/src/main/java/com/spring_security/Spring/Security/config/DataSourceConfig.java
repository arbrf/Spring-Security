package com.spring_security.Spring.Security.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

   /* @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Create a BCrypt password encoder bean
    }*/

    @Autowired
    private Environment env; // To access environment properties

    @Bean
    @Qualifier("mySqlJdbcTemplate")
    public DataSource mysqlDataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        try {
            System.out.println("Setting up MySQL DataSource");
            dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            dataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.password"));

            // Optional settings
            dataSource.setMaximumPoolSize(Integer.parseInt(env.getProperty("spring.datasource.maxActive", "10"))); // Default to 10 if not set
            dataSource.setMinimumIdle(Integer.parseInt(env.getProperty("spring.datasource.minIdle", "5"))); // Default to 5 if not set
            dataSource.setConnectionInitSql(env.getProperty("spring.datasource.validationQuery", "SELECT 1")); // Default query

            System.out.println("DataSource setup complete.");
        } catch (Exception e) {
            System.err.println("Error setting up DataSource: " + e.getMessage());
        }

        return dataSource;
    }
}

