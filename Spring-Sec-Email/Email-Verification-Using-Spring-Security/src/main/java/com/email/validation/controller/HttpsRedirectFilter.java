package com.email.validation.controller;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class HttpsRedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        // Check if the request is for /accounts and not already HTTPS
        if (uri.startsWith("/accounts") && !request.isSecure()) {
            String httpsUrl = "https://" + request.getServerName() + ":8443" + uri;
            response.sendRedirect(httpsUrl);
            return;
        }

        filterChain.doFilter(request, response);
    }
}

