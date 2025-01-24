package com.fastcampus.prometheus.domain.notice.service;

import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
import com.fastcampus.prometheus.domain.notice.dto.response.NoticeResponseDto;
import com.fastcampus.prometheus.domain.notice.entity.Notice;
import com.fastcampus.prometheus.domain.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항 조회 (페이징 처리 포함) 메서드
    public Page<NoticeResponseDto> getPage(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(
                page
                ,pageSize
                ,Sort.by(Sort.Order.asc("noticeType")
                        ,Sort.Order.desc("regDate")
                )
        );

        return noticeRepository.findAll(pageable)
                .map(notice -> NoticeResponseDto.builder()
                        .id(notice.getId())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .writer(notice.getWriter())
                        .noticeType(notice.getNoticeType())
                        .regDate(notice.getRegDate())
                        .build());
    }

    // 특정 공지사항 조회 (NoticeResponseDto 반환)
    public Optional<NoticeResponseDto> getNoticeById(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        return notice.map(n -> NoticeResponseDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .writer(n.getWriter())
                .noticeType(n.getNoticeType())
                .regDate(n.getRegDate())
                .build());
    }

    // 공지사항 생성 (NoticeRequestDto 받기)
    public void createNotice(NoticeRequestDto noticeRequestDto) {
        try{
            Notice notice = noticeRequestDto.toEntity();  // DTO를 엔티티로 변환
            Notice savedNotice = noticeRepository.save(notice);  // 공지사항 저장

            NoticeResponseDto.builder()
                    .id(savedNotice.getId())
                    .title(savedNotice.getTitle())
                    .content(savedNotice.getContent())
                    .writer(savedNotice.getWriter())
                    .noticeType(savedNotice.getNoticeType())
                    .regDate(savedNotice.getRegDate())
                    .build();
        }catch(Exception e){
            System.out.println("공지사항 생성 중 오류" + e.getMessage());
        }
    }

    // 공지사항 수정 (NoticeRequestDto 받기)
    public void updateNotice(Long id, NoticeRequestDto noticeRequestDto) {
        Optional<Notice> existingNotice = noticeRepository.findById(id);
        if (existingNotice.isPresent()) {
            Notice existing = existingNotice.get();
            existing.setTitle(noticeRequestDto.getTitle());  // DTO의 제목으로 수정
            existing.setContent(noticeRequestDto.getContent());  // DTO의 내용으로 수정
            existing.setWriter(noticeRequestDto.getWriter());
            existing.setNoticeType(noticeRequestDto.getNoticeType());
            Notice updatedNotice = noticeRepository.save(existing);  // 수정된 공지사항 저장
            NoticeResponseDto.builder()
                    .id(updatedNotice.getId())
                    .title(updatedNotice.getTitle())
                    .content(updatedNotice.getContent())
                    .writer(updatedNotice.getWriter())
                    .noticeType(updatedNotice.getNoticeType())
                    .regDate(updatedNotice.getRegDate())
                    .build();
        } else {
            throw new RuntimeException("Notice not found with id " + id);  // 공지사항이 없는 경우
        }
    }

    public void deleteNotice(List<Long> id) {
        if (id.size() == 1) {
            // 하나의 상품을 삭제
            noticeRepository.deleteById(id.getFirst());
        } else {
            // 여러 개의 상품을 삭제
            List<Notice> notices = noticeRepository.findAllById(id);
            noticeRepository.deleteAll(notices);
        }
    }
    
    public void delete(Long id){
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 문의글이 존재하지 않습니다."));

        noticeRepository.delete(notice);
    }

    public List<NoticeResponseDto> readAllById(List<Long> id) {
        List<Notice> notices = noticeRepository.findAllById(id);
        return notices.stream()
                .map(NoticeResponseDto::new) // Notice 객체를 NoticeResponseDto로 변환
                .collect(Collectors.toList());
    }
}
