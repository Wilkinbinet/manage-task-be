package com.quid.innteci.manage.task.config;

import com.quid.innteci.manage.task.security.jwt.JwtConfigurer;
import com.quid.innteci.manage.task.security.jwt.JwtTokenProvider;
import com.quid.innteci.manage.task.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

/**
 * This class represents the configuration for the security of the Spring Web application.
 * It extends the WebSecurityConfigurerAdapter class provided by Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Sets the JwtTokenProvider instance.
     *
     * @param jwtTokenProvider the JwtTokenProvider instance to set
     */
    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Sets the UserDetailsServiceImpl instance.
     *
     * @param userDetailsService the UserDetailsServiceImpl instance to set
     */
    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures the AuthenticationManager to use the UserDetailsServiceImpl for user details retrieval
     * and sets the password encoder.
     *
     * @param auth the AuthenticationManagerBuilder instance
     * @throws Exception if an exception occurs during configuration
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Provides the AuthenticationManager bean to be used for authentication.
     *
     * @return the AuthenticationManager bean
     * @throws Exception if an exception occurs during bean creation
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Provides the PasswordEncoder bean for encoding passwords.
     *
     * @return the PasswordEncoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the HTTP security for the application.
     * Adds a custom CorsFilter before the SessionManagementFilter to handle Cross-Origin requests.
     * Disables HTTP basic authentication and CSRF protection.
     * Defines the authorization rules for various API endpoints.
     * Applies the JwtConfigurer for JWT token-based authentication.
     *
     * @param http the HttpSecurity instance to be configured
     * @throws Exception if an exception occurs during configuration
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Adds the custom CorsFilter before the SessionManagementFilter to handle Cross-Origin requests
        http.addFilterBefore(corsFilter(), SessionManagementFilter.class)
                .httpBasic().disable() // Disables HTTP basic authentication
                .csrf().disable() // Disables CSRF protection
                .authorizeRequests()
                .antMatchers("/api/users/register").permitAll() // Permits access to "/api/users/register" endpoint without authentication
                .antMatchers("/api/auth/login").permitAll() // Permits access to "/api/auth/login" endpoint without authentication
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permits OPTIONS access to all endpoints without authentication
                .antMatchers("/api/tasks/**").authenticated() // Requires authentication for "/api/tasks/**" endpoints
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider)); // Applies JwtConfigurer for JWT token-based authentication
    }

    /**
     * Creates and provides the CorsFilter bean.
     *
     * @return the CorsFilter bean
     */
    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }
}