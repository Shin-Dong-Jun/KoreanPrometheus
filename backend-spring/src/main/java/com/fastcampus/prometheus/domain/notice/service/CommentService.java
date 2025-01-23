//package com.fastcampus.prometheus.domain.notice.service;
//
//import com.fastcampus.prometheus.domain.notice.dto.request.CommentRequestDto;
//import com.fastcampus.prometheus.domain.notice.dto.response.CommentResponseDto;
//import com.fastcampus.prometheus.domain.notice.entity.Comment;
//import com.fastcampus.prometheus.domain.notice.repository.CommentRepository;
//import com.fastcampus.prometheus.domain.notice.repository.NoticeRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//@Log4j2
//public class CommentService {
//    private final CommentRepository commentRepository;
//    public void deleteAll(Long bno) {
//        commentRepository.deleteById(bno);
//    }
////    public void delete(int cno, String commenter) {
//        commentRepository.deleteByCnoAndCommenter(cno, commenter);
//    }
//    public void createComment(CommentRequestDto commentRequestDto) {
//        try{
//            Comment comment = CommentRequestDto.toEntity();  // DTO를 엔티티로 변환
//            Comment savedComment = commentRepository.save(comment);  // 공지사항 저장
//            CommentResponseDto.builder()
//                    .cno(savedComment.getCno())
//                    .bno(savedComment.getBno())
//                    .comment(savedComment.getComment())
//                    .commenter(savedComment.getCommenter())
//                    .pcno(savedComment.getPcno())
//                    .regDate(savedComment.getRegDate())
//                    .build();
//        }catch(Exception e){
//            System.out.println("공지사항 생성 중 오류" + e.getMessage());
//        }
//    }
//}