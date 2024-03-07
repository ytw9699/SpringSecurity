package com.example.jwtsecurity.controller;

import com.example.jwtsecurity.dto.JoinDTO;
import com.example.jwtsecurity.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {
        return joinService.joinProcess(joinDTO);
    }
}
