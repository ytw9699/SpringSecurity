package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//시큐리티서 관리
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login").permitAll()//모두에게
                        .requestMatchers("/admin").hasRole("ADMIN")//어드민만
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")//2개 롤만
                        .anyRequest().authenticated()//로그인 해야만
                );

        return http.build();
    }
}