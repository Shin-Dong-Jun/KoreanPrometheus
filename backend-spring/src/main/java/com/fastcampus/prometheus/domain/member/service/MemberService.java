package com.fastcampus.prometheus.domain.member.service;


import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 생성
    public Long join(MemberDto memberDto) {

        Member member = Member.builder()
            .email(memberDto.getEmail())
            .name(memberDto.getName())
            .password(memberDto.getPassword())
            .gender(memberDto.getGender())
            .memberType(memberDto.getMemberType())
            .build();

        memberRepository.save(member);
        return member.getId(); // member id 리턴.
    }
}
