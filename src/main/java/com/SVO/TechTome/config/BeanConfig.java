package com.SVO.TechTome.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Short-timeout template for user-facing Econt calls (cost calc, label creation). */
    @Bean
    public RestTemplate restTemplate(
            RestTemplateBuilder builder,
            @Value("${econt.connect-timeout-ms:3000}") int connectTimeoutMs,
            @Value("${econt.read-timeout-ms:8000}") int readTimeoutMs) {
        return builder
                .connectTimeout(Duration.ofMillis(connectTimeoutMs))
                .readTimeout(Duration.ofMillis(readTimeoutMs))
                .build();
    }

    /** Long-timeout template for background nomenclature loading (cities, offices, streets). */
    @Bean
    @Qualifier("nomenclaturesRestTemplate")
    public RestTemplate nomenclaturesRestTemplate(
            RestTemplateBuilder builder,
            @Value("${econt.connect-timeout-ms:3000}") int connectTimeoutMs) {
        return builder
                .connectTimeout(Duration.ofMillis(connectTimeoutMs))
                .readTimeout(Duration.ofSeconds(120))
                .build();
    }
}
