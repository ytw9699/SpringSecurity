package com.example.oauthsession.config;

import com.example.oauthsession.oauth2.CustomClientRegistrationRepo;
import com.example.oauthsession.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable());
        http
                .formLogin((login) -> login.disable());
        http
                .httpBasic((basic) -> basic.disable());

        http
                .oauth2Login((oauth2) -> oauth2//oauth2Login가 필터 및 나머지 설정 자동으로해줌
                        .loginPage("/login")
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                );
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**", "/h2-console/**").permitAll()
                        .anyRequest().authenticated());
        http
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        //추가하지 않으면 h2 console 접근 안됨

       /* http //csrf enable 하면서 h2-console은 csrf 설정 제거
                .csrf((auth) -> auth.ignoringRequestMatchers("/h2-console/**"));*/

        return http.build();
    }
}