package com.quid.innteci.manage.task.controller;

import com.quid.innteci.manage.task.dto.UserDto;
import com.quid.innteci.manage.task.model.User;
import com.quid.innteci.manage.task.service.impl.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        User savedUser = userServiceImpl.registerUser(user);
        UserDto responseDto = new UserDto();
        BeanUtils.copyProperties(savedUser, responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}