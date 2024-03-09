package com.example.jwtsecurity.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                             Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(Jws<Claims> claims) {
        return claims.getPayload().get("username", String.class);
    }

    public String getRole(Jws<Claims> claims) {
        return claims.getPayload().get("role", String.class);
    }

    public Boolean isExpired(Jws<Claims> claims) {//토큰 만료확인
        return claims.getPayload().getExpiration().before(new Date());//현재시간과 비교
    }
    public Jws<Claims> verifyWith(String token){//verifyWith 토큰이 우리서버에서 생성된건지, 우리가 가지고있는 키랑 맞는지 확인
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    public String getCategory(Jws<Claims> claims) {
        return claims.getPayload().get("category", String.class);
    }

    public String createJwt(String category, String username, String role, Long expiredMs) {//expiredMs 살아 있을 시간
        return Jwts.builder()
                        .claim("category", category)//토큰 종류 access, refresh
                        .claim("username", username)//페이로드
                        .claim("role", role)//페이로드
                        .issuedAt(new Date(System.currentTimeMillis()))//현재 발행 시간
                        .expiration(new Date(System.currentTimeMillis() + expiredMs))//소멸시간
                        .signWith(secretKey)//시그니처 암호화진행
                    .compact();//토큰을 컴팩트
    }
}
