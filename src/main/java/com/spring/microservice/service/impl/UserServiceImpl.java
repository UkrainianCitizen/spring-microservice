package com.spring.microservice.service.impl;

import com.spring.microservice.domain.Role;
import com.spring.microservice.domain.User;
import com.spring.microservice.repo.RoleRepository;
import com.spring.microservice.repo.UserRepository;
import com.spring.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User service implementation.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication signin(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    public Optional<User> signup(String username, String password, String firstName, String lastName) {
        if (!userRepository.findByUsername(username).isPresent()) {
            Optional<Role> role = roleRepository.findByRoleName("ROLE_CSR");
            return role.map(persistedRole -> userRepository.save
                    (new User(username,
                            passwordEncoder.encode(password),
                            persistedRole,
                            firstName,
                            lastName)));
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}