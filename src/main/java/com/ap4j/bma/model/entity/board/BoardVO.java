package com.ap4j.bma.model.entity.board;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class BoardVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //게시글 번호
    private String title; //게시글 제목
    private String content; //게시글 내용
    private String writer; //작성자
    //작성일 추가
    private Integer viewnum; //조회수
}
