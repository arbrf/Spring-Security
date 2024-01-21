package com.in28minutes.learn.spring.security.jwt;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTAuthenticationResource {

    @PostMapping("/authenticate")
    public Authentication authenticate(Authentication authenticateAction) {
        return authenticateAction;
    }
}
