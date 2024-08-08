package com.spring.security.example.controller;

import com.spring.security.example.entities.User;
import com.spring.security.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/home")
    public String homePage(){

        return "Welcome to Home Page...!!!";
    }

    @GetMapping("/admin/home")
    public String adminPage(){

        return "Welcome to Admin Page...!!!";
    }

    @GetMapping("/user/home")
    public String userPage(){

        return "Welcome to User Page...!!!";
    }

    @PostMapping("/register")
    public User sendData(@RequestBody User user){

     String userId=   UUID.randomUUID().toString();
     user.setId(userId);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @PostMapping("/send")
    public String sendData(){

        return "I sent a Mail , Welcome to INDIA Page...!!!";
    }


    @DeleteMapping("/delete")
    public String delete(){

        return "Deleted the message ...!!!";
    }

    @PutMapping("/update")
    public String update(){

        return "Message updated successfully....!!!";
    }


}
