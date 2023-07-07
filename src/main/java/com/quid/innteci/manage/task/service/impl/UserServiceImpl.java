package com.quid.innteci.manage.task.service.impl;

import com.quid.innteci.manage.task.model.User;
import com.quid.innteci.manage.task.repository.UserRepository;
import com.quid.innteci.manage.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class implements the UserService interface and provides user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an instance of UserServiceImpl.
     *
     * @param userRepository  the UserRepository instance for user operations
     * @param passwordEncoder the PasswordEncoder instance for password encoding
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     *
     * @param user the User object representing the user to register
     * @return the registered user
     */
    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
