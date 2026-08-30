package com.hpms.opera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main entry point for the Hotel Property Management System (HPMS) application.
 * Micros Opera Xpress HPMS - Comprehensive hotel operations management system.
 */
@SpringBootApplication
public class HpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HpmsApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
