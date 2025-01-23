package com.fastcampus.prometheus.domain.member.dto;

import com.fastcampus.prometheus.domain.member.entity.MemberType;
import com.fastcampus.prometheus.validadtion.annotation.PasswordMatch;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@PasswordMatch
public class MemberDto {

    private String email;

    private String name;

    private String gender;

    private String password;

    private String passwordConfirm;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

}
