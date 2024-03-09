package com.example.jwtsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@RestController
public class AdminController {

    @GetMapping("/admin")
    public String adminP(Authentication authentication) {

        String username = authentication.getName(); // 사용자의 아이디
        log.info(username);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        log.info(role);

        return "Admin Controller username = "+username +", role = " +role;
    }
}
