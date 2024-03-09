package com.example.testsecurity;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final JoinService joinService;

    /**
     * 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("user join init");

        JoinDTO user = new JoinDTO();
        user.setUsername("user");
        user.setPassword("1111");

        joinService.joinProcess(user);
    }

}
