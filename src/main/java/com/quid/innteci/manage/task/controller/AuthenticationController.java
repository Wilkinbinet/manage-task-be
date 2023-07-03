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

    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthenticationDto request) {
        return ResponseEntity.ok(authenticationServiceImpl.login(request));
    }
}
