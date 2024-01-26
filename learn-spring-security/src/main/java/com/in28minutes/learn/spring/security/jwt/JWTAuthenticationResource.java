package com.in28minutes.learn.spring.security.jwt;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class JWTAuthenticationResource {
    private JwtEncoder jwtEncoder;

    public JWTAuthenticationResource(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/authenticate")
    public JwtResponse authenticate(Authentication authentication) {
        return new JwtResponse(createToken(authentication));
    }

    public String createToken(Authentication authentication) {
        var jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30))
                .subject(authentication.getName()) // Assuming the username is the subject
                .claim("scope", createScope(authentication))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    private String createScope(Authentication authentication) {
        // TODO: Implement logic to determine and create the scope based on the authentication
        // Example: return "read write";
    	return authentication.getAuthorities().
    	stream().map(a->a.getAuthority()).
    	collect(Collectors.joining(""));
         
    }

    public record JwtResponse(String token) {
        // Any additional logic or customization if needed
    }
}
