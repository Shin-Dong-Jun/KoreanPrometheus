package com.fastcampus.prometheus.domain.notice.repository;

import com.fastcampus.prometheus.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}

