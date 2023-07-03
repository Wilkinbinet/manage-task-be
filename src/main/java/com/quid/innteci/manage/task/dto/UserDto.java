package com.quid.innteci.manage.task.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "username should not be empty")
    private String userName;

    @Email(message = "email should be valid")
    @NotEmpty(message = "EMAIL should not be empty")
    private String email;

    @NotEmpty(message = "status should not be empty")
    private String password;
}