package com.fastcampus.prometheus.domain.notice.dto.response;

import com.fastcampus.prometheus.domain.notice.entity.Notice;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NoticeResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long writer;
    private String noticeType;
    private Date regDate;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.writer = notice.getWriter();
        this.noticeType = notice.getNoticeType();
        this.regDate = notice.getRegDate();
    }

    public static NoticeResponseDto fromEntity(Notice notice) {
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .noticeType(notice.getNoticeType())  // Convert boolean to string
                .regDate(notice.getRegDate())
                .build();
    }
}
