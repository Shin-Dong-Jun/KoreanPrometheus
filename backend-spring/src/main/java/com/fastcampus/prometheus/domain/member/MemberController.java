package com.fastcampus.prometheus.domain.member;


import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String createForm() {
        return "registerForm";
    }

    @PostMapping("/register")
    public String create(@Valid MemberDto memberDto, BindingResult result) {

        if (result.hasErrors()) {
            return "registerForm";
        }
        memberService.join(memberDto);

        return "redirect:/";

    }
}
