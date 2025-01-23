package com.fastcampus.prometheus.validadtion.validator;

import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.validadtion.annotation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, MemberDto> {

    public boolean isValid(MemberDto memberDto, ConstraintValidatorContext context) {
        if (memberDto.getPassword() == null || memberDto.getPasswordConfirm() == null) {
            return false;
        }

        if(!memberDto.getPassword().equals(memberDto.getPasswordConfirm())) {
            context.disableDefaultConstraintViolation();

            //필드에 에러가 나타나게 하기위해서
            context.buildConstraintViolationWithTemplate("비밀번호가 일치하지 않습니다.")
                .addPropertyNode("password")
                .addConstraintViolation();

            context.buildConstraintViolationWithTemplate("비밀번호가 일치하지 않습니다.")
                .addPropertyNode("passwordConfirm")
                .addConstraintViolation();

            return false;
        }
        return true;
    }
}
