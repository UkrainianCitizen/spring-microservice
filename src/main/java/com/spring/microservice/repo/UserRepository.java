package com.spring.microservice.repo;

import com.spring.microservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * User repository.
 */
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String userName);
}