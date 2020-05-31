package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author G
 */
public interface StudentRepository extends JpaRepository<Student,Integer> {
}
