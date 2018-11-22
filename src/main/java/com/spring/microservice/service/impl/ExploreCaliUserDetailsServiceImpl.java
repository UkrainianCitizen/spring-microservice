package com.spring.microservice.service.impl;

import com.spring.microservice.domain.User;
import com.spring.microservice.repo.UserRepository;
import com.spring.microservice.security.JwtProvider;
import com.spring.microservice.service.ExploreCaliUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

/**
 * Service to associate user with password and roles setup in the database.
 */
@Service
public class ExploreCaliUserDetailsServiceImpl implements ExploreCaliUserDetailsService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    private static final String USER_DOES_NOT_EXIST_MSG = "User with name %s does not exist";

    @Autowired
    public ExploreCaliUserDetailsServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_DOES_NOT_EXIST_MSG, userName)));

        return withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    @Override
    public Optional<UserDetails> loadUserByJwtToken(String jwtToken) {
        if (jwtProvider.isValidToken(jwtToken)) {
            return Optional.of(
                    withUsername(jwtProvider.getUsername(jwtToken))
                            .authorities(jwtProvider.getRoles(jwtToken))
                            .password("") //token does not have password but field may not be empty
                            .accountExpired(false)
                            .accountLocked(false)
                            .credentialsExpired(false)
                            .disabled(false)
                            .build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDetails> loadUserByJwtTokenAndDatabase(String jwtToken) {
        if (jwtProvider.isValidToken(jwtToken)) {
            return Optional.of(loadUserByUsername(jwtProvider.getUsername(jwtToken)));
        } else {
            return Optional.empty();
        }
    }
}