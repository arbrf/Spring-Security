package com.springsecurity.jwt;

import com.springsecurity.jwt.dao.UserEntity;

import com.springsecurity.jwt.respository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class JwtSpringSecurityAppApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(JwtSpringSecurityAppApplication.class, args);
	}

	@PostConstruct
	public void init() {
		try {
			List<UserEntity> users = Arrays.asList(
					new UserEntity(1, "user1", "password1", "Address1"),
					new UserEntity(2, "user2", "password2", "Address2"),
					new UserEntity(3, "user3", "password3", "Address3")
			);
			userRepository.saveAll(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
