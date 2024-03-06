package com.example.oauthsession.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {

    @GetMapping("/my")
    public String myPage() {
        return "my";
    }
}