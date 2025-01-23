package com.fastcampus.prometheus.domain.notice.service;

import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import com.fastcampus.prometheus.domain.notice.repository.NoticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoticeServiceTest {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    @DisplayName("Registration Notice Test")
    public void registerNoticeTest() {
        IntStream.rangeClosed(1, 100000)
                .mapToObj(i -> NoticeRequestDto.builder()
                        .title("테스트"+i)
                        .content("내용테스트"+i)
                        .noticeType("일반")
                        .build())
                .forEach(noticeRequestDto ->{
                    noticeService.createNotice(noticeRequestDto);
                });
    }
}