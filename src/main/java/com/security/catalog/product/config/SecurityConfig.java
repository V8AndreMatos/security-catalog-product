package com.security.catalog.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desabilita CSRF se necessário
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers("/public/**").permitAll() // libera endpoints públicos
                        .requestMatchers("/h2-console/**").permitAll() // libera o endpoint do h2-console
                        .anyRequest().authenticated() // exige login pros demais
                )
                .formLogin(); // login padrão do Spring Security

        return http.build();
    }
}
