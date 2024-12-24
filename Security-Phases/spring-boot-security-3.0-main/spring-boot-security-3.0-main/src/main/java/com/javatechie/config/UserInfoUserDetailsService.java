package com.javatechie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;



@Service
public class UserInfoUserDetailsService implements UserDetailsService {
	 @Autowired
	    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = repository.findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserInfoUserDetails(userInfo);
    }
}
