package com.quid.innteci.manage.task.service;

import com.quid.innteci.manage.task.dto.AuthenticationDto;
import com.quid.innteci.manage.task.security.jwt.JwtTokenProvider;
import com.quid.innteci.manage.task.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * This class contains unit tests for the AuthenticationServiceImpl class.
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationDto authenticationDto;

    /**
     * Sets up the test environment before each test case.
     */
    @BeforeEach
    void setUp() {
        authenticationDto = new AuthenticationDto();
        authenticationDto.setEmail("test@test.com");
        authenticationDto.setPassword("password");
    }

    /**
     * Unit test for the login method of AuthenticationServiceImpl.
     * Tests the case where user authentication is successful.
     */
    @Test
    void shouldAuthenticateUser() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtTokenProvider.createToken(anyString())).thenReturn("jwt-token");

        String token = authenticationService.login(authenticationDto);

        assertEquals("jwt-token", token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(1)).createToken(anyString());
    }

    /**
     * Unit test for the login method of AuthenticationServiceImpl.
     * Tests the case where user authentication fails.
     */
    @Test
    void shouldNotAuthenticateUser() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authenticationService.login(authenticationDto));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(0)).createToken(anyString());
    }
}
