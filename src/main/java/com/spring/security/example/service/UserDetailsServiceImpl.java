package com.spring.security.example.service;

import com.spring.security.example.entities.User;
import com.spring.security.example.repositories.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


    Optional<User> optionalUser= userRepository.findByUsername(username);

        System.out.println("username"+optionalUser.get().getUsername());
    log.info("LOAD USER DETAILS .... ");
    if(optionalUser.isPresent()){
        User user=optionalUser.get();
        log.info("USER DETAILS .... ",user.getUsername());

        return org.springframework.security.core.userdetails.User
               .builder()
               .username(user.getUsername())
               .password(user.getPassword())
               .roles(getUserRoles(user))
               .build();
    }else {
        throw new UsernameNotFoundException("INVALID USER FOUND...!!!!!");
    }
    }

    private String[] getUserRoles(User user) {

        if(user.getRoles()==null){
         return    new String[] {"USER"};
        }else {
         return user.getRoles().split(",");

        }
    }
}
