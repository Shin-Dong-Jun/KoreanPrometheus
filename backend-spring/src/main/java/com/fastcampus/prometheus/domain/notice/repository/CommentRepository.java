//package com.fastcampus.prometheus.domain.notice.repository;
//
//import com.fastcampus.prometheus.domain.notice.entity.Comment;
//import com.fastcampus.prometheus.domain.notice.entity.Notice;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface CommentRepository  extends JpaRepository<Notice, Long> {
//    List<Comment> findByBno(int bno);
//    List<Comment> findByCno(int cno);
//    int countByBno(int bno);
//    void delete(int bno);
//    void deleteByCnoAndCommenter(int cno, String commenter);
//}
