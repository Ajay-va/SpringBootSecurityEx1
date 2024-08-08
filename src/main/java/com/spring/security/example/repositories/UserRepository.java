package com.spring.security.example.repositories;

import com.spring.security.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    public Optional<User> findByUsername(String username);

}
