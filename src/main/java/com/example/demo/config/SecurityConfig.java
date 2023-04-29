package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception{

        return httpSecurity.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/api/v1/product/*").hasRole("USER")       
            .requestMatchers( HttpMethod.POST,"/api/v1/product/*").hasRole("ADMIN")
            .requestMatchers( HttpMethod.DELETE,"/api/v1/product/*").hasRole("ADMIN")
            .and()
            .httpBasic()
            .and()
            .build();
    }

    @Bean
    UserDetailsService userDetailsService (){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User
            .withUsername("user")
            .password(passwordEncoder().encode("123456"))
            .roles("USER")
            .build());
        userDetailsManager.createUser(User.withUsername("admin")
        .password(passwordEncoder().encode("123456a"))
        .roles("ADMIN","USER")
        .build());
        return userDetailsManager;
    }
    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager( HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder())
        .and()
        .build();
    }
}
