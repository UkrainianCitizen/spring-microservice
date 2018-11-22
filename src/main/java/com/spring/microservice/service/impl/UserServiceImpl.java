package com.spring.microservice.service.impl;

import com.spring.microservice.domain.Role;
import com.spring.microservice.domain.User;
import com.spring.microservice.repo.RoleRepository;
import com.spring.microservice.repo.UserRepository;
import com.spring.microservice.security.JwtProvider;
import com.spring.microservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private JwtProvider jwtProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Optional<String> signin(String username, String password) {
        LOGGER.info("New user attempting to sign in..");
        Optional<String> token = Optional.empty();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
            } catch (AuthenticationException e){
                LOGGER.info("Log in failed for user {}", username);
            }
        }
        return token;
    }

    @Override
    public Optional<User> signup(String username, String password, String firstName, String lastName) {
        LOGGER.info("New user attempting to sign in");
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