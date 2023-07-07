package com.quid.innteci.manage.task.service.impl;

import com.quid.innteci.manage.task.dto.AuthenticationDto;
import com.quid.innteci.manage.task.security.jwt.JwtTokenProvider;
import com.quid.innteci.manage.task.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * This class implements the AuthenticationService interface and provides the login functionality.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructs an instance of AuthenticationServiceImpl.
     *
     * @param authenticationManager the AuthenticationManager instance for authentication
     * @param jwtTokenProvider      the JwtTokenProvider instance for JWT token management
     */
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Performs the login operation.
     * Authenticates the user with the provided credentials and generates a JWT token.
     *
     * @param request the AuthenticationDto containing the login credentials
     * @return the generated JWT token
     */
    @Override
    public String login(AuthenticationDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        return jwtTokenProvider.createToken(request.getEmail());
    }
}
