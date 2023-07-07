package com.quid.innteci.manage.task.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * This class represents the data transfer object (DTO) for a user.
 * It contains the fields representing the user's ID, username, email, and password.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * The ID of the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    @NotEmpty(message = "Username should not be empty")
    private String userName;

    /**
     * The email of the user.
     */
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    /**
     * The password of the user.
     */
    @NotEmpty(message = "Password should not be empty")
    private String password;

}
