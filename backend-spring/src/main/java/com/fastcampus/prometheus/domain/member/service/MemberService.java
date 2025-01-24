package com.fastcampus.prometheus.domain.member.service;


import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    //회원 생성
    public Member join(MemberDto memberDto) {

        String encryptedPassword = passwordEncoder.encode(memberDto.getPassword());

        Member member = Member.builder()
            .email(memberDto.getEmail())
            .name(memberDto.getName())
            .password(encryptedPassword)
            .memberType(memberDto.getMemberType())
            .build();

        memberRepository.save(member);

        return member;
    }
}
