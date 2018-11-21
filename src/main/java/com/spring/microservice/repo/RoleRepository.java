package com.spring.microservice.repo;

import com.spring.microservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Role repository.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
}