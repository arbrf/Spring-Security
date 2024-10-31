package com.spring_security_pwd_validation.demo.repo;

import com.spring_security_pwd_validation.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepo extends JpaRepository<UserEntity, Long> {
    // You can define custom query methods here if needed
    UserEntity findByEmail(String email);
}
