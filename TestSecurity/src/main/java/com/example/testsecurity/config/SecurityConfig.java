package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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

       /* http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );*/

        http
                .httpBasic(Customizer.withDefaults());

        http //csrf enable 하면서 h2-console은 csrf 설정 제거
                .csrf((auth) -> auth.ignoringRequestMatchers("/h2-console/**"));

        http
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
                //추가하지 않으면 h2 console 접근 안됨
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)//하나의 아이디에 대한 다중 로그인 허용 개수
                        .maxSessionsPreventsLogin(false));//초과시 기존 세션 하나 삭제
                                                          //true : 초과시 새로운 로그인 차단
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());//세션 고정 보호

        return http.build();
    }
}