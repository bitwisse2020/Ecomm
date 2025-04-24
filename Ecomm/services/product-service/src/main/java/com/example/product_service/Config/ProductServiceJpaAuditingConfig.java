package com.example.product_service.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

//JPA auditing config triggers Spring to register infrastructure beans (like AuditingHandler)
// responsible for processing the annotations like @CreatedDate, @LastModifiedDate, @CreatedBy, etc.
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ProductServiceJpaAuditingConfig {

    /*
     **TODO:When using Spring Security or a token-based system later, you can inject the actual username:
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        // You can customize this to extract the user from SecurityContext
        return () -> Optional.of("system"); // or return a user from thread context / auth
    }
}
