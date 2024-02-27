package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
            http
                    .authorizeRequests() //보안검사 시작  : 인가정책
                    .anyRequest().authenticated();//어떠한 요청에도 인증 받도록 : 인가정책
            http
                    .formLogin();//인증정책 폼 로그인

    }
}
