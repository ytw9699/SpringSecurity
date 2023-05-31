package com.demo.springsecurity.controller;

import com.demo.springsecurity.domain.ResponseCookieDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class DemoController {
    @GetMapping("/")
    public String helloWorld(){
        return "helloWorld";
    }

    @GetMapping("/session")//프론트가 따로 구현되어있다면 넘겨줄 세션아이디값 테스트
    public ResponseCookieDTO hello(@AuthenticationPrincipal User user, HttpSession HttpSession) {
        String username = "empty";
        String session_id = "empty";

        if (user == null){
            System.out.println("user is null");
        } else {
            session_id = HttpSession.getId();
            username = user.getUsername();
        }

        return new ResponseCookieDTO(username, session_id);
    }
}
