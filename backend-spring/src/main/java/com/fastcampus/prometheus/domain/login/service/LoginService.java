package com.fastcampus.prometheus.domain.login.service;


import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    //1. 로그인 로직. 이메일이랑 비번이 DB에 있고 일치하면 member반환
    public Member login(String loginEmail, String password) {

        return memberRepository.findByEmail(loginEmail)
            .filter(member -> passwordEncoder.matches(password, member.getPassword()))
            .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

    }


}
