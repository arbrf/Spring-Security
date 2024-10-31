package com.spring_security_pwd_validation.demo.repo;




import com.spring_security_pwd_validation.demo.dto.UserData;
import com.spring_security_pwd_validation.demo.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceRepo {

    private final UserDataRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceRepo(UserDataRepo userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUserData(UserData userData) {
        // Check if the user already exists based on the email
        UserEntity existingUserEntity = userRepository.findByEmail(userData.getEmail());
        System.out.println("inside saveUserData method");
        if (existingUserEntity != null) {
            // Handle the case where the user already exists
            throw new RuntimeException("User with email " + userData.getEmail() + " already exists.");
        } else {
            // Save the new user data
            UserEntity userEntity = new UserEntity();
        System.out.println("Inserting USer");
            // Copy properties from UserData to UserEntity
            BeanUtils.copyProperties(userData, userEntity);
            userEntity.setPassword(getEncodedPassword(userData.getPassword()));
            // Save the new user entity to the repository
            userRepository.save(userEntity);
        }
    }

    private String getEncodedPassword(String password) {

       return passwordEncoder.encode(password);
    }


}

