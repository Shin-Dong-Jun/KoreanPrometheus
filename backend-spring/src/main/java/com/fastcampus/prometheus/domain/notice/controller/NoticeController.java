package com.fastcampus.prometheus.domain.notice.controller;

import com.fastcampus.prometheus.domain.notice.dto.PageHandler;
import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import com.fastcampus.prometheus.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 모든 공지사항 조회
    @GetMapping
    public String getAllNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<NoticeResponseDto> noticePage = noticeService.getPage(page, size);
        PageHandler pageHandler = new PageHandler((int)noticePage.getTotalElements(), page, size);

        model.addAttribute("ph", pageHandler);
        model.addAttribute("noticePage", noticePage);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", noticePage.getTotalPages());

        return "notice/noticeList";  // "noticeList.jsp"에 공지사항 목록을 전달
    }

    // 특정 공지사항 조회
    @GetMapping("/{id}")
    public String getNoticeById(@PathVariable("id") Long id, Model model) {
        Optional<NoticeResponseDto> noticeResponseDto = noticeService.getNoticeById(id);
        if (noticeResponseDto.isPresent()) {
            model.addAttribute("notice", noticeResponseDto.get());
            return "notice/notice";  // 공지사항 상세 보기 페이지
        } else {
            return "redirect:/notice";  // 공지사항을 찾을 수 없는 경우 목록 페이지로 리다이렉트
        }
    }

    // 공지사항 작성폼 페이지
    @GetMapping("/write")
    public String createNotice(Model model) {
        return "notice/noticeForm";  // 공지사항 작성 폼
    }

    // 공지사항 생성 처리
    @PostMapping("/write")
    public String createNotice(@ModelAttribute NoticeRequestDto noticeRequestDto) {
        noticeService.createNotice(noticeRequestDto);
        return "redirect:/notice";  // 공지사항 목록 페이지로 리다이렉트
    }


    // 공지사항 수정 처리
    @PutMapping("/{id}")
    public String updateNotice(@PathVariable Long id, @ModelAttribute NoticeRequestDto noticeRequestDto) {
        try {
            noticeService.updateNotice(id, noticeRequestDto);
            return "redirect:/notice";  // 공지사항 목록 페이지로 리다이렉트
        } catch (RuntimeException e) {
            return "redirect:/notice";  // 공지사항을 찾을 수 없는 경우 목록 페이지로 리다이렉트
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = false) List<Long> id, Model model) {
        if(id != null && !id.isEmpty()){
            List<NoticeResponseDto> notice = noticeService.readAllById(id);
            model.addAttribute("notice", notice);
        }
        return "notice/noticeDelete";
    }
    // 공지사항 삭제
    @DeleteMapping("/delete")
    public String deleteNotice(@RequestParam List<Long> id) {
            noticeService.deleteNotice(id);
            return "redirect:/notice";
    }

//    @PostMapping("/{id}")
//    public String delete(@PathVariable Long id){
//
//        noticeService.delete(id);
//        return "redirect:/notice";
//    }
}
