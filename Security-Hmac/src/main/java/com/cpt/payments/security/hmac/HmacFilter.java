package com.cpt.payments.security.hmac;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cpt.payments.config.CachedBodyHttpServletRequest;
import com.cpt.payments.config.CustomAuthToken;
import com.cpt.payments.constants.WrappedRequest;


public class HmacFilter extends OncePerRequestFilter {

    private final HmacSecurityProvider hmacService;
    private static final Logger LOGGER = LogManager.getLogger(HmacFilter.class);
    public HmacFilter(HmacSecurityProvider hmacService) {
        this.hmacService = hmacService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	CachedBodyHttpServletRequest cachedBodyHttpServletRequest=new CachedBodyHttpServletRequest(request);
    	WrappedRequest wrappedRequest=new WrappedRequest(cachedBodyHttpServletRequest);
      boolean verifiedSign=hmacService.verifyHmac(wrappedRequest);
    	if(verifiedSign) {
            // Perform HMAC verification
   
                // Create and set the CustomAuthToken
                Authentication auth = new CustomAuthToken();
                SecurityContextHolder.getContext().setAuthentication(auth);
    	}
             else {
                // If verification fails, reject the request
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
       

        // Continue the filter chain if the verification is successful
       chain.doFilter(request, response);
    }
}

