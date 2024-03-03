package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.dto.JoinDTO;
import com.example.jwtsecurity.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {
        return joinService.joinProcess(joinDTO);
    }
}
