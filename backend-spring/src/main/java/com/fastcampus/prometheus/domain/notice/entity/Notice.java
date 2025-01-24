package com.fastcampus.prometheus.domain.notice.entity;

import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor // 전체 컬럼에 대한 생성자
@NoArgsConstructor // 기본 생성자
@Data
@Table(name = "notice")
public class Notice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    //@ManyToOne
    @Column
    private Long writer;

    @Column(name = "notice_type")
    private String noticeType; // Add noticeType to the entity (boolean for emergency)

    @Column(name = "reg_date", nullable = false)
    @CreationTimestamp
    private Date regDate; // Add regDate (created date)

    // Convert from NoticeRequestDto to Notice entity
    public static Notice fromRequestDto(NoticeRequestDto noticeRequestDto) {
        return Notice.builder()
                .title(noticeRequestDto.getTitle())
                .content(noticeRequestDto.getContent())
                .writer(noticeRequestDto.getWriter())
                .noticeType(noticeRequestDto.getNoticeType())
                .build();
    }

    // Convert from Notice entity to NoticeResponseDto
    public NoticeResponseDto toResponseDto() {
        return NoticeResponseDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .noticeType(this.noticeType) // Convert boolean to string "true" or "false"
                .build();
    }

    // Optional: Update method to modify entity based on NoticeRequestDto
    public void updateFromRequestDto(NoticeRequestDto noticeRequestDto) {
        this.title = noticeRequestDto.getTitle();
        this.content = noticeRequestDto.getContent();
        this.writer = noticeRequestDto.getWriter();
        this.noticeType = noticeRequestDto.getNoticeType();
    }

}
