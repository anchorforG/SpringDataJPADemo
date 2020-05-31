package com.example.springdatajpa.controller;

import com.example.springdatajpa.entity.UserTest;
import com.example.springdatajpa.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author G
 */
@RestController
public class TestController {

    @Autowired
    private UserTestRepository userRepository;

    @GetMapping("/{id}")
    private Object getTest(@PathVariable Integer id){
        return "hello: "+id;
    }

    @GetMapping("/user/{id}")
    private Object getUserTest(@PathVariable Integer id){
        Optional<UserTest> user = userRepository.findById(id);
        System.out.println(user.get());
        return user;
    }

}
