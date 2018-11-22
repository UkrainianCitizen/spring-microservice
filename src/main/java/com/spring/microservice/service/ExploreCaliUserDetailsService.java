package com.spring.microservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * User service.
 */
public interface ExploreCaliUserDetailsService extends UserDetailsService {

    /**
     * Extracts username and roles from a validated jwt string.
     *
     * @param jwtToken jwt string
     * @return UserDetails if valid, Empty otherwise
     */
    Optional<UserDetails> loadUserByJwtToken(String jwtToken);

    /**
     * Extracts the username from the JWT then lookup the user in the database.
     *
     * @param jwtToken jwt string
     * @return user details
     */
    Optional<UserDetails> loadUserByJwtTokenAndDatabase(String jwtToken);
}