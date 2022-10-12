package com.example.springw1crud.service;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

import com.example.springw1crud.dto.UserDto;
import com.example.springw1crud.entity.Authority;
import com.example.springw1crud.entity.User;
import com.example.springw1crud.exception.CustomUserSignupException;
import com.example.springw1crud.exception.DuplicateMemberException;
import com.example.springw1crud.exception.NotFoundMemberException;
import com.example.springw1crud.repository.UserRepository;
import com.example.springw1crud.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }
        if(!Pattern.matches("^[a-zA-Z0-9]*$", userDto.getUsername())){
            throw new CustomUserSignupException("아이디 형식이 올바르지 않습니다.");
        }
        if(!Pattern.matches("^[a-z0-9]*$",userDto.getPassword())){
            throw new CustomUserSignupException("비밀번호 형식이 올바르지 않습니다.");
        }
        if(!userDto.getPassword().equals(userDto.getPasswordRepeat())){
            throw new CustomUserSignupException("비밀번호 확인이 일치하지 않습니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}
