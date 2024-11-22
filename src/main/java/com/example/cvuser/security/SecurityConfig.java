package com.example.cvuser.security; 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers("/", "/register", "/login", "/css/**", "/js/**", "/images/**").permitAll() // Allow public access to resources
                .anyRequest().permitAll() // Allow access to all pages for simplicity
                .and()
            .csrf().disable() // Disable CSRF protection since security isn't required
            .logout()
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .permitAll();
        return http.build();
    }
}
