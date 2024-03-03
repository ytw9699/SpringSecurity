package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig4 extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/expired", "/invalid").permitAll() // expired URL에 대한 접근을 모든 사용자에게 허용
                .anyRequest().authenticated();

            http
                    .formLogin().and()
                    .sessionManagement()//세션관리 기능이 작동함
                    .maximumSessions(1)//최대 허용 세션수, -1은 무제한 로그인 세션 허용
                    .maxSessionsPreventsLogin(true)//동시 로그인 차단 > true 는 아예 로그인 못하게, 디폴트 false : 기존 세션 만료
                    .expiredUrl("/expired")//세션이 만료된 경우 이동할 페이지
                    .and().invalidSessionUrl("/invalid");//expired보다 우선권을 가짐. 세션이 무효화되었거나 유효하지 않을때 이동할 페이지
    }
}
