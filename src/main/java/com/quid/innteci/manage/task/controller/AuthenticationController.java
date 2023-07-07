package com.quid.innteci.manage.task.controller;

import com.quid.innteci.manage.task.dto.AuthenticationDto;
import com.quid.innteci.manage.task.service.impl.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;

    /**
     * Constructs an instance of the AuthenticationController.
     *
     * @param authenticationServiceImpl the AuthenticationServiceImpl instance
     */
    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    /**
     * Handles the login request and returns the ResponseEntity with the authentication result.
     *
     * @param request the AuthenticationDto request object
     * @return the ResponseEntity with the authentication result
     */
    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthenticationDto request) {
        return ResponseEntity.ok(authenticationServiceImpl.login(request));
    }
}