package com.spring_security_pwd_validation.demo.dao.impl;

import com.spring_security_pwd_validation.demo.dao.UserDaoService;
import com.spring_security_pwd_validation.demo.dto.UserData;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDaoService {

    @Override
    public void printUSerDatatoConsole(UserData userData){
        System.out.println(userData.getEmail());

        System.out.println(userData.getPassword());
        System.out.println(userData.getLastName());System.out.println(userData.getFirstName());
    }
}
