package com.quid.innteci.manage.task.controller;

import com.quid.innteci.manage.task.dto.UserDto;
import com.quid.innteci.manage.task.model.User;
import com.quid.innteci.manage.task.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the UserController class.
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userServiceImpl;

    private UserDto userDto;
    private User user;

    /**
     * Sets up the test environment before each test case.
     */
    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setUserName("Test");
        userDto.setEmail("test@test.com");
        userDto.setPassword("****");

        user = new User();
        user.setUserName("Test");
        user.setEmail("test@test.com");
    }

    /**
     * Unit test for the registerUser method.
     */
    @Test
    void registerUser() {
        when(userServiceImpl.registerUser(any(User.class))).thenReturn(user);

        ResponseEntity<UserDto> responseEntity = userController.registerUser(userDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user.getUserName(), responseEntity.getBody().getUserName());
        assertEquals(user.getEmail(), responseEntity.getBody().getEmail());
    }

    /**
     * Unit test for the registerUser method with missing required fields.
     */
    @Test
    void registerUserMissingRequiredFields() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");

        assertThrows(IllegalArgumentException.class, () -> userController.registerUser(userDto));
    }

    /**
     * Unit test for the registerUser method with an invalid email.
     */
    @Test
    void registerUserWithInvalidEmail() {
        UserDto userDto = new UserDto();
        userDto.setUserName("Test");
        userDto.setPassword("password");
        userDto.setEmail("invalid-email");

        assertThrows(IllegalArgumentException.class, () -> userController.registerUser(userDto));
    }
}
