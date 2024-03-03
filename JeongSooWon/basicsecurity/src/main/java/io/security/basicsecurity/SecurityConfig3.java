package io.security.basicsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig3 extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .anyRequest().authenticated();
            http
                    .formLogin();
            http
                    .rememberMe()//리멤버미 기능이 동작함
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(3600)//3600초 1시간 설정함. 디폴트는 14일
                    .alwaysRemember(false)//리멤버미 기능 화면에서 체크해야 실행되게 디폴트 false
                    .userDetailsService(userDetailsService);//사용자계정조회하는 처리에서 필요
    }
}
