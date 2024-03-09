package com.example.testsecurity.service;

import com.example.testsecurity.dto.CustomUserDetails;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username 사용자 로그인시 시큐리티가 넣어줌
        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            return new CustomUserDetails(user);
        }
        return null;
    }
}
