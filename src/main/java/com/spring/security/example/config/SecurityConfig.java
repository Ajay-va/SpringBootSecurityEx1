package com.spring.security.example.config;

import com.spring.security.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->{
                    request.requestMatchers("/content/user/**").hasRole("USER");
                    request.requestMatchers("/content/admin/**").hasRole("ADMIN");
                    request.requestMatchers("/content/**").permitAll();
                    request.anyRequest().authenticated();
                }).formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        return userDetailsService;
    }



//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1=User.builder()
//                .username("ajay")
//                .password("$2a$12$XnBTdaKgKF2j95dGGCiLuefhirU6WmHUbvfSI4nCjjjNVp2jwMSgy")
//                .roles(getRoles("USER,ADMIN"))
//                .build();
//
//
//        UserDetails user2=User.builder()
//                .username("navya")
//                .password("$2a$12$DxsS0i8f5dE.RDdZ9Ufsa.0.IY89jVjOitiB2CfBgYfYPAQwHMa32")
//                .roles("USER")
//                .build();
//
//
//
//        UserDetails user3=User.builder()
//                .username("vijay")
//                .password("$2a$12$FXAnYRsVk22jujs1GyBVE.He/T5hqOEP7IWcFYJGsQFFLj/2OOGJi")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2,user3);
//    }

    private String[] getRoles(String roles) {

        String[] rolesData= roles.split(",");

        return rolesData;

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

//    @Bean
//    public AuthenticationManager authenticationManager(){
//        return new ProviderManager(authenticationProvider());
//    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
