package com.fastcampus.prometheus.domain.notice.dto.request;

import com.fastcampus.prometheus.domain.member.entity.MemberType;
import com.fastcampus.prometheus.domain.notice.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
public class NoticeRequestDto {
    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    /**
     * TODO
     * member 테이블 id와 조인
     */
    private Long writer;

    /**
     * TODO
     * 긴급, 일반 처리하기
     */
    private String noticeType; // Can be 'true' for emergency or 'false' for normal

    public Notice toEntity() {
        return Notice.builder()
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .noticeType(this.noticeType)
                .build();
    }

}
