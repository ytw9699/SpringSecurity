package com.example.jwtsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable 
        //세션방식에서는 세션이 고정되어서 csrf 공격을 방어해주어야하지만 jwt는 세션을 stateless 상태로 관리할것이라 필요없음
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식은 jwt 사용할것이라 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식도 jwt 사용할것이라 disable
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

        //세션 STATELESS 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
