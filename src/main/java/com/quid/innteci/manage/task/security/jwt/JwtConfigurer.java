package com.quid.innteci.manage.task.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class configures JWT authentication in the Spring Security framework.
 * It extends the SecurityConfigurerAdapter class provided by Spring Security.
 */
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;

    /**
     * Constructs an instance of JwtConfigurer.
     *
     * @param jwtTokenProvider the JwtTokenProvider instance for JWT token management
     */
    public JwtConfigurer(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Configures the HttpSecurity to add the JwtTokenFilter before the UsernamePasswordAuthenticationFilter.
     *
     * @param http the HttpSecurity instance to be configured
     * @throws Exception if an exception occurs during configuration
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}