package com.example.testsecurity.service;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {

        UserEntity data = new UserEntity();
                   data.setUsername(joinDTO.getUsername());
                   data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
                   data.setRole("ROLE_USER");

        userRepository.save(data);
    }
}