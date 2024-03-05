package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//시큐리티서 관리
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/h2-console/**", "/login",
                                                  "/loginProc", "/join", "/joinProc").permitAll()//모두에게
                        .requestMatchers("/admin").hasRole("ADMIN")//어드민만
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")//2개 롤만
                        .anyRequest().authenticated()//로그인 해야만
                );

        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable());//사이트 위변조 방지 설정 잠시 끄기

        http
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
                //추가하지 않으면 h2 console 접근 안됨

        return http.build();
    }
}