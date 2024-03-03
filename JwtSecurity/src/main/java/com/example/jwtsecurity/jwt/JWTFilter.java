package com.example.jwtsecurity.jwt;

import com.example.jwtsecurity.dto.CustomUserDetails;
import com.example.jwtsecurity.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor//jwt 검증 필터
public class JWTFilter extends OncePerRequestFilter {//OncePerRequestFilter요청에 대해 한번만

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("token null");
            filterChain.doFilter(request, response);//다음 필터 이동
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String token = authorization.split(" ")[1];//토큰에서 Bearer자름
        Jws<Claims> claims = jwtUtil.verifyWith(token);//토큰 검증

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(claims)) {
            log.info("token expired");
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String username = jwtUtil.getUsername(claims);
        String role = jwtUtil.getRole(claims);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("temppassword");//비밀번호까지 디비조회하는건 비효율, 컨텍스트에도 굳이 넣을 필요없음
        userEntity.setRole(role);

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken =
                new UsernamePasswordAuthenticationToken(customUserDetails,
                                               null,
                                                        customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        //유저 세션 생성함으로써 특별한 경로에 접근가능
        filterChain.doFilter(request, response);//그다음 필터 이동
    }
}
