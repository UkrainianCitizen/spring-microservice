package com.spring.microservice.web;

import com.spring.microservice.domain.User;
import com.spring.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public Authentication login(@RequestBody @Valid LoginDto loginDto) {
        return userService.signin(loginDto.getUsername(), loginDto.getPassword());
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User signup(@RequestBody @Valid LoginDto loginDto) {
        return userService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFirstName(),
                loginDto.getLastName()).orElseThrow(() -> new RuntimeException("User already exists"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}