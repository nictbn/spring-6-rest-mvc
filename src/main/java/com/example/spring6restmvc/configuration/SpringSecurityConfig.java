package com.example.spring6restmvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("!test")
@Configuration
public class SpringSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/v3/api-docs**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();
        return http.build();
    }
}
