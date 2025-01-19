package com.example.bookmanager.Config;

import com.example.bookmanager.Utils.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> jwtAuthenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/api/books/*");
        return registrationBean;
    }
}
