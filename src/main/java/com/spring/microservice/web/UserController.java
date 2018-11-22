package com.spring.microservice.web;

import com.spring.microservice.domain.User;
import com.spring.microservice.service.UserService;
import com.spring.microservice.web.dto.LoginRequest;
import com.spring.microservice.web.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;

/**
 * User controller.
 */
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private UserService userService;

    private static final String LOGIN_FAILED_MSG = "Login Failed";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return new LoginResponse(userService.signin(loginRequest.getUsername(),
                loginRequest.getPassword()).orElseThrow(() ->
                new HttpServerErrorException(HttpStatus.FORBIDDEN, LOGIN_FAILED_MSG)));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User signup(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.signup(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getFirstName(),
                loginRequest.getLastName()).orElseThrow(() -> new RuntimeException("User already exists"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}