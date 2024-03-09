package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig1 extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
            http
                    .authorizeRequests() //보안검사 시작  : 인가정책
                    .anyRequest().authenticated();//어떠한 요청에도 인증 받도록 : 인가정책
            http
                    .formLogin()//인증정책 폼 로그인 : UsernamePasswordAuthenticationFilter 생성
                    //.loginPage("/loginPage")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login")
                    .usernameParameter("userId")
                    .passwordParameter("passwd")
                    .loginProcessingUrl("/login_proc")
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override//인증에 성공하면 Authentication > 최종인증결과 담은 객체 전달됨
                        public void onAuthenticationSuccess(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            Authentication authentication) throws IOException, ServletException {
                            System.out.println("authentication = " + authentication.getName());
                            response.sendRedirect("/");
                        }
                    })
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            AuthenticationException exception) throws IOException, ServletException {
                            System.out.println("exception = " + exception);
                            response.sendRedirect("/login");
                        }
                    })
                    .permitAll();
    }
}
