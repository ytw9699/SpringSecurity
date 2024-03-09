package com.example.testsecurity;

import com.example.testsecurity.service.JoinService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class TestSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSecurityApplication.class, args);
    }

    @Bean
    @Profile("local")
    public TestDataInit testDataInit(JoinService joinService) {
        return new TestDataInit(joinService);
    }
}
