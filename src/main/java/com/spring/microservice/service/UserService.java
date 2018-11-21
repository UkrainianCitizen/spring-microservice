package com.spring.microservice.service;

import com.spring.microservice.domain.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

/**
 * User Service.
 */
public interface UserService {
    Authentication signin(String username, String password);
    Optional<User> signup(String username, String password, String firstName, String lastName);
    List<User> getAll();
}