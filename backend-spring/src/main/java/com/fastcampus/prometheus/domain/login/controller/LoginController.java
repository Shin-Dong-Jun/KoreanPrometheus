package com.fastcampus.prometheus.domain.login.controller;


import com.fastcampus.prometheus.domain.login.dto.LoginRequestDto;
import com.fastcampus.prometheus.domain.login.service.LoginService;
import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/auth/login")
    public String showLogin(Model model) {
        model.addAttribute("loginDto", LoginRequestDto.builder().build());
        return "loginForm";
    }


//    @PostMapping("/auth/login")
//    public String login(@Valid @ModelAttribute("loginDto")LoginRequestDto loginRequestDto){
//
//        loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//        return "redirect:/";
//    }

    @PostMapping("/auth/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginRequestDto loginRequestDto,
        Model model, HttpServletRequest request, HttpServletResponse response) {
        log.info("Login attempt: {}", loginRequestDto);

        try {
            Member member = loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            log.info("Login success for member: {}", member);

            // 세션 생성
            HttpSession session = request.getSession();
            session.setAttribute("memberId", member.getId());
            session.setAttribute("memberName", member.getName());
            log.info("Session created: sessionId={}, memberId={}, memberName={}",
                session.getId(), member.getId(), member.getName());

            // 쿠키 생성
            Cookie loginCookie = new Cookie("loginSession", session.getId());
            loginCookie.setHttpOnly(true);
            loginCookie.setPath("/");
            loginCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(loginCookie);
            log.info("Cookie created: name={}, value={}, maxAge={}",
                loginCookie.getName(), loginCookie.getValue(), loginCookie.getMaxAge());

            // 로그인 성공
            return "redirect:/"; // 성공 시 일단 돌아갈 페이지 없으니 /로
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            model.addAttribute("errorMessage", "Invalid email or password");
            return "loginForm"; // 실패 시 로그인 폼으로 다시 이동
        }
    }


}
