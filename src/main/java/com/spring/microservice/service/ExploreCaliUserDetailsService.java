package com.spring.microservice.service;

import com.spring.microservice.domain.User;
import com.spring.microservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

/**
 * Service to associate user with password and roles setup in the database.
 */
@Service
public class ExploreCaliUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private static final String USER_DOES_NOT_EXIST_MSG = "User with name %s does not exist";

    @Autowired
    public ExploreCaliUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}