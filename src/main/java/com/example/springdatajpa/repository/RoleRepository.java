package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author G
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
