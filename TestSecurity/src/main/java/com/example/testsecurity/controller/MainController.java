package com.example.testsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(Model model) {
        model.addAttribute("userId", getUserId());
        model.addAttribute("role",getRole());
        return "main";
    }

    @GetMapping("/my")
    public String mypage(Model model) {
        model.addAttribute("userId", getUserId());
        model.addAttribute("role",getRole());
        return "mypage";
    }

    private String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();
        return role;
    }

    private String getUserId() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return userId;
    }
}
