package com.gini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

import static org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.oauth2Login(x -> x.defaultSuccessUrl("/"));

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/test").permitAll()
                .anyRequest().permitAll()

        );

        http.csrf(x -> x.disable());

        http.headers(
                headers -> headers.xssProtection(h -> h.headerValue(ENABLED_MODE_BLOCK))
        );

        return http.build();

    }




}
