package com.quid.innteci.manage.task.service;

import com.quid.innteci.manage.task.dto.AuthenticationDto;

public interface AuthenticationService {
    String login(AuthenticationDto request);
}