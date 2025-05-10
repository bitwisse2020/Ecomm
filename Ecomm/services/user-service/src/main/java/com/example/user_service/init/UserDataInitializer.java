package com.example.user_service.init;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import com.example.user_service.entity.User;
import com.example.user_service.repository.UserRepository;
import com.example.common.Models.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class UserDataInitializer {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .email("admin@example.com")
                        .password("admin123") // Encode the password
                        .firstName("Admin")
                        .lastName("User")
                        .username("admin")
                        .role(UserRole.ADMIN)
                        .isActive(true)
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .addresses(Collections.emptyList())
                        .build();

                userRepository.save(admin);
                System.out.println("✅ Admin user created: username=admin, password=admin123");
            } else {
                System.out.println("ℹ️ Admin user already exists. Skipping initialization.");
            }
        };
    }
}

