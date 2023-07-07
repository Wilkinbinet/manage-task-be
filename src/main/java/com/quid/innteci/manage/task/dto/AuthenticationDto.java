package com.quid.innteci.manage.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * This class represents the data transfer object (DTO) for authentication.
 * It contains the email and password fields for authentication purposes.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {

    /**
     * The email associated with the user.
     */
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * The password associated with the user.
     */
    @NotEmpty(message = "Password should not be empty")
    private String password;

}
