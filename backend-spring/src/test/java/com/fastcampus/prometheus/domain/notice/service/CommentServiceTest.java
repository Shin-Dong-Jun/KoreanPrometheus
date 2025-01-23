//package com.fastcampus.prometheus.domain.notice.service;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.fastcampus.prometheus.domain.notice.dto.request.CommentRequestDto;
//import com.fastcampus.prometheus.domain.notice.dto.request.NoticeRequestDto;
//import com.fastcampus.prometheus.domain.notice.repository.CommentRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//@SpringBootTest
//public class CommentServiceTest {
//
//    @Autowired
//    private NoticeService noticeService;
//
//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private CommentRepository commentRepository;
//    @Test
//    public void remove() throws Exception {
//        // 모든 게시판 데이터 삭제
//        commentService.deleteAll();
//
//        // 새로운 게시판 데이터 추가
//        NoticeRequestDto noticeRequestDto = new NoticeRequestDto("hello", "hello", "asdf", "일반");
//        assertTrue(noticeService.createNotice(noticeRequestDto)==1);
//
//        Long bno = noticeService.getNoticeById(2L).get().getId().
//        // 해당 게시판과 관련된 모든 댓글 삭제
//        commentService.deleteAll();
//        CommentRequestDto commentRequestDto = new CommentRequestDto(cno, 0, "hi", "qwer");
//
//        // 댓글 수 검증
//        assertTrue(boardDao.select(bno).getComment_cnt() == 0);
//        assertTrue(commentService.write(commentDto) == 1);
//        assertTrue(boardDao.select(bno).getComment_cnt() == 1);
//
//        // 댓글 번호 가져오기
//        Integer cno = commentDao.selectAll(bno).get(0).getCno();
//
//        // 댓글 삭제 및 검증
//        int rowCnt = commentService.delete(cno, bno, commentDto.getCommenter());
//        assertTrue(rowCnt == 1);
//        assertTrue(boardDao.select(bno).getComment_cnt() == 0);
//    }
//}
