package com.fastcampus.prometheus.domain.notice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor // 전체 컬럼에 대한 생성자
@NoArgsConstructor // 기본 생성자
@Data
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "cno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;

    @Column(nullable = false)
    private int bno;

    @Column
    private int pcno;

    @Column
    private String comment;

    @Column
    private String commenter;

    @Column(name="reg_date")
    private Date regDate;

    @Column(name="up_date")
    private Date upDate;
}
