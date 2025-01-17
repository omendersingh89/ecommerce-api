package com.ecommerce.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
@Bean
public PasswordEncoder passwordEncoder() {
 return new BCryptPasswordEncoder();
 }
}