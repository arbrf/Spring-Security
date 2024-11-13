package com.email.validation.config;

import com.email.validation.controller.HttpsRedirectFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HttpsRedirectFilter> customHttpsRedirectFilter() {
        FilterRegistrationBean<HttpsRedirectFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpsRedirectFilter());
        registrationBean.addUrlPatterns("/accounts/*");
        return registrationBean;
    }
}
